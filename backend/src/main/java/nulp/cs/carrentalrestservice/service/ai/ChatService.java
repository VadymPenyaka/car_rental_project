package nulp.cs.carrentalrestservice.service.ai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import nulp.cs.carrentalrestservice.model.request.CarSearchRequest;
import nulp.cs.carrentalrestservice.model.request.ChatSearchRequest;
import nulp.cs.carrentalrestservice.model.response.CarCardResponse;
import nulp.cs.carrentalrestservice.model.response.ChatSearchResponse;
import nulp.cs.carrentalrestservice.service.car.CarService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final CarService carService;

    @Value("${openai.key}")
    private String key;
    private final ObjectMapper objectMapper;


    public List<ChatSearchResponse> recommendFromAvailableCars(ChatSearchRequest request) {
        String userContent = request.getMessage()+getAvailableCars(request.getStart(), request.getEnd());

        try {
            String requestBody = buildRequestBody(userContent);
            String response = sendHttpRequest(requestBody);
            return parseResponseToList(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private String buildRequestBody(String message) {
        JSONObject bodyObj = new JSONObject();
        bodyObj.put("model", "o3-mini");

        JSONArray messagesArr = new JSONArray();

        JSONObject systemMessageObj = new JSONObject();
        systemMessageObj.put("role", "system");
        String system_content = """
                You are a car rental assistant.
                The user will provide a list of available car models with their IDs.
                Based on the user's request, select 1 or 2 cars that best match the request.
                Return the car IDs along with a one sentence brief explanation of the selection for each. 
                Return the response in the following JSON format: {\"cars\": [{\"id\": \"<Car ID>\", \"explanation\": \"<Brief Explanation>\"}]}
                """;
        systemMessageObj.put("content", system_content);

        JSONObject userMessageObj = new JSONObject();
        userMessageObj.put("role", "user");
        userMessageObj.put("content", message);

        messagesArr.put(systemMessageObj);
        messagesArr.put(userMessageObj);

        bodyObj.put("messages", messagesArr);
        return bodyObj.toString();
    }

    private String sendHttpRequest(String body) throws IOException {
        String url = "https://api.openai.com/v1/chat/completions";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Authorization", "Bearer " + key);
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        try (OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream())) {
            writer.write(body);
            writer.flush();
        }

        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

        return response.toString();
    }

    private List<ChatSearchResponse> parseResponseToList(String responseJson) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode = objectMapper.readTree(responseJson);
        JsonNode messageContentNode = rootNode.path("choices").get(0).path("message").path("content");
        String messageContent = messageContentNode.asText();

        JsonNode carsNode = objectMapper.readTree(messageContent).path("cars");

        return objectMapper.readValue(carsNode.toString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, ChatSearchResponse.class));
    }

    private String getAvailableCars (LocalDate start, LocalDate end) {
        CarSearchRequest searchRequest = CarSearchRequest.builder()
                .startDate(start)
                .endDate(end)
                .build();

        List<CarCardResponse> cars = carService.getAllCarsByCriteria(searchRequest);

        StringBuilder sb = new StringBuilder();
        sb.append("; Available cars:");

        for (CarCardResponse car : cars) {
            sb.append(" -ID:").append(car.getId())
                    .append(" -Model:").append(car.getModelName())
                    .append(" -Seats:").append(car.getNumberOfSeats())
                    .append(" -Fuel:").append(car.getFuelType())
                    .append(" -Fuel consumption:").append(car.getFuelConsumption())
                    .append(" -Gearbox:").append(car.getGearboxType())
                    .append(" -Pricing:").append(car.getCarPricing().getUpToThreeDays())
                    .append("day (Up to 3 days)")
                    .append(" -Pledge:").append(car.getCarPricing().getPledge())  // Додаємо інформацію про заставу
                    .append("; ");
        }
        return sb.toString();
    }

}
