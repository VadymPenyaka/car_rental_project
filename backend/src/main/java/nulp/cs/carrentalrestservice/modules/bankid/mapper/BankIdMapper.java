package nulp.cs.carrentalrestservice.modules.bankid.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nulp.cs.carrentalrestservice.modules.bankid.dto.DriverLicensesDTO;
import nulp.cs.carrentalrestservice.modules.bankid.dto.PassportDTO;
import nulp.cs.carrentalrestservice.shared.dto.request.PersonalInfoRequest;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class BankIdMapper {

    private final ObjectMapper objectMapper;

    public BankIdMapper() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    /**
     * Converts PersonalInfoRequest to a Map suitable for GraphQL mutation
     * @param request PersonalInfoRequest to convert
     * @return Map containing person input data for GraphQL
     */
    public Map<String, Object> convertRequestToPersonInput(PersonalInfoRequest request) {
        Map<String, Object> personInput = new HashMap<>();
        personInput.put("id", request.getPersonId().toString());

        if (request.getPassport() != null) {
            Map<String, Object> passportMap = objectMapper.convertValue(request.getPassport(), Map.class);
            passportMap.remove("id");
            personInput.put("passport", passportMap);
        }

        if (request.getDriverLicense() != null) {
            Map<String, Object> licenseMap = objectMapper.convertValue(request.getDriverLicense(), Map.class);
            licenseMap.remove("id");

            if (licenseMap.containsKey("categories") && licenseMap.get("categories") instanceof List) {
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> categories = (List<Map<String, Object>>) licenseMap.get("categories");
                for (Map<String, Object> category : categories) {
                    category.remove("id");
                }
            }

            personInput.put("driverLicense", licenseMap);
        }

        return personInput;
    }

    /**
     * Converts JsonNode from GraphQL response to PersonalInfoRequest
     * @param personData JsonNode containing person data from GraphQL response
     * @return PersonalInfoRequest object
     * @throws Exception if conversion fails
     */
    public PersonalInfoRequest convertJsonToPersonalInfoRequest(JsonNode personData) throws Exception {
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

    /**
     * Creates GraphQL query variables for person lookup
     * @param personId UUID of the person to query
     * @return Map containing GraphQL query and variables
     */
    public Map<String, Object> createPersonQueryVariables(UUID personId) {
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
}