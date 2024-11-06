
ALTER TABLE chat_message
    ADD COLUMN user_id INTEGER;


ALTER TABLE chat_message
    ADD CONSTRAINT fk_user_id
        FOREIGN KEY (user_id) REFERENCES users(id);