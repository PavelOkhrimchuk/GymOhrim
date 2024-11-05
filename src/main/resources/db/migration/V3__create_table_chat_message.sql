CREATE TABLE chat_message (
                              id SERIAL PRIMARY KEY,
                              sender VARCHAR(255) NOT NULL,
                              content TEXT NOT NULL,
                              timestamp VARCHAR(255) NOT NULL,
                              user_id INTEGER NOT NULL,
                              CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);