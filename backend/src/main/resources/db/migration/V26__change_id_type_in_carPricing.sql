ALTER TABLE `car_rental_service`.`cars`
    DROP FOREIGN KEY `cars_ibfk_1`;

ALTER TABLE `car_rental_service`.`car_pricing`
    CHANGE COLUMN `id` `id` VARCHAR(36) NOT NULL ;

ALTER TABLE `car_rental_service`.`cars`
    CHANGE COLUMN `car_pricing_id` `car_pricing_id` VARCHAR(36) NOT NULL ;
ALTER TABLE `car_rental_service`.`cars`
    ADD CONSTRAINT `cars_ibfk_1`
        FOREIGN KEY (`car_pricing_id`)
            REFERENCES `car_rental_service`.`car_pricing` (`id`);
