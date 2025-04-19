alter table driver_license_category
    drop constraint driver_license_categories_pkey,
    add column id varchar(36) primary key;
