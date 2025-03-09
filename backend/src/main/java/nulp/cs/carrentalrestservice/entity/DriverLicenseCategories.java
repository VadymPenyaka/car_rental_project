package nulp.cs.carrentalrestservice.entity;

import jakarta.persistence.*;
import lombok.*;
import nulp.cs.carrentalrestservice.model.enumeration.LicenseCategory;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, insertable = false, updatable = false)
    private LicenseCategory category;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private LicenseCategory category;
}
