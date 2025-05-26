package nulp.cs.carrentalrestservice.service.person;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.model.dto.DriverLicensesDTO;
import nulp.cs.carrentalrestservice.model.dto.PassportDTO;
import nulp.cs.carrentalrestservice.model.dto.PersonalDataDTO;
import nulp.cs.carrentalrestservice.model.request.PersonalInfoRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;


@Component
@RequiredArgsConstructor
public class PersonalInfoService {
    @Value("${bank.id.url}")
    private String baseUrl;
    private final String graphqlEndpoint=baseUrl+"/graphql";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final PersonService personService;

    @Value("${bank.id.secret_key}")
    private String key;



    private final RestTemplate restTemplate = new RestTemplate();

    public void createPersonWithRandomData(UUID personId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        String url = baseUrl+"/api/v1/person/"+personId.toString();

        ResponseEntity<Void> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                Void.class
        );

        response.getStatusCode();
    }

    public void createPerson(PersonalInfoRequest customerRequest) throws Exception {
        String mutation = """
                mutation CreatePerson($person: PersonInput!) {
                  createPerson(person: $person) {
                    id
                  }
                }
                """;

        Map<String, Object> graphqlRequest = new HashMap<>();
        graphqlRequest.put("query", mutation);

        Map<String, Object> variables = new HashMap<>();
        variables.put("person", convertRequestToPersonInput(customerRequest));
        graphqlRequest.put("variables", variables);

        String requestBody = objectMapper.writeValueAsString(graphqlRequest);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(graphqlEndpoint))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Error creating person: " + response.statusCode());
            }

        } catch (Exception e) {
            throw e;
        }
    }

    @SneakyThrows
    public Optional<PersonalDataDTO> getCustomerDataById(UUID personId) {
        Map<String, Object> graphqlRequest = getStringObjectMap(personId);
        String requestBody = objectMapper.writeValueAsString(graphqlRequest);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/graphql"))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = httpClient.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("HTTP error: " + response.statusCode());
        }

        JsonNode responseJson = objectMapper.readTree(response.body());

        if (responseJson.has("errors")) {
            JsonNode errors = responseJson.get("errors");

            handleBankIdErrors(errors);
        }
        JsonNode personData = responseJson.get("data").get("person");

        if (personData == null || personData.isNull()) {
            return Optional.empty();
        }
        PersonalInfoRequest personalInfoRequest  = convertJsonToCustomerFullInfoRequest(personData);

        PersonalDataDTO personalDataDTO = PersonalDataDTO.builder()
                .driverLicense(personalInfoRequest.getDriverLicense())
                .passport(personalInfoRequest.getPassport())
                .person(personService.getPersonById(personalInfoRequest.getPersonId())
                        .orElseThrow(()-> new NotFoundException("Person not found!")))
                .build();

        return Optional.ofNullable(personalDataDTO);
    }

    //TODO
    private void handleBankIdErrors(JsonNode errors) {
        for (JsonNode error : errors) {
            if (error.has("extensions")) {
                JsonNode extensions = error.get("extensions");
                if (extensions.has("classification")) {
                    String classification = extensions.get("classification").asText();
                    if ("INTERNAL_ERROR".equals(classification)) {
                        if (error.has("message")) {
                            String errorMessage = error.get("message").asText().toLowerCase();
                            if (errorMessage.contains("personal info not found") ||
                                    errorMessage.contains("not found") ||
                                    errorMessage.contains("does not exist")) {
                                throw new NotFoundException("Person data not found");
                            }
                        }
                    }
                }
                if (extensions.has("code")) {
                    String errorCode = extensions.get("code").asText();
                    if ("NOT_FOUND".equals(errorCode) || "ENTITY_NOT_FOUND".equals(errorCode)) {
                         throw new NotFoundException("Person data not found");
                    }
                }
            }

            if (error.has("message")) {
                String errorMessage = error.get("message").asText().toLowerCase();
                if (errorMessage.contains("personal info not found") ||
                        errorMessage.contains("not found") ||
                        errorMessage.contains("does not exist")) {
                    throw new NotFoundException("Person data not found");
                }
            }
        }
    }

    private Map<String, Object> convertRequestToPersonInput(PersonalInfoRequest request) {
        Map<String, Object> personInput = new HashMap<>();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        personInput.put("id", request.getPersonId().toString());

        if (request.getPassport() != null) {
            Map passportMap = objectMapper.convertValue(request.getPassport(), Map.class);
            passportMap.remove("id");
            personInput.put("passport", passportMap);        }

        if (request.getDriverLicense() != null) {
            Map licenseMap = objectMapper.convertValue(request.getDriverLicense(), Map.class);
            licenseMap.remove("id");

            if (licenseMap.containsKey("categories") && licenseMap.get("categories") instanceof List) {
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> categories = (List<Map<String, Object>>) licenseMap.get("categories");
                for (Map<String, Object> category : categories) {
                    category.remove("id");
                }
            }

            personInput.put("driverLicense", licenseMap);        }

        return personInput;
    }



    private static Map<String, Object> getStringObjectMap(UUID personId) {
        String query = """
            query GetPerson($id: UUID!) {
              person(id: $id) {
                id
                passport {
                  id
                  fullName
                  dateOfBirth
                  documentNumber
                  issuedBy
                  expirationDate
                  taxIdentificationNumber
                }
                driverLicense {
                  id
                  expirationDate
                  issueDate
                  issuedBy
                  documentNumber
                  categories {
                    category
                    issueDate
                  }
                }
              }
            }
            """;

        Map<String, Object> graphqlRequest = new HashMap<>();
        graphqlRequest.put("query", query);

        Map<String, Object> variables = new HashMap<>();
        variables.put("id", personId.toString());
        graphqlRequest.put("variables", variables);
        return graphqlRequest;
    }

    private PersonalInfoRequest convertJsonToCustomerFullInfoRequest(JsonNode personData) throws Exception {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        PersonalInfoRequest.PersonalInfoRequestBuilder builder = PersonalInfoRequest.builder();

        if (personData.has("id")) {
            builder.personId(UUID.fromString(personData.get("id").asText()));
        }

        if (personData.has("passport") && !personData.get("passport").isNull()) {
            JsonNode passportNode = personData.get("passport");
            PassportDTO passport = objectMapper.convertValue(passportNode, PassportDTO.class);
            builder.passport(passport);
        }

        if (personData.has("driverLicense") && !personData.get("driverLicense").isNull()) {
            JsonNode driverLicenseNode = personData.get("driverLicense");
            DriverLicensesDTO driverLicense = objectMapper.convertValue(driverLicenseNode, DriverLicensesDTO.class);
            builder.driverLicense(driverLicense);
        }

        return builder.build();
    }
}





