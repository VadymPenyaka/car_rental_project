package nulp.cs.carrentalrestservice.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nulp.cs.carrentalrestservice.entity.Document;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverLicensesDTO {
    private UUID id;

    private String category;

    private LocalDate issueDate;

    private LocalDate expirationDate;

    private String issuedBy;

    private String series;

    private String documentNumber;

    private Document document;
}
