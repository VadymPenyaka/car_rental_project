ALTER TABLE `car_rental_service`.`orders_details`
    DROP FOREIGN KEY `orders_details_ibfk_2`;
ALTER TABLE `car_rental_service`.`orders_details`
    DROP COLUMN `customer_id`
;
