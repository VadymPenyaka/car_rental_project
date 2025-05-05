CREATE TABLE users_pending_verification
(
    id VARCHAR(36) PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    verification_token VARCHAR(36) NOT NULL,
    token_expiry TIMESTAMP NOT NULL
);
