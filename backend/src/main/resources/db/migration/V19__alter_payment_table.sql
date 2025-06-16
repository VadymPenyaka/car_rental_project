ALTER TABLE payment
    ADD COLUMN IF NOT EXISTS currency VARCHAR(3),
    ADD COLUMN IF NOT EXISTS session_id VARCHAR(100),
    ADD COLUMN IF NOT EXISTS receipt_url VARCHAR(500),
    ADD COLUMN IF NOT EXISTS created_at TIMESTAMP DEFAULT NOW(),
    ADD COLUMN IF NOT EXISTS paid_at TIMESTAMP;

ALTER TABLE payment
    ALTER COLUMN amount TYPE DECIMAL(10,2) USING (amount::DECIMAL(10,2) / 100);

DO $$
    BEGIN
        IF EXISTS (SELECT 1 FROM information_schema.columns
                   WHERE table_name = 'payment' AND column_name = 'payment_status') THEN
            ALTER TABLE payment RENAME COLUMN payment_status TO status;
        END IF;
    END $$;

ALTER TABLE payment
    ALTER COLUMN status TYPE VARCHAR(20),
    ALTER COLUMN payment_intent_id TYPE VARCHAR(100);

UPDATE payment
SET created_at = payment_date
WHERE created_at IS NULL;

ALTER TABLE payment
    DROP COLUMN IF EXISTS payment_method,
    DROP COLUMN IF EXISTS payment_date,
    DROP COLUMN IF EXISTS card_exp_year,
    DROP COLUMN IF EXISTS card_exp_month;

UPDATE payment
SET currency = 'USD'
WHERE currency IS NULL;

ALTER TABLE payment
    ALTER COLUMN currency SET NOT NULL,
    ALTER COLUMN created_at SET NOT NULL;

DO $$
    BEGIN
        BEGIN
            ALTER TABLE payment ADD CONSTRAINT uk_payment_intent_id UNIQUE (payment_intent_id);
        EXCEPTION WHEN duplicate_table THEN
            NULL;
        END;

        BEGIN
            ALTER TABLE payment ADD CONSTRAINT uk_session_id UNIQUE (session_id);
        EXCEPTION WHEN duplicate_table THEN
            NULL;
        END;
    END $$;

CREATE INDEX IF NOT EXISTS idx_payment_status ON payment(status);
CREATE INDEX IF NOT EXISTS idx_payment_created_at ON payment(created_at);
CREATE INDEX IF NOT EXISTS idx_payment_order_id ON payment(order_id);