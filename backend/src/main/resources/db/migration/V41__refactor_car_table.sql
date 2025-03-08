CREATE TABLE brand (
    name VARCHAR(50) NOT NULL PRIMARY KEY
);

CREATE TABLE model (
    id VARCHAR(36) PRIMARY KEY,
    brand_name VARCHAR(50) NOT NULL,
    CONSTRAINT brand_name_fk
        FOREIGN KEY (brand_name)
            REFERENCES brand (name),
    model_name VARCHAR(50) NOT NULL,
    description VARCHAR(256) NOT NULL,
    year INT NOT NULL
);

ALTER TABLE cars
    ADD COLUMN required_experience INT DEFAULT 1 NOT NULL,
    ADD COLUMN model_id VARCHAR(36),
    ADD CONSTRAINT model_id_fk
        FOREIGN KEY (model_id)
            REFERENCES model (id);
