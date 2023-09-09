SELECT constraint_name FROM information_schema.constraint_column_usage WHERE table_name = 'user_access' AND column_name = 'access_id' and constraint_name <> 'unique_user_access';

ALTER TABLE user_access DROP CONSTRAINT "uk_ojlpsp4dq6pt966i85jb7i386";