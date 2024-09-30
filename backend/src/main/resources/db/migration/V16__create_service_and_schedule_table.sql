ALTER TABLE `car_rental_service`.`car_orders`
    DROP COLUMN `end_date`,
    DROP COLUMN `start_date`;



CREATE TABLE IF NOT EXISTS car_schedule (
    `id` BIGINT PRIMARY KEY,
    `car_id` BIGINT NOT NULL,
    `start_date` DATE NOT NULL,
    `end_date` DATE NOT NULL,
    `status` ENUM('BOOKED', 'UNDER_SERVICE') NOT NULL,
    CONSTRAINT fk_car_schedule_car FOREIGN KEY (car_id) REFERENCES cars(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS cars_services (
    `id` BIGINT PRIMARY KEY,
    `schedule_id` BIGINT NOT NULL,
    `car_id` BIGINT NOT NULL,
    `description` VARCHAR(100) NOT NULL,
    `price` DOUBLE NOT NULL,
    FOREIGN KEY (`car_id`) REFERENCES cars(`id`),
    FOREIGN KEY  (`schedule_id`) REFERENCES car_schedule(`id`)
);

ALTER TABLE `car_rental_service`.`car_orders`
    ADD COLUMN `schedule_id` BIGINT NOT NULL;

ALTER TABLE `car_rental_service`.`car_orders`
    ADD CONSTRAINT `schedule_id`
        FOREIGN KEY (`schedule_id`)
            REFERENCES `car_rental_service`.`car_schedule` (`id`);



