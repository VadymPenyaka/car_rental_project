DROP TABLE IF EXISTS `car_rental_service`.`locations`;
CREATE TABLE `car_rental_service`.`locations` (
    `id` BIGINT NOT NULL,
    `location_name` VARCHAR(50) NOT NULL,
    `region` VARCHAR(50) NOT NULL,
    `city` VARCHAR(50) NOT NULL,
    `address` VARCHAR(100) NOT NULL,
    `latitude` VARCHAR(50) NOT NULL,
    `longitude` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`id`)
);
