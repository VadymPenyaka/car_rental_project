ALTER TABLE `car_rental_service`.`cars`
    CHANGE COLUMN `fuel_type` `fuel_type` ENUM('DIESEL', 'GASOLINE', 'ELECTRIC', 'GAS_OIL_GASOLINE') NOT NULL,
    CHANGE COLUMN `gearbox_type` `gearbox_type` ENUM('AUTO', 'MANUAL') NOT NULL ;

