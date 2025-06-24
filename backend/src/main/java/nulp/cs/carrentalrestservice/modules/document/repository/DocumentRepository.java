package nulp.cs.carrentalrestservice.modules.document.repository;

import nulp.cs.carrentalrestservice.modules.document.enity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface DocumentRepository extends JpaRepository <Document, UUID> {
    @Query(value = "SELECT d.* FROM document d " +
            "JOIN car_order co ON d.order_id = co.id " +
            "WHERE co.person_id = CAST(:personId AS varchar)",
            nativeQuery = true)
    List<Document> findUnsignedDocumentsByPersonId(@Param("personId") String personId);
}
