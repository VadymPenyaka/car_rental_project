alter table car_schedule
    drop constraint car_id_fk;

drop table car;

CREATE TABLE car_details
(
    id varchar(36) PRIMARY KEY,
    engine_capacity DOUBLE PRECISION NOT NULL,
    fuel_tank_capacity INTEGER NOT NULL,
    fuel_consumption INTEGER NOT NULL,
    trunk_capacity INTEGER NOT NULL,
    number_of_seats INTEGER NOT NULL,
    license_category VARCHAR(5) NOT NULL DEFAULT 'B'::character varying NOT NULL,
    required_experience INTEGER NOT NULL DEFAULT 1,
    configuration jsonb not null
);

CREATE TABLE car_registration_info
(
    id varchar(36) PRIMARY KEY,
    vin VARCHAR(36) NOT NULL,
    number VARCHAR(36) NOT NULL,
    color VARCHAR(36) NOT NULL
);

CREATE TABLE car
(
    id varchar(36) NOT NULL,
    location_id varchar(36) NOT NULL,
    car_class varchar(50) NOT NULL,
    fuel_type varchar(50),
    gearbox_type varchar(50),
    drive_type varchar(50) NOT NULL,
    car_pricing_id varchar(36) NOT NULL,
    constraint pricing_fk foreign key (car_pricing_id) references car_pricing(id),
    body_type varchar(36) NOT NULL,
    model_id varchar(36),
    registration_info_id varchar(36) NOT NULL,
    constraint registration_info_fk foreign key (registration_info_id) references car_registration_info(id),
    car_details_id varchar(36) NOT NULL,
    constraint car_details_fk foreign key (car_details_id) references car_details(id)
);