ALTER TABLE cars_maintenance
    DROP COLUMN car_id;
;

ALTER TABLE car_orders
    DROP CONSTRAINT fk_car_id;
ALTER TABLE car_orders
    DROP COLUMN car_id;
;
