
ALTER TABLE workout_exercise
DROP COLUMN IF EXISTS start_time,
DROP COLUMN IF EXISTS is_timer_running;


ALTER TABLE workout
    ADD COLUMN start_time BIGINT,
ADD COLUMN is_timer_running BOOLEAN DEFAULT FALSE;
