package nulp.cs.carrentalrestservice.repository;

import nulp.cs.carrentalrestservice.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DocumentRepository extends JpaRepository <Document, UUID> {
}
