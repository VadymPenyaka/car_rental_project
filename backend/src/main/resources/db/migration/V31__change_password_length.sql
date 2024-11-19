ALTER TABLE `car_rental_service`.`admins`
    CHANGE COLUMN `password` `password` VARCHAR(100) NOT NULL ;
ALTER TABLE `car_rental_service`.`customers`
    CHANGE COLUMN `password` `password` VARCHAR(100) NOT NULL ;