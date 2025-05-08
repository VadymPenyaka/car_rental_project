CREATE TYPE fuel_type_enum AS ENUM ('DIESEL', 'GASOLINE', 'ELECTRIC', 'GAS_OIL_GASOLINE');
CREATE TYPE gearbox_type_enum AS ENUM ('AUTO', 'MANUAL');

ALTER TABLE cars ADD COLUMN fuel_type_temp fuel_type_enum;
ALTER TABLE cars ADD COLUMN gearbox_type_temp gearbox_type_enum;

UPDATE cars
SET fuel_type_temp = CASE
                         WHEN fuel_type = 0 THEN 'DIESEL'
                         WHEN fuel_type = 1 THEN 'GASOLINE'
                         WHEN fuel_type = 2 THEN 'ELECTRIC'
                         WHEN fuel_type = 3 THEN 'GAS_OIL_GASOLINE'
    END::fuel_type_enum,
    gearbox_type_temp = CASE
                            WHEN gearbox_type = 0 THEN 'AUTO'
                            WHEN gearbox_type = 1 THEN 'MANUAL'
        END::gearbox_type_enum;

ALTER TABLE cars DROP COLUMN fuel_type;
ALTER TABLE cars DROP COLUMN gearbox_type;

ALTER TABLE cars RENAME COLUMN fuel_type_temp TO fuel_type;
ALTER TABLE cars RENAME COLUMN gearbox_type_temp TO gearbox_type;

