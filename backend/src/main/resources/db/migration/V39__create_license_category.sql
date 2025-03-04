CREATE TABLE driver_license_categories (
    license_id VARCHAR(36) NOT NULL REFERENCES driver_licenses(id) ON DELETE CASCADE,
    category VARCHAR(5) NOT NULL,
    PRIMARY KEY (license_id, category)
);

ALTER TABLE cars
    ADD COLUMN category VARCHAR(5) NOT NULL default 'B';

ALTER TABLE driver_licenses
    DROP COLUMN series,
    DROP COLUMN category;