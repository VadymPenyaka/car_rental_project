package nulp.cs.carrentalrestservice.service.person;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.model.request.CustomerFullInfoRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class PersonGraphQlClient {
    private final String graphqlEndpoint="http://localhost:8080/graphql";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();


    public void createPerson(CustomerFullInfoRequest customerRequest) throws Exception {
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
                throw new RuntimeException("HTTP помилка при створенні PersonDTO! Статус: " + response.statusCode());
            }

            JsonNode responseJson = objectMapper.readTree(response.body());

            if (responseJson.has("errors")) {
                throw new RuntimeException("GraphQL помилки при створенні PersonDTO: " +
                        responseJson.get("errors").toString());
            }

            JsonNode personData = responseJson.get("data").get("createPerson");
            String createdPersonId = personData.get("id").asText();

        } catch (Exception e) {
            throw e;
        }
    }
    private Map<String, Object> convertRequestToPersonInput(CustomerFullInfoRequest request) {
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
}





