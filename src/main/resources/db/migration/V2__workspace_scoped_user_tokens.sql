DO $$
DECLARE
    constraint_name text;
BEGIN
    IF to_regclass('public.user_tokens') IS NOT NULL THEN
        ALTER TABLE public.user_tokens
            ADD COLUMN IF NOT EXISTS workspace_id varchar(255);

        FOR constraint_name IN
            SELECT c.conname
            FROM pg_constraint c
            WHERE c.conrelid = 'public.user_tokens'::regclass
              AND c.contype = 'u'
              AND cardinality(c.conkey) = 1
              AND (
                  SELECT a.attname
                  FROM pg_attribute a
                  WHERE a.attrelid = c.conrelid
                    AND a.attnum = c.conkey[1]
              ) = 'user_id'
        LOOP
            EXECUTE format('ALTER TABLE public.user_tokens DROP CONSTRAINT %I', constraint_name);
        END LOOP;

        IF to_regclass('public.workspace_member') IS NOT NULL THEN
            UPDATE public.user_tokens token
            SET workspace_id = memberships.workspace_id
            FROM (
                SELECT user_id, MIN(workspace_id) AS workspace_id
                FROM public.workspace_member
                GROUP BY user_id
                HAVING COUNT(DISTINCT workspace_id) = 1
            ) memberships
            WHERE token.user_id = memberships.user_id
              AND token.workspace_id IS NULL;
        END IF;

        IF to_regclass('public.workspaces') IS NOT NULL
           AND NOT EXISTS (
               SELECT 1
               FROM pg_constraint
               WHERE conrelid = 'public.user_tokens'::regclass
                 AND conname = 'fk_user_tokens_workspace'
           ) THEN
            ALTER TABLE public.user_tokens
                ADD CONSTRAINT fk_user_tokens_workspace
                FOREIGN KEY (workspace_id) REFERENCES public.workspaces (id);
        END IF;

        IF NOT EXISTS (
            SELECT 1
            FROM pg_constraint
            WHERE conrelid = 'public.user_tokens'::regclass
              AND conname = 'uk_user_tokens_user_workspace'
        ) THEN
            ALTER TABLE public.user_tokens
                ADD CONSTRAINT uk_user_tokens_user_workspace
                UNIQUE (user_id, workspace_id);
        END IF;
    END IF;
END;
$$;
