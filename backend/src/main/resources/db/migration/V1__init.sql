-- Drop tables if they exist
DROP TABLE IF EXISTS car_orders CASCADE;
DROP TABLE IF EXISTS persons CASCADE;
DROP TABLE IF EXISTS orders_details CASCADE;
DROP TABLE IF EXISTS cars_maintenance CASCADE;
DROP TABLE IF EXISTS car_schedule CASCADE;
DROP TABLE IF EXISTS customers CASCADE;
DROP TABLE IF EXISTS admins CASCADE;
DROP TABLE IF EXISTS cars CASCADE;
DROP TABLE IF EXISTS car_pricing CASCADE;
DROP TYPE IF EXISTS car_class_enum;
DROP TYPE IF EXISTS car_order_status_enum;
DROP TYPE IF EXISTS schedule_status;
DROP TYPE IF EXISTS fuel_type_enum;
DROP TYPE IF EXISTS gearbox_type_enum;
DROP TYPE IF EXISTS drive_type_enum;
DROP TYPE IF EXISTS role_enum;

-- Create car_pricing table
CREATE TABLE car_pricing (
                             more_then_month double precision NOT NULL,
                             pledge double precision NOT NULL,
                             up_to_month double precision NOT NULL,
                             up_to_ten_days double precision NOT NULL,
                             up_to_three_days double precision NOT NULL,
                             id bigint NOT NULL,
                             PRIMARY KEY (id)
);

CREATE TYPE car_class_enum AS ENUM ('CHEAP', 'COMFORT', 'BUSINESS', 'MINIBUS');

-- Create cars table
CREATE TABLE cars (
                      fuel_consumption integer NOT NULL,
                      fuel_type smallint NOT NULL,
                      is_available varchar(5) NOT NULL,
                      number_of_seats integer NOT NULL,
                      price_per_day double precision NOT NULL,
                      car_pricing_id bigint DEFAULT NULL,
                      id bigint NOT NULL,
                      brand varchar(50) NOT NULL,
                      model varchar(50) NOT NULL,
                      location varchar(255) NOT NULL,
                      car_class car_class_enum NOT NULL,
                      gearbox_type smallint NOT NULL,
                      PRIMARY KEY (id),
                      FOREIGN KEY (car_pricing_id) REFERENCES car_pricing(id),
                      CONSTRAINT cars_chk_1 CHECK (fuel_type BETWEEN 0 AND 3)
);

-- Define ENUM for car_class


-- Create admins table
CREATE TABLE admins (
                        id bigint NOT NULL,
                        first_name varchar(50) NOT NULL,
                        last_name varchar(50) NOT NULL,
                        password varchar(50) NOT NULL,
                        PRIMARY KEY (id)
);

-- Create customers table
CREATE TABLE customers (
                           birth_date date NOT NULL,
                           expiry_date date NOT NULL,
                           id bigint NOT NULL,
                           passport_id varchar(9) NOT NULL,
                           first_name varchar(50) NOT NULL,
                           last_name varchar(50) NOT NULL,
                           sure_name varchar(50) NOT NULL,
                           PRIMARY KEY (id)
);

-- Create orders_details table
CREATE TABLE orders_details (
                                drop_off_date date NOT NULL,
                                number_of_days integer NOT NULL,
                                pick_up_date date NOT NULL,
                                total_price double precision NOT NULL,
                                car_id bigint NOT NULL,
                                customer_id bigint NOT NULL,
                                id bigint NOT NULL,
                                comment varchar(50) DEFAULT NULL,
                                drop_off_location varchar(255) NOT NULL,
                                pick_up_location varchar(255) NOT NULL,
                                PRIMARY KEY (id),
                                FOREIGN KEY (car_id) REFERENCES cars(id),
                                FOREIGN KEY (customer_id) REFERENCES customers(id)
);

CREATE TYPE car_order_status_enum AS ENUM ('APPROVED', 'IN_USE', 'RETURNED', 'DAMAGED', 'PAID');

-- Create car_orders table
CREATE TABLE car_orders (
                            admin_id bigint NOT NULL,
                            id bigint NOT NULL,
                            order_detail_id bigint DEFAULT NULL,
                            status car_order_status_enum NOT NULL,
                            PRIMARY KEY (id),
                            FOREIGN KEY (admin_id) REFERENCES admins(id),
                            FOREIGN KEY (order_detail_id) REFERENCES orders_details(id)
);
