DROP TABLE IF EXISTS driver_license_categories;

CREATE TABLE driver_license_categories (
                                           license_id VARCHAR(36) NOT NULL,
                                           category VARCHAR(5) NOT NULL,
                                           issue_date DATE NOT NULL,
                                           PRIMARY KEY (license_id, category),
                                           CONSTRAINT fk_license FOREIGN KEY (license_id) REFERENCES driver_licenses(id) ON DELETE CASCADE
);
