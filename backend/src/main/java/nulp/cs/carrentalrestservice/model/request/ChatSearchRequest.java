package nulp.cs.carrentalrestservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatSearchRequest {
    private String message;
    private LocalDate start;
    private LocalDate end;
}
