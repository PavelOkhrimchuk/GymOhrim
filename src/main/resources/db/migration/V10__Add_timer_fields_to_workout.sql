ALTER TABLE workout
DROP COLUMN IF EXISTS start_time,
DROP COLUMN IF EXISTS is_timer_running,
DROP COLUMN IF EXISTS end_time;


ALTER TABLE workout
ADD COLUMN start_time TIMESTAMP,
ADD COLUMN end_time TIMESTAMP,
ADD COLUMN duration BIGINT;

