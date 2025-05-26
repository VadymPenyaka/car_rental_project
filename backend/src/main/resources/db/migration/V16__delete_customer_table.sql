ALTER TABLE car_order ADD COLUMN person_id varchar(36),
DROP CONSTRAINT customer_id_fk,
DROP COLUMN customer_id;
DROP TABLE customer;