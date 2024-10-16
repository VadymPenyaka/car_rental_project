ALTER TABLE `car_rental_service`.`cars_maintenance`
    DROP FOREIGN KEY `cars_maintenance_ibfk_2`;
ALTER TABLE `car_rental_service`.`car_orders`
    DROP FOREIGN KEY `schedule_id`;

ALTER TABLE `car_rental_service`.`cars_maintenance`
    CHANGE COLUMN `schedule_id` `schedule_id` VARCHAR(36) NOT NULL;
ALTER TABLE `car_rental_service`.`car_orders`
    CHANGE COLUMN `schedule_id` `schedule_id` VARCHAR(36) NOT NULL ;
ALTER TABLE `car_rental_service`.`car_schedule`
    CHANGE COLUMN `id` `id` VARCHAR(36) NOT NULL;

ALTER TABLE `car_rental_service`.`cars_maintenance`
    ADD CONSTRAINT `cars_maintenance_ibfk_2`
        FOREIGN KEY (`schedule_id`)
            REFERENCES `car_rental_service`.`car_schedule` (`id`);

ALTER TABLE `car_rental_service`.`car_orders`
    ADD CONSTRAINT `schedule_id`
        FOREIGN KEY (`schedule_id`)
            REFERENCES `car_rental_service`.`car_schedule` (`id`);
