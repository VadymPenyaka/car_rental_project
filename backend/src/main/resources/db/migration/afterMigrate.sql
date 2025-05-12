truncate table public.car_order cascade;

truncate table public.admin cascade;

truncate table public.customer cascade;

truncate table public.car_maintenance cascade;

truncate table public.car_schedule cascade;

truncate table public.car cascade;

truncate table public.car_pricing cascade;

truncate table public.car_registration_info cascade;

truncate table public.car_details cascade;

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
     'ROLE_SADMIN',
     'h6c70+dkpnDTOUsGBwhsqwNttgODeVz/ckhTlQZG16A=',
     '$2a$10$OOdJ8/0NI0ZNujcLi1vTYO3EHvFH0qRH9DYc/vRrLGOHZHUCvSV66',
     'UCsd1xwCHX/arWAyDs32yg==',
     'Fl5jDslDkYKKLBS16ylGdA==',
     'Fl5jDslDkYKKLBS16ylGdA=='),

    ('36a77d85-212a-438b-bf1c-f409c9400a85',
     'ROLE_USER',
     'Lk6Inqan00OUtv237L//MX5004qbJ7DUhG66QRXedJc=',
     '$2a$10$1pwUxYuSNG139KIhYYKeCerRZSNP/yGa7WZjox8rV20yDMLEqCesC',
     'JJUUOGHuZcT5UiN62eI1dg==',
     'ijhIXe+7O/5Cx9EVaM/48Q==',
     'KKI2trtJPaY+9gIL8mOM8g=='),

    ('cdb059ee-a7f3-4c6a-9799-5cc9fe165979',
     'ROLE_ADMIN',
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
    ('Toyota'),
    ('Tesla'),
    ('Volkswagen'),
    ('Chevrolet'),
    ('Nisan'),
    ('Skoda'),
    ('Lexus'),
    ('Mercedes-Benz');

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

INSERT INTO car_details (id, engine_capacity, fuel_tank_capacity, fuel_consumption, trunk_capacity, number_of_seats, license_category, required_experience, configuration)
VALUES
    ('e54193e7-1b99-4a86-9d27-000000000001', 1.8, 50, 7, 450, 5, 'B', 1, '["heated seats", "cruise control", "ABS"]'),
    ('e54193e7-1b99-4a86-9d27-000000000002', 2.0, 55, 8, 480, 5, 'B', 2, '["rain sensor", "cruise control"]'),
    ('e54193e7-1b99-4a86-9d27-000000000003', 2.5, 60, 9, 500, 5, 'B', 2, '["panoramic roof", "heated seats", "autopilot"]'),
    ('e54193e7-1b99-4a86-9d27-000000000004', 3.0, 70, 10, 550, 5, 'B', 3, '["autopilot", "rain sensor", "ABS"]'),
    ('e54193e7-1b99-4a86-9d27-000000000005', 2.0, 60, 7, 480, 5, 'B', 2, '["cruise control", "ABS"]'),
    ('e54193e7-1b99-4a86-9d27-000000000006', 3.0, 65, 8, 500, 5, 'B', 3, '["panoramic roof", "heated seats"]'),
    ('e54193e7-1b99-4a86-9d27-000000000007', 2.0, 55, 6, 450, 5, 'B', 1, '["rain sensor"]'),
    ('e54193e7-1b99-4a86-9d27-000000000008', 3.0, 70, 9, 500, 5, 'B', 2, '["autopilot", "panoramic roof"]'),
    ('e54193e7-1b99-4a86-9d27-000000000009', 2.0, 65, 7, 470, 5, 'B', 3, '["ABS", "heated seats"]'),
    ('e54193e7-1b99-4a86-9d27-000000000010', 3.5, 80, 12, 600, 7, 'B', 4, '["autopilot", "panoramic roof", "rain sensor", "heated seats"]');

INSERT INTO car_registration_info (id, vin, number, color)
VALUES
    ('9e72f4ae-f001-4af0-9001-000000000001', 'VIN000001', 'AA0001AA', 'Black'),
    ('9e72f4ae-f001-4af0-9001-000000000002', 'VIN000002', 'AA0002AA', 'Black'),
    ('9e72f4ae-f001-4af0-9001-000000000003', 'VIN000003', 'AA0003AA', 'Gray'),
    ('9e72f4ae-f001-4af0-9001-000000000004', 'VIN000004', 'AA0004AA', 'Grey'),
    ('9e72f4ae-f001-4af0-9001-000000000005', 'VIN000005', 'AA0005AA', 'Blue'),
    ('9e72f4ae-f001-4af0-9001-000000000006', 'VIN000006', 'AA0006AA', 'White'),
    ('9e72f4ae-f001-4af0-9001-000000000007', 'VIN000007', 'AA0007AA', 'Blue'),
    ('9e72f4ae-f001-4af0-9001-000000000008', 'VIN000008', 'AA0008AA', 'Black'),
    ('9e72f4ae-f001-4af0-9001-000000000009', 'VIN000009', 'AA0009AA', 'White'),
    ('9e72f4ae-f001-4af0-9001-000000000010', 'VIN000010', 'AA0010AA', 'White');

INSERT INTO car (id, location_id, car_class, fuel_type, gearbox_type, drive_type, car_pricing_id, body_type, model_id, registration_info_id, car_details_id)
VALUES
    ('44444444-4444-4444-4444-444444444441', '9d56f035-becb-4f1f-80a7-1be5cf85b0de', 'ECONOMY', 'PETROL', 'AUTO', 'FRONT_WD', '33333333-3333-3333-3333-333333333331', 'SEDAN', '11111111-1111-1111-1111-111111111111', '9e72f4ae-f001-4af0-9001-000000000001', 'e54193e7-1b99-4a86-9d27-000000000001'),
    ('44444444-4444-4444-4444-444444444442', '9d56f035-becb-4f1f-80a7-1be5cf85b0de', 'COMFORT', 'PETROL', 'AUTO', 'FRONT_WD', '33333333-3333-3333-3333-333333333332', 'SEDAN', '11111111-1111-1111-1111-111111111112', '9e72f4ae-f001-4af0-9001-000000000002', 'e54193e7-1b99-4a86-9d27-000000000002'),
    ('44444444-4444-4444-4444-444444444443', '9d56f035-becb-4f1f-80a7-1be5cf85b0de', 'COMFORT', 'PETROL', 'AUTO', 'ALL_WD', '33333333-3333-3333-3333-333333333333', 'SUV', '11111111-1111-1111-1111-111111111113', '9e72f4ae-f001-4af0-9001-000000000003', 'e54193e7-1b99-4a86-9d27-000000000003'),
    ('44444444-4444-4444-4444-444444444444', '9d56f035-becb-4f1f-80a7-1be5cf85b0de', 'BUSINESS', 'DIESEL', 'AUTO', 'ALL_WD', '33333333-3333-3333-3333-333333333334', 'SUV', '11111111-1111-1111-1111-111111111114', '9e72f4ae-f001-4af0-9001-000000000004', 'e54193e7-1b99-4a86-9d27-000000000004'),
    ('44444444-4444-4444-4444-444444444445', '9d56f035-becb-4f1f-80a7-1be5cf85b0de', 'BUSINESS', 'PETROL', 'AUTO', 'REAR_WD', '33333333-3333-3333-3333-333333333335', 'SEDAN', '11111111-1111-1111-1111-111111111115', '9e72f4ae-f001-4af0-9001-000000000005', 'e54193e7-1b99-4a86-9d27-000000000005'),
    ('44444444-4444-4444-4444-444444444446', '9d56f035-becb-4f1f-80a7-1be5cf85b0de', 'BUSINESS', 'PETROL', 'AUTO', 'REAR_WD', '33333333-3333-3333-3333-333333333336', 'SEDAN', '11111111-1111-1111-1111-111111111116', '9e72f4ae-f001-4af0-9001-000000000006', 'e54193e7-1b99-4a86-9d27-000000000006'),
    ('44444444-4444-4444-4444-444444444447', '9d56f035-becb-4f1f-80a7-1be5cf85b0de', 'COMFORT', 'PETROL', 'AUTO', 'FRONT_WD', '33333333-3333-3333-3333-333333333337', 'SEDAN', '11111111-1111-1111-1111-111111111117', '9e72f4ae-f001-4af0-9001-000000000007', 'e54193e7-1b99-4a86-9d27-000000000007'),
    ('44444444-4444-4444-4444-444444444448', '9d56f035-becb-4f1f-80a7-1be5cf85b0de', 'BUSINESS', 'PETROL', 'AUTO', 'ALL_WD', '33333333-3333-3333-3333-333333333338', 'SUV', '11111111-1111-1111-1111-111111111118', '9e72f4ae-f001-4af0-9001-000000000008', 'e54193e7-1b99-4a86-9d27-000000000008'),
    ('44444444-4444-4444-4444-444444444449', '9d56f035-becb-4f1f-80a7-1be5cf85b0de', 'BUSINESS', 'DIESEL', 'AUTO', 'ALL_WD', '33333333-3333-3333-3333-333333333339', 'SEDAN', '11111111-1111-1111-1111-111111111119', '9e72f4ae-f001-4af0-9001-000000000009', 'e54193e7-1b99-4a86-9d27-000000000009'),
    ('44444444-4444-4444-4444-444444444450', '9d56f035-becb-4f1f-80a7-1be5cf85b0de', 'PREMIUM', 'PETROL', 'AUTO', 'ALL_WD', '33333333-3333-3333-3333-333333333340', 'SUV', '11111111-1111-1111-1111-111111111120', '9e72f4ae-f001-4af0-9001-000000000010', 'e54193e7-1b99-4a86-9d27-000000000010');