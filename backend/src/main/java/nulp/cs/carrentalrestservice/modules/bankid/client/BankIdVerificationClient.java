package nulp.cs.carrentalrestservice.modules.bankid.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import nulp.cs.carrentalrestservice.modules.bankid.dto.PersonalDataDTO;
import nulp.cs.carrentalrestservice.modules.bankid.mapper.BankIdMapper;
import nulp.cs.carrentalrestservice.shared.exception.NotFoundException;
import nulp.cs.carrentalrestservice.shared.dto.request.PersonalInfoRequest;
import nulp.cs.carrentalrestservice.modules.person.PersonService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Component
@RequiredArgsConstructor
public class BankIdVerificationClient {

    @Value("${bank.id.url}")
    private String baseUrl;

    @Value("${bank.id.secret_key}")
    private String key;

    private final String graphqlEndpoint = baseUrl + "/graphql";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    private final PersonService personService;
    private final BankIdMapper bankIdMapper;

    public void createPersonWithRandomData(UUID personId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        String url = baseUrl + "/api/v1/person/" + personId.toString();

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
        variables.put("person", bankIdMapper.convertRequestToPersonInput(customerRequest));
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
        Map<String, Object> graphqlRequest = bankIdMapper.createPersonQueryVariables(personId);
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

        PersonalInfoRequest personalInfoRequest = bankIdMapper.convertJsonToPersonalInfoRequest(personData);

        PersonalDataDTO personalDataDTO = PersonalDataDTO.builder()
                .driverLicense(personalInfoRequest.getDriverLicense())
                .passport(personalInfoRequest.getPassport())
                .person(personService.getPersonById(personalInfoRequest.getPersonId())
                        .orElseThrow(() -> new NotFoundException("Person not found!")))
                .build();

        return Optional.ofNullable(personalDataDTO);
    }

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
}