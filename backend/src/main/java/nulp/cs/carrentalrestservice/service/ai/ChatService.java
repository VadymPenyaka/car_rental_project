package nulp.cs.carrentalrestservice.service.ai;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.model.dto.CarDTO;
import nulp.cs.carrentalrestservice.model.request.CarSearchRequest;
import nulp.cs.carrentalrestservice.model.response.CarCardResponse;
import nulp.cs.carrentalrestservice.service.car.CarService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    public HttpResponse<String> getResponse (String userMessage) throws IOException, InterruptedException {
        String systemContent = "You are a car rental assistant. The user will provide a list of available car models with their IDs.Based on the user's request, select 1-2 cars that best match the request. Return only the selected cars IDs.";
        String requestBody = buildRequestBody(userMessage, systemContent);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + key)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
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

        for (CarCardResponse car:cars) {
            sb.append(car.getBrandName())
                    .append(" ")
                    .append(car.getModelName())
                    .append(" id: ")
                    .append(car.getId())
                    .append("\n");
        }

        return sb.toString();
    }

}
