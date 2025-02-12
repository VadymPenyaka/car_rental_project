ALTER TABLE person RENAME TO persons;
ALTER TABLE persons
    ALTER COLUMN role SET DATA TYPE VARCHAR(36);

ALTER TABLE customers
    ADD COLUMN person_id VARCHAR(36);
UPDATE customers
    SET person_id = (SELECT id FROM persons LIMIT 1);
ALTER TABLE customers
    ALTER COLUMN person_id SET NOT NULL;
ALTER TABLE customers ADD CONSTRAINT person_id_fk
        FOREIGN KEY (person_id) REFERENCES persons(id);

ALTER TABLE admins
    ADD COLUMN person_id VARCHAR(36);
UPDATE admins
    SET person_id = (SELECT id FROM persons LIMIT 1);
ALTER TABLE admins ALTER COLUMN person_id SET NOT NULL;


ALTER TABLE admins ADD CONSTRAINT person_id_fk
    FOREIGN KEY (person_id) REFERENCES persons(id);