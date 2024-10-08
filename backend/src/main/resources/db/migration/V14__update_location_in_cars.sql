ALTER TABLE `car_rental_service`.`cars`
    DROP COLUMN `location`,
    ADD COLUMN `location_id` BIGINT NOT NULL,
    ADD INDEX `location_id_idx` (`location_id` ASC) VISIBLE;
;
ALTER TABLE `car_rental_service`.`cars`
    ADD CONSTRAINT `location_id`
        FOREIGN KEY (`location_id`)
            REFERENCES `car_rental_service`.`locations` (`id`)
;
