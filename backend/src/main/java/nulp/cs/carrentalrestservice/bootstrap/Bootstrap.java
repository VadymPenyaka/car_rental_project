package nulp.cs.carrentalrestservice.bootstrap;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.entity.*;
import nulp.cs.carrentalrestservice.model.CarClass;
import nulp.cs.carrentalrestservice.model.FuelType;
import nulp.cs.carrentalrestservice.model.GearboxType;
import nulp.cs.carrentalrestservice.model.OrderStatus;
import nulp.cs.carrentalrestservice.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;

    private final AdminRepository adminRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final CarOrderRepository carOrderRepository;

    private final CarPricingRepository carPricingRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        deleteAllData();
        createCarPricing();
        createCars();
        createAdmin();
        createCustomer();
        createOrderDetail();
        createOrder();
    }

    private void deleteAllData () {
        carOrderRepository.deleteAll();
        customerRepository.deleteAll();
        orderDetailRepository.deleteAll();
        adminRepository.deleteAll();
        carRepository.deleteAll();

    }

    private void createCarPricing() {
        if(carPricingRepository.count()==0) {
            carPricingRepository.saveAndFlush(
                    CarPricing.builder()
                            .pledge(300.0)
                            .upToThreeDays(250.0)
                            .upToTenDays(200.0)
                            .upToMonth(150.0)
                            .moreThenMonth(120.0)
                            .build()
            );
        }
    }

    private void createOrder() {
        if (carOrderRepository.count()==0) {
            carOrderRepository.saveAndFlush(
                    CarOrder.builder()
                            .status(OrderStatus.IN_USE)
                            .orderDetail(orderDetailRepository.findAll().get(0))
                            .admin(adminRepository.findAll().get(0))
                            .customer(customerRepository.findAll().get(0))
                            .build()
            );
        }
    }

    private void createOrderDetail() {
        if (orderDetailRepository.count()==0) {
            OrderDetail orderDetail = OrderDetail.builder()
                    .id(Long.valueOf(123))
                    .totalPrice(123.2)
                    .car(carRepository.findAll().get(0))
                    .build();
            orderDetailRepository.saveAndFlush(orderDetail);
        }
    }

    private void createCars () {
        if (carRepository.count()==0) {
            carRepository.saveAndFlush(
                    Car.builder()
                            .carClass(CarClass.BUSINESS)
                            .brand("BMW")
                            .model("X5")
                            .carPricing(carPricingRepository.findAll().get(0))
                            .fuelConsumption(10)
                            .numberOfSeats(5)
                            .fuelType(FuelType.DIESEL)
                            .gearboxType(GearboxType.AUTOMATIC)
                            .location("Lviv, Gorodotska St. 12")
                            .build()
            );

        }

    }

    private void createCustomer () {
        if (customerRepository.count()==0) {
            customerRepository.saveAndFlush(
                    Customer.builder()
                            .birthDate(LocalDate.now())
                            .passportExpiryDate(LocalDate.now())
                            .firstName("FirstName")
                            .sureName("SureName")
                            .passportId("12345678")
                            .email("vadym.penyaka@gmail.com")
                            .build()
            );
        }
    }

    private void createAdmin () {
        if (adminRepository.count()==0) {
            adminRepository.saveAndFlush(
                    Admin.builder()
                            .password("password")
                            .firstName("FirstName")
                            .lastName("LastName")
                            .build()
            );
        }
    }

}
