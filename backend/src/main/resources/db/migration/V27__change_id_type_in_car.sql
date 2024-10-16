ALTER TABLE `car_rental_service`.`car_schedule`
    DROP FOREIGN KEY `fk_car_schedule_car`;

ALTER TABLE `car_rental_service`.`cars`
    CHANGE COLUMN `id` `id` VARCHAR(36) NOT NULL ;

ALTER TABLE `car_rental_service`.`car_schedule`
    CHANGE COLUMN `car_id` `car_id` VARCHAR(36) NOT NULL ;
ALTER TABLE `car_rental_service`.`car_schedule`
    ADD CONSTRAINT `fk_car_schedule_car`
        FOREIGN KEY (`car_id`)
            REFERENCES `car_rental_service`.`cars` (`id`)
            ON DELETE CASCADE;