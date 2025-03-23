package nulp.cs.carrentalrestservice.util;

import nulp.cs.carrentalrestservice.entity.*;
import nulp.cs.carrentalrestservice.model.enumeration.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Component
public class CarGenerator {

    private final Random RANDOM = new Random();

    public final List<Brand> brands = List.of(
            Brand.builder().name("Audi").build(),
            Brand.builder().name("BMW").build(),
            Brand.builder().name("Toyota").build(),
            Brand.builder().name("Volkswagen").build(),
            Brand.builder().name("Hyundai").build(),
            Brand.builder().name("Ford").build(),
            Brand.builder().name("Honda").build(),
            Brand.builder().name("Nissan").build(),
            Brand.builder().name("Tesla").build()
    );


    public List<Car> generateCarList (int size) {
        List<Car> cars = new ArrayList<>();

        for (int i =0; i< size; i++) {
            cars.add(generateCar());
        }

        return cars;
    }

    public Car generateCar () {
        System.out.println("Car generating\n\n\n\n");
        return Car.builder()
                .vin(generateVin())
                .bodyType(randomEnum(BodyType.class))
                .number(generateCarNumber())
                .color(randomColor())
                .carClass(randomEnum(CarClass.class))
                .fuelConsumption(RANDOM.nextInt(5, 20))
                .numberOfSeats(RANDOM.nextInt(2, 7))
                .fuelType(randomEnum(FuelType.class))
                .gearboxType(randomEnum(GearboxType.class))
                .driveType(randomEnum(DriveType.class))
                .engineCapacity(RANDOM.nextDouble(1.0, 5.0))
                .fuelTankCapacity(RANDOM.nextInt(40, 80))
                .trunkCapacity(RANDOM.nextInt(200, 600))
                .licenseCategory(randomEnum(LicenseCategory.class))
                .requiredExperience(RANDOM.nextInt(0, 5))
                .model(generateModel())
                .location(generateLocation())
                .carPricing(generateCarPricing())
                .build();
    }

    public Model generateModel () {
        List<String> FEATURES = List.of(
                "Панорамний дах ", "Камера заднього виду ",
                "Обігрів сидінь ", "Масажні сидіння ", "Безключовий доступ ",
                "Розширена мультимедіа система ", "Адаптивний круїз-контроль "
        );

        List<String> DRIVE_EXPERIENCES = List.of(
                "Ідеально для міського руху ", "Чудово підходить для довгих подорожей ",
                "Потужний та швидкий на трасі ", "Економічний для щоденного використання "
        );

        List<Brand> brands = generateBrands();

        return Model.builder()
                .brandName((Brand) getRandom(brands))
                .year(RANDOM.nextInt(2010, 2025))
                .modelName(generateModelName())
                .description(getRandom(FEATURES).toString()
                        + getRandom(DRIVE_EXPERIENCES).toString())
                .build();
    }

    public List<Brand> generateBrands () {
        return brands;
    }

    public Location generateLocation() {
        List<String> locationNames = List.of(
                "Автоцентр Київ", "Оренда авто Львів", "Станція Прокат Одеса",
                "Рент Кар Дніпро", "АвтоПарк Харків"
        );

        List<String> regions = List.of(
                "Київська область", "Львівська область", "Одеська область",
                "Дніпропетровська область", "Харківська область"
        );

        List<String> cities = List.of(
                "Київ", "Львів", "Одеса", "Дніпро", "Харків"
        );

        List<String> addresses = List.of(
                "вул. Хрещатик, 10", "просп. Свободи, 15", "вул. Дерибасівська, 7",
                "просп. Дмитра Яворницького, 22", "вул. Сумська, 30"
        );

        List<String> latitudes = List.of(
                "50.4501", "49.8397", "46.4825", "48.4647", "49.9935"
        );

        List<String> longitudes = List.of(
                "30.5234", "24.0297", "30.7233", "35.0421", "36.2304"
        );

        return Location.builder()
                .locationName(getRandom(locationNames).toString())
                .region(getRandom(regions).toString())
                .city(getRandom(cities).toString())
                .address(getRandom(addresses).toString())
                .latitude(getRandom(latitudes).toString())
                .longitude(getRandom(longitudes).toString())
                .build();
    }

    public CarPricing generateCarPricing() {
        return CarPricing.builder()
                .pledge(generateRandomPrice(500, 5000))
                .upToThreeDays(generateRandomPrice(30, 100))
                .upToTenDays(generateRandomPrice(25, 90))
                .upToMonth(generateRandomPrice(20, 80))
                .moreThenMonth(generateRandomPrice(15, 70))
                .build();
    }

    private Double generateRandomPrice(double min, double max) {
        return min + (max - min) * RANDOM.nextDouble();
    }

    private String generateModelName () {
        return "A"+RANDOM.nextInt(1, 9);
    }


    private String generateVin() {
        return "1HGCM82633A" + RANDOM.nextInt(100000, 999999);
    }

    private String generateCarNumber() {
        return "AA" + RANDOM.nextInt(1000, 9999) + "BB";
    }

    private String randomColor() {
        String[] colors = {"Red", "Blue", "Black", "White", "Gray", "Silver"};
        return colors[RANDOM.nextInt(colors.length)];
    }

    private <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        T[] values = clazz.getEnumConstants();
        return values[RANDOM.nextInt(values.length)];
    }

    private <Element> Object getRandom(List<Element> list) {
        return list.get(RANDOM.nextInt(list.size()));
    }
}
