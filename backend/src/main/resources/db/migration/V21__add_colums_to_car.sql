ALTER TABLE `car_rental_service`.`cars`
    DROP FOREIGN KEY `cars_ibfk_1`;
ALTER TABLE `car_rental_service`.`cars`
    ADD COLUMN `drive_type` ENUM('FRONT_WD', 'REAR_WD', 'ALL_WD') NOT NULL AFTER `location_id`,
    ADD COLUMN `engine_capacity` DOUBLE NOT NULL AFTER `drive_type`,
    CHANGE COLUMN `car_pricing_id` `car_pricing_id` BIGINT NOT NULL ;
ALTER TABLE `car_rental_service`.`cars`
    ADD CONSTRAINT `cars_ibfk_1`
        FOREIGN KEY (`car_pricing_id`)
            REFERENCES `car_rental_service`.`car_pricing` (`id`);
