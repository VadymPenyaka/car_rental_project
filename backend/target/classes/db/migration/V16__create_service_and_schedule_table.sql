ALTER TABLE car_orders
    DROP COLUMN end_date,
    DROP COLUMN start_date;


CREATE TYPE schedule_status AS ENUM ('BOOKED', 'UNDER_SERVICE');

CREATE TABLE IF NOT EXISTS car_schedule (
    id BIGINT PRIMARY KEY,
    car_id BIGINT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status schedule_status NOT NULL,
    CONSTRAINT fk_car_schedule_car FOREIGN KEY (car_id) REFERENCES cars(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS cars_services (
    id BIGINT PRIMARY KEY,
    schedule_id BIGINT NOT NULL,
    car_id BIGINT NOT NULL,
    description VARCHAR(100) NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    FOREIGN KEY (car_id) REFERENCES cars(id),
    FOREIGN KEY  (schedule_id) REFERENCES car_schedule(id)
);

ALTER TABLE car_orders
    ADD COLUMN schedule_id BIGINT NOT NULL;

ALTER TABLE car_orders
    ADD CONSTRAINT schedule_id
        FOREIGN KEY (schedule_id)
            REFERENCES car_schedule (id);



