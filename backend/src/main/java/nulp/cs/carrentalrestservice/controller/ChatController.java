package nulp.cs.carrentalrestservice.controller;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.model.request.ChatSearchRequest;
import nulp.cs.carrentalrestservice.model.response.ChatSearchResponse;
import nulp.cs.carrentalrestservice.service.ai.ChatService;
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
    private final ChatService chatService;

    @PostMapping
    public List<ChatSearchResponse> searchCar (@RequestBody ChatSearchRequest request) {
        return chatService.recommendFromAvailableCars(request);
    }
}
