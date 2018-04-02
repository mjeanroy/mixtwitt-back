--
-- Insert some data
--

INSERT INTO users(id, creation_date, login, password) VALUES (1, NOW(), 'mickael', 'azerty123');
INSERT INTO users(id, creation_date, login, password) VALUES (2, NOW(), 'houssem', 'qwerty123');

INSERT INTO tweets(id, creation_date, message, user_id) VALUES('3a7b6541-7724-497e-b6fb-17d886018ffe', NOW(), 'Hello MixIT 2018!', 1);
INSERT INTO tweets(id, creation_date, message, user_id) VALUES('0555051a-ec9c-48e8-9b44-0e10df80dfa2', NOW(), 'How are you today?', 2);
