--
-- Insert some data
--

INSERT INTO users(id, creation_date, login, password) VALUES (1, NOW(), 'mickael', 'azerty123');
INSERT INTO users(id, creation_date, login, password) VALUES (2, NOW(), 'houssem', 'qwerty123');

INSERT INTO tweets(creation_date, message, user_id) VALUES(NOW(), 'Hello MixIT 2018!', 1);
INSERT INTO tweets(creation_date, message, user_id) VALUES(NOW(), 'How are you today?', 2);
