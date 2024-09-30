DELETE FROM `car_orders`;
DELETE FROM `cars_maintenance`;
DELETE FROM `car_schedule`;
DELETE FROM `admins`;
DELETE FROM `cars`;
DELETE FROM `customers`;
DELETE FROM `locations`;
DELETE FROM `car_pricing`;


INSERT INTO `car_pricing` (`more_then_month`, `pledge`, `up_to_month`, `up_to_ten_days`, `up_to_three_days`, `id`)
VALUES (100, 300, 120, 150, 180, 1);
INSERT INTO `car_pricing` (`more_then_month`, `pledge`, `up_to_month`, `up_to_ten_days`, `up_to_three_days`, `id`)
VALUES (120, 350, 150, 200, 220, 2);

INSERT INTO `locations` (`id`, `location_name`, `region`, `city`, `address`, `latitude`, `longitude`)
VALUES (1, 'Lviv Metro', 'Lviv', 'Lviv', 'Gorodotska 300', '32423434', '0989809');
INSERT INTO `locations` (`id`, `location_name`, `region`, `city`, `address`, `latitude`, `longitude`)
VALUES (2, 'Boryspil Airport', 'Kyiv', 'Kyiv', 'Airport (KBP)', '3242324', '0989809');

INSERT INTO `cars` (`fuel_consumption`, `fuel_type`, `number_of_seats`, `car_pricing_id`, `id`, `brand`, `model`, `car_class`, `gearbox_type`, `location_id`)
VALUES (10, 'DIESEL', 5, 1, 1, 'BMW', 'X5', 'BUSINESS', 'AUTOMATIC', 1);
INSERT INTO `cars` (`fuel_consumption`, `fuel_type`, `number_of_seats`, `car_pricing_id`, `id`, `brand`, `model`, `car_class`, `gearbox_type`, `location_id`)
VALUES (9, 'DIESEL', 5, 2, 2, 'BMW', 'X6', 'BUSINESS', 'AUTOMATIC', 2);

INSERT INTO `customers` (`birth_date`, `passport_expiry_date`, `id`, `passport_id`, `first_name`, `sure_name`, `email`, `phone_number`)
VALUES ('2001-05-20', '2026-12-01', 2, '999999999', 'Pip', 'Ivan', 'ivan@gmail.com', '0955915122');
INSERT INTO `customers` (`birth_date`, `passport_expiry_date`, `id`, `passport_id`, `first_name`, `sure_name`, `email`, `phone_number`)
VALUES ('2004-05-20', '2026-12-01', 1, '999911149', 'Vadym', 'Peniaka', 'vadym.penyaka@gmail.com', '0953322122');

INSERT INTO admins (id, first_name, last_name, password, email, phone_number)
VALUES (1, 'Vasyl', 'Melnyk', 'P@ssw0rd', 'vasyl@gmail.com', '09989874620');
INSERT INTO admins (id, first_name, last_name, password, email, phone_number)
VALUES (2, 'Ivan', 'Melnyk', 'P@ssw0rd', 'ivan@gmail.com', '09912374621');

INSERT INTO `car_schedule` (`id`, `car_id`, `start_date`, `end_date`, `status`)
VALUES (1, 1, '2024-09-20', '2024-09-22', 'BOOKED');
INSERT INTO `car_schedule` (`id`, `car_id`, `start_date`, `end_date`, `status`)
VALUES (2, 1, '2024-09-23', '2024-09-24', 'UNDER_SERVICE');

INSERT INTO `cars_maintenance` (`id`, `schedule_id`, `car_id`, `description`, `price`)
VALUES (1, 2, 1, 'Cleaning', 100);

INSERT INTO `car_orders` (`admin_id`, `id`, `status`, `car_id`, `customer_id`, `total_price`, `comment`, `schedule_id`)
VALUES (1, 1, 'APPROVED', 1, 1, 300.0, 'none', 1);