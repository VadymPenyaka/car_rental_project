CREATE TABLE refresh_token (
        id VARCHAR(36) PRIMARY KEY,
        person_id VARCHAR(36) NOT NULL,
        refresh_token varchar(255) NOT NULL,
        FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE CASCADE
);