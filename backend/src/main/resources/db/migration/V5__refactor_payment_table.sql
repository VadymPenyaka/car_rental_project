alter table payment
    add column order_id varchar(36),
    add constraint order_id_fk
        foreign key (order_id) references car_order(id),
    add column card_brand varchar(20),
    add column card_last_digits varchar(4),
    add column card_exp_year INT,
    add column card_exp_month INT,
    add payment_intent_id varchar(50);

alter table payment
    alter column amount type BIGINT;

alter table car_order
    drop constraint fk_payment,
    drop column payment_id,
    alter column total_price type BIGINT;