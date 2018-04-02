--
-- Simple DATABASE
--

CREATE TABLE users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  creation_date DATE NOT NULL,
  login varchar(500) NOT NULL,
  password varchar(500) NOT NULL
);

CREATE TABLE tweets (
  id VARCHAR(100) PRIMARY KEY,
  creation_date DATE NOT NULL,
  message TEXT NOT NULL,
  user_id BIGINT NOT NULL
);
