ALTER TABLE `car_rental_service`.`cars`
    DROP FOREIGN KEY `location_id`;
ALTER TABLE `car_rental_service`.`locations`
    CHANGE COLUMN `id` `id` VARCHAR(36) NOT NULL ;

ALTER TABLE `car_rental_service`.`cars`
    CHANGE COLUMN `location_id` `location_id` VARCHAR(36) NOT NULL ;
ALTER TABLE `car_rental_service`.`cars`
    ADD CONSTRAINT `location_id`
        FOREIGN KEY (`location_id`)
            REFERENCES `car_rental_service`.`locations` (`id`);


