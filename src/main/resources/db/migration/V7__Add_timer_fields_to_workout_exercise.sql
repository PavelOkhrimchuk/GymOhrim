ALTER TABLE workout_exercise
    ADD COLUMN start_time BIGINT,
ADD COLUMN is_timer_running BOOLEAN DEFAULT FALSE;
