alter table car_registration_info
    add column passport_id varchar(36) not null default 'AB 987654',
    add column insurance_id varchar(36) not null default 'AA12345678';