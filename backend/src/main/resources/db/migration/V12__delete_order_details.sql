ALTER TABLE `car_rental_service`.`car_orders`
    DROP FOREIGN KEY `car_orders_ibfk_2`;
ALTER TABLE `car_rental_service`.`car_orders`
    DROP COLUMN `order_detail_id`;

DROP TABLE IF EXISTS orders_details;

ALTER TABLE `car_rental_service`.`car_orders`
    ADD COLUMN `car_id` BIGINT NOT NULL AFTER `status`,
    ADD INDEX `car_id_idx` (`car_id` ASC) VISIBLE;
;
ALTER TABLE `car_rental_service`.`car_orders`
    ADD CONSTRAINT `car_id`
        FOREIGN KEY (`car_id`)
            REFERENCES `car_rental_service`.`cars` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION;
