CREATE EXTENSION IF NOT EXISTS pg_trgm;

CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       name VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       auth_provider_type VARCHAR(255)
);

CREATE TABLE user_profile (
                              id SERIAL PRIMARY KEY,
                              user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                              weight NUMERIC(10, 2) CHECK (weight >= 0.0),
                              height NUMERIC(10, 2) CHECK (height >= 0.0),
                              gender VARCHAR(50),
                              birth_date DATE,
                              profile_picture_url VARCHAR(1000)
);

CREATE TABLE product (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(255),
                         barcode VARCHAR(255) UNIQUE
);

CREATE TABLE daily_record (
                              id SERIAL PRIMARY KEY,
                              user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                              date DATE
);

CREATE TABLE workout (
                         id SERIAL PRIMARY KEY,
                         daily_record_id INT NOT NULL REFERENCES daily_record(id) ON DELETE CASCADE,
                         workout_type VARCHAR(255),
                         notes TEXT
);

CREATE TABLE exercise (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          description TEXT,
                            muscle_group VARCHAR(50),
                          media_url VARCHAR(1000)
);

CREATE TABLE nutrition (
                           id SERIAL PRIMARY KEY,
                           daily_record_id INT NOT NULL REFERENCES daily_record(id) ON DELETE CASCADE,
                           product_id INT NOT NULL REFERENCES product(id) ON DELETE CASCADE,
                           calories INT,
                           protein NUMERIC(10, 2),
                           fat NUMERIC(10, 2),
                           carbohydrates NUMERIC(10, 2),
                           grams NUMERIC(10, 2)
);

CREATE TABLE workout_exercise (
                                  id SERIAL PRIMARY KEY,
                                  workout_id INT NOT NULL REFERENCES workout(id) ON DELETE CASCADE,
                                  exercise_id INT NOT NULL REFERENCES exercise(id) ON DELETE CASCADE,
                                  sets INT,
                                  reps INT,
                                  weight NUMERIC(10, 2)
);

