# CREATE SCHEMA IF NOT EXISTS `car_rental_service`;
USE `car_rental_service`;

DROP TABLE IF EXISTS `customers`;
CREATE TABLE `customers` (
                             `birth_date` date NOT NULL,
                             `expiry_date` date NOT NULL,
                             `id` bigint NOT NULL,
                             `passport_id` varchar(9) COLLATE utf8mb4_unicode_ci NOT NULL,
                             `first_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
                             `last_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
                             `sure_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `admins`;
CREATE TABLE `admins` (
                          `id` bigint NOT NULL,
                          `first_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
                          `last_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
                          `password` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `car_pricing`;
CREATE TABLE `car_pricing` (
                               `more_then_month` double NOT NULL,
                               `pledge` double NOT NULL,
                               `up_to_month` double NOT NULL,
                               `up_to_ten_days` double NOT NULL,
                               `up_to_three_days` double NOT NULL,
                               `id` bigint NOT NULL,
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `cars`;
CREATE TABLE `cars` (
                        `fuel_consumption` int NOT NULL,
                        `fuel_type` tinyint NOT NULL,
                        `is_available` varchar(5) COLLATE utf8mb4_unicode_ci NOT NULL,
                        `number_of_seats` int NOT NULL,
                        `price_per_day` double NOT NULL,
                        `car_pricing_id` bigint DEFAULT NULL,
                        `id` bigint NOT NULL,
                        `brand` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
                        `model` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
                        `location` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                        `car_class` enum('CHEAP','COMFORT','BUSINESS','MINIBUS') COLLATE utf8mb4_unicode_ci NOT NULL,
                        `gearbox_type` tinyint NOT NULL,
                        PRIMARY KEY (`id`),
                        FOREIGN KEY (`car_pricing_id`) REFERENCES `car_pricing` (`id`),
                        CONSTRAINT `cars_chk_1` CHECK ((`fuel_type` between 0 and 3))
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `orders_details`;
CREATE TABLE `orders_details` (
                                  `drop_off_date` date NOT NULL,
                                  `number_of_days` int NOT NULL,
                                  `pick_up_date` date NOT NULL,
                                  `total_price` double NOT NULL,
                                  `car_id` bigint NOT NULL,
                                  `customer_id` bigint NOT NULL,
                                  `id` bigint NOT NULL,
                                  `comment` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                  `drop_off_location` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                                  `pick_up_location` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                                  PRIMARY KEY (`id`),
                                  FOREIGN KEY (`car_id`) REFERENCES `cars` (`id`),
                                  FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `car_orders`;
CREATE TABLE `car_orders` (
                              `admin_id` bigint NOT NULL,
                              `id` bigint NOT NULL,
                              `order_detail_id` bigint DEFAULT NULL,
                              `orderStatus` enum('APPROVED','IN_USE','RETURNED','DAMAGED','PAID') COLLATE utf8mb4_unicode_ci NOT NULL,
                              PRIMARY KEY (`id`),
                              FOREIGN KEY (`admin_id`) REFERENCES `admins` (`id`),
                              FOREIGN KEY (`order_detail_id`) REFERENCES `orders_details` (`id`)
) ENGINE=InnoDB;


