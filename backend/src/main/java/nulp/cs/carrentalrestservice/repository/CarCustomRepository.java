package nulp.cs.carrentalrestservice.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.entity.Brand;
import nulp.cs.carrentalrestservice.entity.Car;
import nulp.cs.carrentalrestservice.entity.CarSchedule;
import nulp.cs.carrentalrestservice.entity.Location;
import nulp.cs.carrentalrestservice.model.request.CarSearchRequest;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;
@Repository
@RequiredArgsConstructor
public class CarCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Car> getAllCarsByCriteria(CarSearchRequest carSearchRequest) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> cq = cb.createQuery(Car.class);
        Root<Car> carRoot = cq.from(Car.class);

        Join<Car, Model> modelJoin = carRoot.join("model", JoinType.LEFT);
        Join<Car, Location> locationJoin = carRoot.join("location", JoinType.LEFT);
        Join<Model, Brand> brandJoin = modelJoin.join("brandName", JoinType.LEFT);
        Join<Car, CarSchedule> carScheduleJoin = carRoot.join("carSchedules", JoinType.LEFT);

        Predicate predicates = cb.conjunction();

        if (carSearchRequest.getCity() != null) {
            predicates = cb.and(predicates, cb.equal(locationJoin.get("city"), carSearchRequest.getCity()));
        }
        if (carSearchRequest.getCarClass() != null) {
            predicates = cb.and(predicates, cb.equal(carRoot.get("carClass"), carSearchRequest.getCarClass().name()));
        }
        if (carSearchRequest.getFuelType() != null) {
            predicates = cb.and(predicates, cb.equal(carRoot.get("fuelType"), carSearchRequest.getFuelType().name()));
        }
        if (carSearchRequest.getGearboxType() != null) {
            predicates = cb.and(predicates, cb.equal(carRoot.get("gearboxType"), carSearchRequest.getGearboxType().name()));
        }
        if (carSearchRequest.getBrand() != null) {
            predicates = cb.and(predicates, cb.equal(brandJoin.get("name"), carSearchRequest.getBrand()));
        }

        if (carSearchRequest.getMaxPrice() != null) {
            predicates = cb.and(predicates, cb.lessThanOrEqualTo(carRoot.get("pricePerMonth"), carSearchRequest.getMaxPrice()));
        }
        if (carSearchRequest.getMinPrice() != null) {
            predicates = cb.and(predicates, cb.greaterThanOrEqualTo(carRoot.get("pricePerMonth"), carSearchRequest.getMinPrice()));
        }

        if (carSearchRequest.getStartDate() != null && carSearchRequest.getEndDate() != null) {
            Subquery<Long> subquery = cq.subquery(Long.class);
            Root<CarSchedule> carScheduleSubqueryRoot = subquery.from(CarSchedule.class);

            subquery.select(cb.literal(1L))
                    .where(
                            cb.equal(carScheduleSubqueryRoot.get("car").get("id"), carRoot.get("id")),
                            cb.greaterThanOrEqualTo(carScheduleSubqueryRoot.get("endDate"), carSearchRequest.getStartDate()),
                            cb.lessThanOrEqualTo(carScheduleSubqueryRoot.get("startDate"), carSearchRequest.getEndDate())
                    );

            Predicate datePredicate = cb.not(cb.exists(subquery));

            predicates = cb.and(predicates, datePredicate);
        }

        cq.where(predicates);
        cq.select(carRoot);

        TypedQuery<Car> query = entityManager.createQuery(cq);
        return query.getResultList();
    }
}
