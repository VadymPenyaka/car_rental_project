ALTER TABLE car_order ADD CONSTRAINT
    person_fk foreign key (person_id)
        references person(id);