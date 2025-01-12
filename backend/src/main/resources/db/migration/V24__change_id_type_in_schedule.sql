ALTER TABLE cars_maintenance
    DROP CONSTRAINT cars_services_schedule_id_fkey;
ALTER TABLE car_orders
    DROP CONSTRAINT schedule_id;

ALTER TABLE cars_maintenance
    ALTER COLUMN schedule_id TYPE VARCHAR(36);
ALTER TABLE car_orders
    ALTER COLUMN schedule_id TYPE VARCHAR(36);
ALTER TABLE car_schedule
    ALTER COLUMN id TYPE VARCHAR(36);

ALTER TABLE cars_maintenance
    ADD CONSTRAINT schedule_id_fk
        FOREIGN KEY (schedule_id)
            REFERENCES car_schedule (id);

ALTER TABLE car_orders
    ADD CONSTRAINT schedule_id_fk
        FOREIGN KEY (schedule_id)
            REFERENCES car_schedule (id);
