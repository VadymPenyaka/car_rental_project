ALTER TABLE car_schedule
    DROP CONSTRAINT fk_car_schedule_car;

ALTER TABLE cars
    ALTER COLUMN id TYPE VARCHAR(36);

ALTER TABLE car_schedule
    ALTER COLUMN car_id TYPE VARCHAR(36);
ALTER TABLE car_schedule
    ADD CONSTRAINT car_id_fk
        FOREIGN KEY (car_id)
            REFERENCES cars (id)
            ON DELETE CASCADE;