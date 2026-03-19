INSERT INTO users(username, password, email, is_active) VALUES('admin', '$2a$10$mPT9UbKzJbs/cbyB/yLEXeXpseD7RjLICRysZbNELgbNr6OlFDOWK', 'admin@gmail.com', 1);

INSERT INTO user_roles(user_id, role_id) VALUES(1, 1);

INSERT INTO user_roles(user_id, role_id) VALUES(1, 2);