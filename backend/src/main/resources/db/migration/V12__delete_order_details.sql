ALTER TABLE car_orders
    DROP CONSTRAINT car_orders_order_detail_id_fkey; -- Replace with actual constraint name

ALTER TABLE car_orders
    DROP COLUMN order_detail_id;

DROP TABLE IF EXISTS orders_details;

ALTER TABLE car_orders
    ADD COLUMN car_id BIGINT NOT NULL;

ALTER TABLE car_orders
    ADD CONSTRAINT fk_car_id
        FOREIGN KEY (car_id)
            REFERENCES cars (id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION;
