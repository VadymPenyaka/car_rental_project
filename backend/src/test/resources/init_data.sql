DELETE FROM `car_orders`;
DELETE FROM `cars_maintenance`;
DELETE FROM `car_schedule`;
DELETE FROM `admins`;
DELETE FROM `cars`;
DELETE FROM `customers`;
DELETE FROM `locations`;
DELETE FROM `car_pricing`;


INSERT INTO `car_pricing` (`more_then_month`, `pledge`, `up_to_month`, `up_to_ten_days`, `up_to_three_days`, `id`)
VALUES (100, 300, 120, 150, 180, 'a08456f3-4c7e-4a9b-bc51-d7ec8b621bfb');
INSERT INTO `car_pricing` (`more_then_month`, `pledge`, `up_to_month`, `up_to_ten_days`, `up_to_three_days`, `id`)
VALUES (120, 350, 150, 200, 220, '2e5ea1c7-4e5c-4330-a19c-e034f97f97c2');

INSERT INTO `locations` (`id`, `location_name`, `region`, `city`, `address`, `latitude`, `longitude`)
VALUES ('2d65947e-78ca-482a-9356-af6e929f5fbe', 'Lviv Metro', 'Lviv', 'Lviv', 'Gorodotska 300', '32423434', '0989809');
INSERT INTO `locations` (`id`, `location_name`, `region`, `city`, `address`, `latitude`, `longitude`)
VALUES ('f4b2f24b-b3fa-4b8c-a63b-5f25ffa770b2', 'Boryspil Airport', 'Kyiv', 'Kyiv', 'Airport (KBP)', '3242324', '0989809');

INSERT INTO `cars` (fuel_consumption, fuel_type, number_of_seats, car_pricing_id, id, brand, model, car_class, gearbox_type, location_id, `engine_capacity`, `drive_type`)
VALUES (13, 'DIESEL', 5, 'a08456f3-4c7e-4a9b-bc51-d7ec8b621bfb', 'b231afa5-79df-4597-ac6f-4f410fac85b4', 'BMW', 'X5', 'BUSINESS', 'AUTO', '2d65947e-78ca-482a-9356-af6e929f5fbe', 3.0, 'FRONT_WD');
INSERT INTO `cars` (`fuel_consumption`, `fuel_type`, `number_of_seats`, `car_pricing_id`, `id`, `brand`, `model`, `car_class`, `gearbox_type`, `location_id`, `engine_capacity`, `drive_type`)
VALUES (9, 'GASOLINE', 5, '2e5ea1c7-4e5c-4330-a19c-e034f97f97c2', '05c398e4-de14-4c91-b8d6-0b3364c619b2', 'BMW', 'X6', 'BUSINESS', 'AUTO', '2d65947e-78ca-482a-9356-af6e929f5fbe', 2.0, 'FRONT_WD');

INSERT INTO `customers` (`birth_date`, `passport_expiry_date`, `id`, `passport_id`, `first_name`, `sure_name`, `email`, `phone_number`)
VALUES ('2001-05-20', '2026-12-01', 2, '999999999', 'Pip', 'Ivan', 'ivan@gmail.com', '0955915122');
INSERT INTO `customers` (`birth_date`, `passport_expiry_date`, `id`, `passport_id`, `first_name`, `sure_name`, `email`, `phone_number`)
VALUES ('2004-05-20', '2026-12-01', 1, '999911149', 'Vadym', 'Peniaka', 'vadym.penyaka@gmail.com', '0953322122');

INSERT INTO admins (id, first_name, last_name, password, email, phone_number)
VALUES (1, 'Vasyl', 'Melnyk', 'P@ssw0rd', 'vasyl@gmail.com', '09989874620');
INSERT INTO admins (id, first_name, last_name, password, email, phone_number)
VALUES (2, 'Ivan', 'Melnyk', 'P@ssw0rd', 'ivan@gmail.com', '09912374621');

INSERT INTO `car_schedule` (`id`, `car_id`, `start_date`, `end_date`, `status`)
VALUES ('526ea4c7-afa7-4a58-8c6d-967fabbcb180', 'b231afa5-79df-4597-ac6f-4f410fac85b4', '2024-09-20', '2024-09-22', 'BOOKED');
INSERT INTO `car_schedule` (`id`, `car_id`, `start_date`, `end_date`, `status`)

VALUES ('245deb7c-5e36-49df-8e9c-78a1bab2d373', 'b231afa5-79df-4597-ac6f-4f410fac85b4', '2024-09-23', '2024-09-24', 'UNDER_SERVICE');
INSERT INTO `cars_maintenance` (`id`, `schedule_id`, `description`, `price`)
VALUES ('ecb05a9b-0541-49f6-8d31-36acfb37ef09', '245deb7c-5e36-49df-8e9c-78a1bab2d373', 'Cleaning', 100);

INSERT INTO `car_orders` (`admin_id`, `id`, `status`, `customer_id`, `total_price`, `comment`, `schedule_id`)
VALUES (1, '9a49ebae-5abd-41ed-95ab-f9297de73dc5', 'APPROVED', 1, 300.0, 'none', '526ea4c7-afa7-4a58-8c6d-967fabbcb180');