CREATE TABLE persons (
    phone_number VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    sure_name VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(60) NOT NULL,
    role VARCHAR(36) NOT NULL,
    id varchar(36) NOT NULL,
    primary key (id)
);

ALTER TABLE customers
    DROP COLUMN first_name,
    DROP COLUMN sure_name,
    DROP COLUMN password,
    DROP COLUMN phone_number,
    DROP COLUMN email,
    ADD COLUMN person_id VARCHAR(36) NOT NULL,
    ADD CONSTRAINT person_id_fk
        FOREIGN KEY (person_id) REFERENCES persons(id);


ALTER TABLE admins
    DROP COLUMN first_name,
    DROP COLUMN sure_name,
    DROP COLUMN password,
    DROP COLUMN phone_number,
    DROP COLUMN email,
    DROP COLUMN role,
    ADD COLUMN position VARCHAR(50),
    ADD COLUMN department VARCHAR(50),
    ADD COLUMN person_id VARCHAR(36) NOT NULL,
    ADD CONSTRAINT person_id_fk
        FOREIGN KEY (person_id) REFERENCES persons(id);