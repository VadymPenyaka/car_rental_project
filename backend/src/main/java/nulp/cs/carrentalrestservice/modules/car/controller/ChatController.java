package nulp.cs.carrentalrestservice.modules.car.controller;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.shared.dto.request.ChatSearchRequest;
import nulp.cs.carrentalrestservice.shared.dto.response.ChatSearchResponse;
import nulp.cs.carrentalrestservice.modules.car.serivce.AICarSearchService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ChatController.BASE_PATH)
public class ChatController {
    public final static String BASE_PATH = "api/v1/chat";
    private final AICarSearchService AICarSearchService;

    @PostMapping
    public List<ChatSearchResponse> searchCar (@RequestBody ChatSearchRequest request) {
        return AICarSearchService.recommendFromAvailableCars(request);
    }
}
