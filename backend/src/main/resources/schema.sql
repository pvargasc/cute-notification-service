CREATE TABLE status
(
    code VARCHAR(50) NOT NULL PRIMARY KEY
);

CREATE TABLE categories
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    type        VARCHAR(50) NOT NULL,
    status_code VARCHAR(50) NOT NULL,
    FOREIGN KEY (status_code) REFERENCES status (code)
);


CREATE TABLE channels
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    type        VARCHAR(50) NOT NULL,
    status_code VARCHAR(50) NOT NULL,
    FOREIGN KEY (status_code) REFERENCES status (code)
);


CREATE TABLE users
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    first_name   VARCHAR(50)  NOT NULL,
    last_name    VARCHAR(50)  NOT NULL,
    phone_number CHAR(10)     NOT NULL,
    email        VARCHAR(100) NOT NULL,
    status_code  VARCHAR(50)  NOT NULL,
    FOREIGN KEY (status_code) REFERENCES status (code)
);


CREATE TABLE user_category
(
    user_id     INT NOT NULL,
    category_id INT NOT NULL,
    PRIMARY KEY (user_id, category_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (category_id) REFERENCES categories (id)
);


CREATE TABLE user_channel
(
    user_id     INT NOT NULL,
    channel_id  INT NOT NULL,
    PRIMARY KEY (user_id, channel_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (channel_id) REFERENCES channels (id)
);


CREATE TABLE notifications
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    category_id INT          NOT NULL,
    channel_id  INT          NOT NULL,
    user_id     INT          NOT NULL,
    sent_at     TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    message     VARCHAR(100) NOT NULL,
    FOREIGN KEY (category_id) REFERENCES categories (id),
    FOREIGN KEY (channel_id) REFERENCES channels (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);
