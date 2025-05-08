CREATE TYPE drive_type_enum AS ENUM ('FRONT_WD', 'REAR_WD', 'ALL_WD');

ALTER TABLE cars
    ADD COLUMN drive_type drive_type_enum  NOT NULL,
    ADD COLUMN engine_capacity DOUBLE PRECISION NOT NULL;

ALTER TABLE cars
    ALTER COLUMN car_pricing_id TYPE BIGINT;

ALTER TABLE cars
    ADD CONSTRAINT car_id_fk
        FOREIGN KEY (car_pricing_id)
            REFERENCES car_pricing (id);
