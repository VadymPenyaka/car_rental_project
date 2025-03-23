package nulp.cs.carrentalrestservice.repository;

import nulp.cs.carrentalrestservice.entity.CarOrder;
import nulp.cs.carrentalrestservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface CustomerRepository extends JpaRepository <Customer, UUID> {
    @Query("SELECT c FROM customers c WHERE c.person.id = :personId")
    Optional<Customer> findCustomerByPersonId (@Param("personId") UUID personId);

    @Query("SELECT o FROM CarOrder o WHERE o.customer.id = :customerId")
    List<CarOrder> getAllCustomerOrders (UUID customerId);
}

