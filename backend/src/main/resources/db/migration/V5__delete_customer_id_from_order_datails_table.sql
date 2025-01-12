ALTER TABLE  orders_details
    DROP CONSTRAINT  orders_details_customer_id_fkey;
ALTER TABLE  orders_details
    DROP COLUMN  customer_id;
;