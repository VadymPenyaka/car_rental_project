CREATE TABLE person (
    phone_number VARCHAR(12) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    sure_name VARCHAR(50) NOT NULL,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(60) NOT NULL,
    role role_enum not null,
    id varchar(36) not null,
    primary key (id)
);

ALTER TABLE customers
    DROP COLUMN first_name,
    DROP COLUMN sure_name,
    DROP COLUMN password,
    DROP COLUMN phone_number,
    DROP COLUMN email;

ALTER TABLE admins
    DROP COLUMN first_name,
    DROP COLUMN sure_name,
    DROP COLUMN password,
    DROP COLUMN phone_number,
    DROP COLUMN email,
    DROP COLUMN role;