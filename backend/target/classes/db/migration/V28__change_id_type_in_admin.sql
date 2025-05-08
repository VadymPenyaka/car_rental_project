ALTER TABLE car_orders
    DROP CONSTRAINT car_orders_admin_id_fkey;

ALTER TABLE car_orders
    ALTER COLUMN admin_id TYPE VARCHAR(36);

ALTER TABLE admins
    ALTER COLUMN id TYPE VARCHAR(36);

ALTER TABLE car_orders
    ADD CONSTRAINT admin_id_fk
        FOREIGN KEY (admin_id)
            REFERENCES admins (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE;