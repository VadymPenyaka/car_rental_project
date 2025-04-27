truncate table public.car_order cascade;

truncate table public.admin cascade;

truncate table public.customer cascade;

truncate table public.car_maintenance cascade;

truncate table public.car_schedule cascade;

truncate table public.car cascade;

truncate table public.car_pricing cascade;

truncate table public.location cascade;

truncate table public.person cascade;

truncate table public.passport cascade;

truncate table public.model cascade;

truncate table public.brand cascade;

truncate table public.driver_license_category cascade;

truncate table public.driver_license cascade;

truncate table public.document cascade;



INSERT INTO location (id, location_name, region, city, address, latitude, longitude)
VALUES ('9d56f035-becb-4f1f-80a7-1be5cf85b0de',
        'Central Railway Station',
        'Kyiv Oblast', 'Kyiv',
        'Vokzalna Square 1, Kyiv, 01001',
        '50.4501',
        '30.5003');

INSERT INTO person (id, role, username, password, sure_name, first_name, phone_number)
VALUES
    ('d7c9f4d7-82b5-4c5f-9096-9c1c875cb0a4',
     'SYS_ADMIN',
     'h6c70+dkpnDTOUsGBwhsqwNttgODeVz/ckhTlQZG16A=',
     '$2a$10$OOdJ8/0NI0ZNujcLi1vTYO3EHvFH0qRH9DYc/vRrLGOHZHUCvSV66',
     'UCsd1xwCHX/arWAyDs32yg==',
     'Fl5jDslDkYKKLBS16ylGdA==',
     'Fl5jDslDkYKKLBS16ylGdA=='),

    ('36a77d85-212a-438b-bf1c-f409c9400a85',
     'USER',
     'Lk6Inqan00OUtv237L//MX5004qbJ7DUhG66QRXedJc=',
     '$2a$10$1pwUxYuSNG139KIhYYKeCerRZSNP/yGa7WZjox8rV20yDMLEqCesC',
     'JJUUOGHuZcT5UiN62eI1dg==',
     'ijhIXe+7O/5Cx9EVaM/48Q==',
     'KKI2trtJPaY+9gIL8mOM8g=='),

    ('cdb059ee-a7f3-4c6a-9799-5cc9fe165979',
     'ADMIN',
     'XjupPvD+xwUQHn25PdjFxJIq9kQyVBjz9WohQ5aS7UE=',
     '$2a$10$DBx6N6hVCTeUuzBZU8g09..S/6ohQ0i2uW5/bJjd52Rlqts7IjFsq',
     'tDly87I2tXo5wt7faWrRPQ==',
     'YIZ793b+gFXNT5PfYnL+2g==',
     'ig5wanE5FK3CDVaBApNfFg==')

;

INSERT INTO admin (id, person_id, position, department, is_on_vocation, location_id)
VALUES ('da8dcb8e-b692-4ac2-8b58-b9eaaf381edb',
        'd7c9f4d7-82b5-4c5f-9096-9c1c875cb0a4',
        'System admin',
        'Admin',
        false,
        '9d56f035-becb-4f1f-80a7-1be5cf85b0de'),

       ('1bbc25c2-b373-46df-be0d-77df17261118',
        'cdb059ee-a7f3-4c6a-9799-5cc9fe165979',
        'Admin',
        'Admin',
        false,
        '9d56f035-becb-4f1f-80a7-1be5cf85b0de')
;

INSERT INTO brand (name) VALUES
    ('BMW'),
    ('Audi'),
    ('Toyota');

INSERT INTO model (id, brand_name, model_name, year, description) VALUES
    ('11111111-1111-1111-1111-111111111111', 'Toyota', 'Corolla', 2022, 'Compact car'),
    ('11111111-1111-1111-1111-111111111112', 'Toyota', 'Camry', 2021, 'Sedan'),
    ('11111111-1111-1111-1111-111111111113', 'Toyota', 'RAV4', 2023, 'SUV'),
    ('11111111-1111-1111-1111-111111111114', 'BMW', 'X5', 2022, 'Luxury SUV'),
    ('11111111-1111-1111-1111-111111111115', 'BMW', '3 Series', 2020, 'Sedan'),
    ('11111111-1111-1111-1111-111111111116', 'BMW', '5 Series', 2021, 'Executive Sedan'),
    ('11111111-1111-1111-1111-111111111117', 'Audi', 'A4', 2022, 'Compact Executive Car'),
    ('11111111-1111-1111-1111-111111111118', 'Audi', 'Q5', 2021, 'SUV'),
    ('11111111-1111-1111-1111-111111111119', 'Audi', 'A6', 2020, 'Executive Car'),
    ('11111111-1111-1111-1111-111111111120', 'Audi', 'Q7', 2023, 'Luxury SUV');

