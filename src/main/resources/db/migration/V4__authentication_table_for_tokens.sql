CREATE TABLE authentication_tokens(
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL, -- One to One relationship with User
    access_token VARCHAR(500) NOT NULL,
    refresh_token VARCHAR(500) NOT NULL,
    is_active BOOLEAN NOT NULL, -- refresh token is active
    FOREIGN KEY (user_id) REFERENCES users(id)
)