package nulp.cs.carrentalrestservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import nulp.cs.carrentalrestservice.annotation.ValidEmail;
import nulp.cs.carrentalrestservice.annotation.ValidPhoneNumber;
import nulp.cs.carrentalrestservice.model.enumeration.Role;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "VARCHAR(36)")
    private UUID id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, length = 60)
    private String password;
    @Column(nullable = false, length = 50)
    private String sureName;
    @Column(nullable = false, length = 50)
    private String firstName;
    @Column(nullable = false, length = 12, unique = true)
    private String phoneNumber;
}
