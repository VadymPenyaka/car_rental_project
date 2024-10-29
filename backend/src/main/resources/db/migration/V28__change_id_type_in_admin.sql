ALTER TABLE `car_rental_service`.`car_orders`
    DROP FOREIGN KEY `FK2qoflwv9pycftcbkom7m8w341`;

ALTER TABLE `car_rental_service`.`admins`
    CHANGE COLUMN `id` `id` VARCHAR(36) NOT NULL ;

ALTER TABLE `car_rental_service`.`car_orders`
    CHANGE COLUMN `admin_id` `admin_id` VARCHAR(36) NOT NULL ;
ALTER TABLE `car_rental_service`.`car_orders`
    ADD CONSTRAINT `FK2qoflwv9pycftcbkom7m8w341`
        FOREIGN KEY (`admin_id`)
            REFERENCES `car_rental_service`.`admins` (`id`);
