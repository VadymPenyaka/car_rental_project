DELETE FROM car_orders;
DELETE FROM cars_maintenance;
DELETE FROM car_schedule;
DELETE FROM admins;
DELETE FROM cars;
DELETE FROM customers;
DELETE FROM locations;
DELETE FROM car_pricing;
DELETE FROM persons;


INSERT INTO car_pricing (more_then_month, pledge, up_to_month, up_to_ten_days, up_to_three_days, id)
VALUES (100, 300, 120, 150, 180, 'a08456f3-4c7e-4a9b-bc51-d7ec8b621bfb');
INSERT INTO car_pricing (more_then_month, pledge, up_to_month, up_to_ten_days, up_to_three_days, id)
VALUES (120, 350, 150, 200, 220, '2e5ea1c7-4e5c-4330-a19c-e034f97f97c2');

INSERT INTO locations (id, location_name, region, city, address, latitude, longitude)
VALUES ('2d65947e-78ca-482a-9356-af6e929f5fbe', 'Lviv Metro', 'Lviv', 'Lviv', 'Gorodotska 300', '32423434', '0989809');
INSERT INTO locations (id, location_name, region, city, address, latitude, longitude)
VALUES ('f4b2f24b-b3fa-4b8c-a63b-5f25ffa770b2', 'Boryspil Airport', 'Kyiv', 'Kyiv', 'Airport (KBP)', '3242324', '0989809');

INSERT INTO cars (fuel_consumption, fuel_type, number_of_seats, car_pricing_id, id, brand, model, car_class, gearbox_type, location_id, engine_capacity, drive_type)
VALUES (13, 'DIESEL', 5, 'a08456f3-4c7e-4a9b-bc51-d7ec8b621bfb', 'b231afa5-79df-4597-ac6f-4f410fac85b4', 'BMW', 'X5', 'BUSINESS', 'AUTO', '2d65947e-78ca-482a-9356-af6e929f5fbe', 3.0, 'FRONT_WD');
INSERT INTO cars (fuel_consumption, fuel_type, number_of_seats, car_pricing_id, id, brand, model, car_class, gearbox_type, location_id, engine_capacity, drive_type)
VALUES (9, 'GASOLINE', 5, '2e5ea1c7-4e5c-4330-a19c-e034f97f97c2', '05c398e4-de14-4c91-b8d6-0b3364c619b2', 'BMW', 'X6', 'BUSINESS', 'AUTO', '2d65947e-78ca-482a-9356-af6e929f5fbe', 2.0, 'FRONT_WD');

INSERT INTO persons (id, first_name, sure_name, phone_number, username, password, role)
VALUES ('08368a63-2f14-43a5-b8f0-84e2b3b51675', 'Admin', 'Admin', '0999999999', 'admin@gmail.com', 'Passw0rd&!', 'ADMIN');
INSERT INTO persons (id, first_name, sure_name, phone_number, username, password, role)
VALUES ('82ad7c5d-a926-4eda-9cff-f2eee2146039', 'Customer', 'Customer', '0111111111', 'customer@gmail.com', 'Passw0rd&!', 'ADMIN');

INSERT INTO customers (birth_date, passport_expiry_date, id, passport_id, person_id)
VALUES ('2004-05-20', '2026-12-01', '90ec8258-29cd-4367-b983-c18895e7a1a3', '999911149', '82ad7c5d-a926-4eda-9cff-f2eee2146039');

INSERT INTO admins (id, person_id, position, department)
VALUES ('e1cbe369-09cb-45a4-b370-61b9ae7c7efc', '08368a63-2f14-43a5-b8f0-84e2b3b51675', 'manager', 'department');

INSERT INTO car_schedule (id, car_id, start_date, end_date, status)
VALUES ('526ea4c7-afa7-4a58-8c6d-967fabbcb180', 'b231afa5-79df-4597-ac6f-4f410fac85b4', '2024-09-20', '2024-09-22', 'BOOKED');
INSERT INTO car_schedule (id, car_id, start_date, end_date, status)

VALUES ('245deb7c-5e36-49df-8e9c-78a1bab2d373', 'b231afa5-79df-4597-ac6f-4f410fac85b4', '2024-09-23', '2024-09-24', 'UNDER_SERVICE');
INSERT INTO cars_maintenance (id, schedule_id, description, price)
VALUES ('ecb05a9b-0541-49f6-8d31-36acfb37ef09', '245deb7c-5e36-49df-8e9c-78a1bab2d373', 'Cleaning', 100);

INSERT INTO car_orders (admin_id, id, status, customer_id, total_price, comment, schedule_id)
VALUES ('e1cbe369-09cb-45a4-b370-61b9ae7c7efc', '9a49ebae-5abd-41ed-95ab-f9297de73dc5', 'APPROVED', '90ec8258-29cd-4367-b983-c18895e7a1a3', 300.0, 'none', '526ea4c7-afa7-4a58-8c6d-967fabbcb180');