ALTER TABLE lists
    ADD COLUMN IF NOT EXISTS parent_task_count integer,
    ADD COLUMN IF NOT EXISTS total_task_count integer,
    ADD COLUMN IF NOT EXISTS subtask_count integer;
