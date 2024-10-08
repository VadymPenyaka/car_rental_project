ALTER TABLE `car_rental_service`.`car_orders`
    ADD COLUMN `start_date` DATE NOT NULL,
    ADD COLUMN `end_date` DATE NOT NULL,
    ADD COLUMN `service_duration` INT NOT NULL,
    ADD COLUMN `total_price` FLOAT NOT NULL,
    ADD COLUMN `comment` VARCHAR(100) NULL;