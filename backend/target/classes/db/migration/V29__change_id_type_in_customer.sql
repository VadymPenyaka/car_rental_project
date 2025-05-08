ALTER TABLE car_orders
    DROP CONSTRAINT customer_id;
ALTER TABLE customers
    ALTER COLUMN id TYPE VARCHAR(36);


ALTER TABLE car_orders
    ALTER COLUMN customer_id TYPE VARCHAR(36);
ALTER TABLE car_orders
    ADD CONSTRAINT customer_id_fk
        FOREIGN KEY (customer_id)
            REFERENCES customers (id);
