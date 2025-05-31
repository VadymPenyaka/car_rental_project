alter table document
    add column order_id varchar(36) not null default 'undefined',
    add constraint order_fk foreign key (order_id) references car_order(id);