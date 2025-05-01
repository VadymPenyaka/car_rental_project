CREATE TABLE verification_token (
        token VARCHAR(36) PRIMARY KEY,
        type VARCHAR(36) NOT NULL,
        value VARCHAR(255) NOT NULL,
        expiry_date TIMESTAMP NOT NULL,
        person_id varchar(36) NOT NULL,
        CONSTRAINT fk_person FOREIGN KEY (person_id) REFERENCES person(id)
);