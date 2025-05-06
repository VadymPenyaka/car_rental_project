CREATE TABLE refresh_token (
        id VARCHAR(36) PRIMARY KEY,
        username VARCHAR(255) NOT NULL,
        refresh_token varchar(255) NOT NULL
);