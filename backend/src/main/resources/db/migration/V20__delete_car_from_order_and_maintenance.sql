ALTER TABLE `car_rental_service`.`cars_maintenance`
    DROP FOREIGN KEY `cars_maintenance_ibfk_1`;
ALTER TABLE `car_rental_service`.`cars_maintenance`
    DROP COLUMN `car_id`,
    DROP INDEX `car_id` ;
;

ALTER TABLE `car_rental_service`.`car_orders`
    DROP FOREIGN KEY `car_id`;
ALTER TABLE `car_rental_service`.`car_orders`
    DROP COLUMN `car_id`,
    DROP INDEX `car_id_idx` ;
;
