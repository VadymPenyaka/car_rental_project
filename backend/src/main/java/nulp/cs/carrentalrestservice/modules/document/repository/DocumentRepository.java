package nulp.cs.carrentalrestservice.modules.document.repository;

import nulp.cs.carrentalrestservice.modules.document.enity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface DocumentRepository extends JpaRepository <Document, UUID> {
}
