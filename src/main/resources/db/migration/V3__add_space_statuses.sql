ALTER TABLE spaces
    ADD COLUMN IF NOT EXISTS statuses jsonb;
