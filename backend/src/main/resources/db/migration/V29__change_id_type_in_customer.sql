ALTER TABLE `car_rental_service`.`car_orders`
    DROP FOREIGN KEY `FKbgqbf5peani5uhmsrqlfxxtae`;
ALTER TABLE `car_rental_service`.`customers`
    CHANGE COLUMN `id` `id` VARCHAR(36) NOT NULL ;


ALTER TABLE `car_rental_service`.`car_orders`
    CHANGE COLUMN `customer_id` `customer_id` VARCHAR(36) NOT NULL ;
ALTER TABLE `car_rental_service`.`car_orders`
    ADD CONSTRAINT `FKbgqbf5peani5uhmsrqlfxxtae`
        FOREIGN KEY (`customer_id`)
            REFERENCES `car_rental_service`.`customers` (`id`);
