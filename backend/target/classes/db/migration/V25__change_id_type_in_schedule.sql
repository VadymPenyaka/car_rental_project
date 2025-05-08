ALTER TABLE cars
    DROP CONSTRAINT location_id;
ALTER TABLE locations
    ALTER COLUMN id TYPE VARCHAR(36);

ALTER TABLE cars
    ALTER COLUMN location_id TYPE VARCHAR(36);
ALTER TABLE cars
    ADD CONSTRAINT location_id_fk
        FOREIGN KEY (location_id)
            REFERENCES locations (id);


