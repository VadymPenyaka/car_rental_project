ALTER TABLE cars
    DROP COLUMN location,
    ADD COLUMN location_id BIGINT NOT NULL;
;
ALTER TABLE cars
    ADD CONSTRAINT location_id
        FOREIGN KEY (location_id)
            REFERENCES locations (id)
;
