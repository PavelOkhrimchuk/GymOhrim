
INSERT INTO users (email, name, password, auth_provider_type) VALUES
                                                                  ('user1@example.com', 'User One', 'password123', null),
                                                                  ('user2@example.com', 'User Two', 'password456', null);

INSERT INTO user_profile (user_id, weight, height, gender, birth_date, profile_picture_url) VALUES
                                                                                                (1, 70.0, 175.0, 'Male', '1990-01-01', 'http://example.com/profile1.jpg'),
                                                                                                (2, 60.0, 165.0, 'Female', '1992-02-02', 'http://example.com/profile2.jpg');

INSERT INTO daily_record (user_id, date) VALUES
                                             (1, '2024-10-01'),
                                             (2, '2024-10-02');

INSERT INTO product (name, barcode) VALUES
                                        ('Apple', '1234567890123'),
                                        ('Banana', '2345678901234');

INSERT INTO nutrition (daily_record_id, calories, protein, fat, carbohydrates, product_id, grams) VALUES
                                                                                                      (1, 95, 0.5, 0.3, 25, 1, 100.0),
                                                                                                      (1, 105, 1.3, 0.4, 27, 2, 150.0);

INSERT INTO workout (daily_record_id, workout_type, notes) VALUES
                                                               (1, 'Strength Training', 'Focus on upper body.'),
                                                               (2, 'Cardio', '30 minutes of running.');

INSERT INTO exercise (name, description, muscle_group, media_url) VALUES
                                                                      ('Push Up', 'A basic exercise to strengthen the upper body.', 'Chest', 'http://example.com/pushup.jpg'),
                                                                      ('Squat', 'A basic exercise to strengthen the legs.', 'Legs', 'http://example.com/squat.jpg');

INSERT INTO workout_exercise (workout_id, exercise_id, sets, reps, weight) VALUES
                                                                               (1, 1, 3, 10, 60.0),
                                                                               (1, 2, 3, 15, 40.0);
