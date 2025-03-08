package nulp.cs.carrentalrestservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "driver_license_categories")
public class DriverLicenseCategories {

    @EmbeddedId
    private DriverLicenseCategoryId id;

    @ManyToOne
    @MapsId("licenseId")
    @JoinColumn(nullable = false, insertable = false, updatable = false)
    private DriverLicense license;

    @Column(nullable = false, insertable = false, updatable = false)
    private String category;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;
}

@Embeddable
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class DriverLicenseCategoryId implements Serializable {
    @Column(name = "license_id")
    private Long licenseId;

    @Column(name = "category")
    private String category;
}
