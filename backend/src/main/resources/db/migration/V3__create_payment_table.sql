CREATE TABLE payment (
                          id VARCHAR(36) PRIMARY KEY,
                          amount DOUBLE PRECISION NOT NULL,
                          payment_method VARCHAR(20) NOT NULL,
                          payment_status VARCHAR(10) NOT NULL,
                          payment_date TIMESTAMP NOT NULL DEFAULT NOW()
);

ALTER TABLE car_order
    ADD COLUMN payment_id VARCHAR(36),
    ADD CONSTRAINT fk_payment
        FOREIGN KEY (payment_id)
        REFERENCES payment(id)