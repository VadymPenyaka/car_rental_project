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
    private final CarOrderRepository carOrderRepository;
    private final CarPricingRepository carPricingRepository;
    private final LocationRepository locationRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        deleteAllData();
        createLocation();
        createCarPricing();
        createCars();
        createAdmin();
        createCustomer();
        createOrder();
    }

    private void deleteAllData () {
        carOrderRepository.deleteAll();
        customerRepository.deleteAll();
        adminRepository.deleteAll();
        carRepository.deleteAll();
        carPricingRepository.deleteAll();
        locationRepository.deleteAll();
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
                            .admin(adminRepository.findAll().get(0))
                            .customer(customerRepository.findAll().get(0))
                            .car(carRepository.findAll().get(0))
                            .endDate(LocalDate.now().plusDays(1))
                            .startDate(LocalDate.now())
                            .serviceDuration(1)
                            .build()
            );
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
                            .location(locationRepository.findAll().get(0))
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
                            .firstName("Ivan")
                            .sureName("Kovalenko")
                            .passportId("123456789")
                            .email("vadym.penyaka@gmail.com")
                            .birthDate(LocalDate.of(2000, 10, 10))
                            .passportExpiryDate(LocalDate.of(2031, 10, 10))
                            .phoneNumber("0958915290")
                            .build()
            );
        }
    }

    private void createAdmin () {
        if (adminRepository.count()==0) {
            adminRepository.saveAndFlush(
                    Admin.builder()
                            .password("P@ssw0rd")
                            .firstName("Ivan")
                            .lastName("Pip")
                            .email("email@gmail.com")
                            .phoneNumber("0958888222")
                            .build()
            );
        }
    }

    private void createLocation () {
        if (locationRepository.count()==0) {
            locationRepository.saveAndFlush(
                    Location.builder()
                            .address("Gorodotska 21")
                            .city("Lviv")
                            .locationName("Location name")
                            .region("Lviv")
                            .latitude("11111")
                            .longitude("11122")
                            .build()
            );
        }
    }

}
