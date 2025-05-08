drop table verification_token;

CREATE TABLE person_pending_confirmation
(
    id VARCHAR(36) PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    data JSONB NOT NULL,
    expires_at TIMESTAMP NOT NULL
);