ALTER TABLE `car_rental_service`.`admins`
    ADD COLUMN `role` ENUM('USER', 'ADMIN', 'SYS_ADMIN') NOT NULL AFTER `password`;