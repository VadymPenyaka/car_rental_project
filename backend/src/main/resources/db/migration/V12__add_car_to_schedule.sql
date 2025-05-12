alter table car
    add primary key (id);

alter table car_schedule
    add constraint car_fk
        foreign key (car_id)
            references car(id)