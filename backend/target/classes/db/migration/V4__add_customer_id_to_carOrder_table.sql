ALTER TABLE  car_orders
ADD COLUMN  customer_id  BIGINT NOT NULL;
;
ALTER TABLE  car_orders
ADD CONSTRAINT  customer_id
  FOREIGN KEY ( customer_id )
  REFERENCES  customers  ( id )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
