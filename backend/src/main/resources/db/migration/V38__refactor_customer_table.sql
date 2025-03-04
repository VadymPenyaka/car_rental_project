ALTER TABLE customers
    DROP COLUMN passport_id,
    DROP COLUMN birth_date,
    DROP COLUMN passport_expiry_date,
    ADD COLUMN passport_id VARCHAR(36),
    ADD COLUMN driver_license_id VARCHAR(36),
    ADD CONSTRAINT fk_passport_id
        FOREIGN KEY (passport_id)
            REFERENCES passports(id),
    ADD CONSTRAINT fk_driver_license_id
        FOREIGN KEY (driver_license_id)
            REFERENCES driver_licenses(id);