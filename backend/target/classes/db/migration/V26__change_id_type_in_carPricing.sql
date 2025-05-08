ALTER TABLE cars
    DROP CONSTRAINT IF EXISTS cars_car_pricing_id_fkey;

ALTER TABLE cars
    DROP COLUMN IF EXISTS car_pricing_id;

ALTER TABLE car_pricing
    ALTER COLUMN id TYPE VARCHAR(36);

ALTER TABLE cars
    ADD COLUMN car_pricing_id VARCHAR(36) NOT NULL;

ALTER TABLE cars
    ADD CONSTRAINT car_pricing_id_fk
        FOREIGN KEY (car_pricing_id)
            REFERENCES car_pricing (id);
