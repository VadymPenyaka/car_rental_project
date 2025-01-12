CREATE TYPE role_enum AS ENUM ('USER', 'ADMIN', 'SYS_ADMIN');

ALTER TABLE admins
    ADD COLUMN role role_enum NOT NULL;