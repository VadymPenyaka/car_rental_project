ALTER TABLE admins
    ADD COLUMN is_on_vocation BOOLEAN NOT NULL DEFAULT TRUE,
    ADD COLUMN location_id VARCHAR(36),
    ADD CONSTRAINT location_id_fk
        FOREIGN KEY (location_id)
            REFERENCES locations (id);