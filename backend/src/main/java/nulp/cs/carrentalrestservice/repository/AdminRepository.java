package nulp.cs.carrentalrestservice.repository;

import nulp.cs.carrentalrestservice.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin, UUID> {

}
