package nulp.cs.carrentalrestservice.entity;

import jakarta.persistence.*;
import lombok.*;
import nulp.cs.carrentalrestservice.model.enumeration.Role;
import nulp.cs.carrentalrestservice.security.SensitiveDataConverter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.HashSet;
import java.util.Set;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(36)")
    private Role role;
    @Column(nullable = false, unique = true)
    @Convert(converter = SensitiveDataConverter.class)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @Convert(converter = SensitiveDataConverter.class)
    private String sureName;
    @Column(nullable = false)
    @Convert(converter = SensitiveDataConverter.class)
    private String firstName;
    @Convert(converter = SensitiveDataConverter.class)
    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @OneToOne(mappedBy = "person", cascade = CascadeType.REMOVE)
    private Admin admin;
    @OneToMany(mappedBy = "person", cascade = CascadeType.REMOVE)
    private Set<CarOrder> orders = new HashSet<>();
}
