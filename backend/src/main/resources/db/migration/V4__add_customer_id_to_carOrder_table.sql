ALTER TABLE `car_rental_service`.`car_orders`
ADD COLUMN `customer_id` BIGINT NOT NULL,
ADD INDEX `customer_id_idx` (`customer_id` ASC) VISIBLE;
;
ALTER TABLE `car_rental_service`.`car_orders`
ADD CONSTRAINT `customer_id`
  FOREIGN KEY (`customer_id`)
  REFERENCES `car_rental_service`.`customers` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
