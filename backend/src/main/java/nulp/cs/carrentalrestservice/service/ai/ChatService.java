package nulp.cs.carrentalrestservice.service.ai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import nulp.cs.carrentalrestservice.model.request.CarSearchRequest;
import nulp.cs.carrentalrestservice.model.request.UserChatRequest;
import nulp.cs.carrentalrestservice.model.response.CarCardResponse;
import nulp.cs.carrentalrestservice.model.response.ChatSearchResponse;
import nulp.cs.carrentalrestservice.service.car.CarService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
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

    @SneakyThrows
    public List<ChatSearchResponse> getResponse (UserChatRequest userRequest) {
        String systemContent = "You are a car rental assistant. The user will provide a list of available car models with their IDs.Based on the user's request, select 1 or 2 cars that best match the request. Return the car IDs along with a one sentence brief explanation of the selection for each. Return the response in the following JSON format: {\"cars\": [{\"id\": \"<Car ID>\", \"explanation\": \"<Brief Explanation>\"}]}";
        String messageWithAvailableCars = userRequest.getMessage() +
                getAvailableCars(userRequest.getStart(), userRequest.getEnd());

        String requestBody = buildRequestBody(messageWithAvailableCars, systemContent);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + key)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        return parseResponse(client.send(request, HttpResponse.BodyHandlers.ofString()));
    }


    private List<ChatSearchResponse> parseResponse (HttpResponse<String> httpResponse) {
        String response = httpResponse.body();

        JsonNode rootNode = null;
        try {
            rootNode = objectMapper.readTree(response);
            JsonNode messageContentNode = rootNode.path("choices").get(0).path("message").path("content");
            String messageContent = messageContentNode.asText();
            JsonNode carsNode = objectMapper.readTree(messageContent).path("cars");
            return objectMapper.readValue(carsNode.toString(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, ChatSearchResponse.class));


        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildRequestBody(String userContent, String systemContent) {
        return """
        {
            "model": "o3-mini",
            "messages": [
                {
                    "role": "system",
                    "content": "%s"
                },
                {
                    "role": "user",
                    "content": "%s"
                }
            ]
        }
        """.formatted(systemContent.replace("\"", "\\\""), userContent.replace("\"", "\\\""));
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
