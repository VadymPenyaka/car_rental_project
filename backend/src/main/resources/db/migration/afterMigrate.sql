delete from admins;
delete from locations;
delete from customers;
delete from persons;
delete from passports;
delete from driver_licenses;

INSERT INTO locations (id, location_name, region, city, address, latitude, longitude)
VALUES ('9d56f035-becb-4f1f-80a7-1be5cf85b0de',
        'Central Railway Station',
        'Kyiv Oblast', 'Kyiv',
        'Vokzalna Square 1, Kyiv, 01001',
        '50.4501',
        '30.5003');

INSERT INTO persons (id, role, username, password, sure_name, first_name, phone_number)
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
     'KKI2trtJPaY+9gIL8mOM8g==')
;

INSERT INTO admins (id, person_id, position, department, is_on_vocation, location_id)
VALUES ('da8dcb8e-b692-4ac2-8b58-b9eaaf381edb',
        'd7c9f4d7-82b5-4c5f-9096-9c1c875cb0a4',
        'System admin',
        'Admin',
        false,
        '9d56f035-becb-4f1f-80a7-1be5cf85b0de');