INSERT INTO car_pricing (id, pledge, up_to_three_days, up_to_ten_days, up_to_month, more_then_month)
VALUES
    ('33333333-3333-3333-3333-333333333331', 500.00, 50.00, 45.00, 40.00, 35.00),
    ('33333333-3333-3333-3333-333333333332', 550.00, 55.00, 50.00, 45.00, 40.00),
    ('33333333-3333-3333-3333-333333333333', 600.00, 60.00, 55.00, 50.00, 45.00),
    ('33333333-3333-3333-3333-333333333334', 700.00, 70.00, 65.00, 60.00, 55.00),
    ('33333333-3333-3333-3333-333333333335', 750.00, 75.00, 70.00, 65.00, 60.00),
    ('33333333-3333-3333-3333-333333333336', 800.00, 80.00, 75.00, 70.00, 65.00),
    ('33333333-3333-3333-3333-333333333337', 500.00, 50.00, 45.00, 40.00, 35.00),
    ('33333333-3333-3333-3333-333333333338', 650.00, 65.00, 60.00, 55.00, 50.00),
    ('33333333-3333-3333-3333-333333333339', 700.00, 70.00, 65.00, 60.00, 55.00),
    ('33333333-3333-3333-3333-333333333340', 850.00, 85.00, 80.00, 75.00, 70.00);

INSERT INTO car (id, vin, body_type, number, color, car_class, fuel_consumption, number_of_seats, fuel_type, gearbox_type, drive_type, engine_capacity, fuel_tank_capacity, trunk_capacity, model_id, license_category, required_experience, location_id, car_pricing_id)
VALUES
    ('44444444-4444-4444-4444-444444444441', 'VIN000001', 'SEDAN', 'AA0001AA', 'Black', 'ECONOMY', 7, 5, 'PETROL', 'AUTO', 'FRONT_WD', 1.8, 50, 450, '11111111-1111-1111-1111-111111111111', 'B', 1, '9d56f035-becb-4f1f-80a7-1be5cf85b0de', '33333333-3333-3333-3333-333333333331'),
    ('44444444-4444-4444-4444-444444444442', 'VIN000002', 'SEDAN', 'AA0002AA', 'Black', 'COMFORT', 8, 5, 'PETROL', 'AUTO', 'FRONT_WD', 2.0, 55, 480, '11111111-1111-1111-1111-111111111112', 'B', 2, '9d56f035-becb-4f1f-80a7-1be5cf85b0de', '33333333-3333-3333-3333-333333333332'),
    ('44444444-4444-4444-4444-444444444443', 'VIN000003', 'SUV', 'AA0003AA', 'Gray', 'COMFORT', 9, 5, 'PETROL', 'AUTO', 'ALL_WD', 2.5, 60, 500, '11111111-1111-1111-1111-111111111113', 'B', 2, '9d56f035-becb-4f1f-80a7-1be5cf85b0de', '33333333-3333-3333-3333-333333333333'),
    ('44444444-4444-4444-4444-444444444444', 'VIN000004', 'SUV', 'AA0004AA', 'Grey', 'BUSINESS', 10, 5, 'DIESEL', 'AUTO', 'ALL_WD', 3.0, 70, 550, '11111111-1111-1111-1111-111111111114', 'B', 3, '9d56f035-becb-4f1f-80a7-1be5cf85b0de', '33333333-3333-3333-3333-333333333334'),
    ('44444444-4444-4444-4444-444444444445', 'VIN000005', 'SEDAN', 'AA0005AA', 'Blue', 'BUSINESS', 7, 5, 'PETROL', 'AUTO', 'REAR_WD', 2.0, 60, 480, '11111111-1111-1111-1111-111111111115', 'B', 2, '9d56f035-becb-4f1f-80a7-1be5cf85b0de', '33333333-3333-3333-3333-333333333335'),
    ('44444444-4444-4444-4444-444444444446', 'VIN000006', 'SEDAN', 'AA0006AA', 'White', 'BUSINESS', 8, 5, 'PETROL', 'AUTO', 'REAR_WD', 3.0, 65, 500, '11111111-1111-1111-1111-111111111116', 'B', 3, '9d56f035-becb-4f1f-80a7-1be5cf85b0de', '33333333-3333-3333-3333-333333333336'),
    ('44444444-4444-4444-4444-444444444447', 'VIN000007', 'SEDAN', 'AA0007AA', 'Blue', 'COMFORT', 6, 5, 'PETROL', 'AUTO', 'FRONT_WD', 2.0, 55, 450, '11111111-1111-1111-1111-111111111117', 'B', 1, '9d56f035-becb-4f1f-80a7-1be5cf85b0de', '33333333-3333-3333-3333-333333333337'),
    ('44444444-4444-4444-4444-444444444448', 'VIN000008', 'SUV', 'AA0008AA', 'Black', 'BUSINESS', 9, 5, 'PETROL', 'AUTO', 'ALL_WD', 3.0, 70, 500, '11111111-1111-1111-1111-111111111118', 'B', 2, '9d56f035-becb-4f1f-80a7-1be5cf85b0de', '33333333-3333-3333-3333-333333333338'),
    ('44444444-4444-4444-4444-444444444449', 'VIN000009', 'SEDAN', 'AA0009AA', 'White', 'BUSINESS', 7, 5, 'DIESEL', 'AUTO', 'ALL_WD', 2.0, 65, 470, '11111111-1111-1111-1111-111111111119', 'B', 3, '9d56f035-becb-4f1f-80a7-1be5cf85b0de', '33333333-3333-3333-3333-333333333339'),
    ('44444444-4444-4444-4444-444444444450', 'VIN000010', 'SUV', 'AA0010AA', 'White', 'PREMIUM', 12, 7, 'PETROL', 'AUTO', 'ALL_WD', 3.5, 80, 600, '11111111-1111-1111-1111-111111111120', 'B', 4, '9d56f035-becb-4f1f-80a7-1be5cf85b0de', '33333333-3333-3333-3333-333333333340');