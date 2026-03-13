create database storybookdb;
use storybookdb;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('USER','ADMIN') DEFAULT 'USER' NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE authors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    bio TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT
);

CREATE TABLE storybooks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    author_id BIGINT,
    category_id BIGINT,
    price DECIMAL(10,2),
    audio_url VARCHAR(500),
    cover_image_url VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (author_id) REFERENCES authors(id),
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE cart (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE cart_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cart_id BIGINT,
    storybook_id BIGINT,
    quantity INT DEFAULT 1,

    FOREIGN KEY (cart_id) REFERENCES cart(id),
    FOREIGN KEY (storybook_id) REFERENCES storybooks(id)
);

CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    total_amount DECIMAL(10,2),
    status ENUM('CREATED','PENDING','PAID','FAILED'),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT,
    storybook_id BIGINT,
    price DECIMAL(10,2),

    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (storybook_id) REFERENCES storybooks(id)
);

CREATE TABLE payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT,
    payment_gateway VARCHAR(50),
    transaction_id VARCHAR(200),
    payment_status ENUM('SUCCESS','FAILED','PENDING'),
    paid_at TIMESTAMP,

    FOREIGN KEY (order_id) REFERENCES orders(id)
);

CREATE TABLE user_library (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    storybook_id BIGINT,
    purchased_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (storybook_id) REFERENCES storybooks(id)
);

CREATE TABLE reviews (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    storybook_id BIGINT,
    rating INT,
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (storybook_id) REFERENCES storybooks(id)
);

CREATE TABLE wishlist (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    storybook_id BIGINT,

    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (storybook_id) REFERENCES storybooks(id)
);

-- =============================================
-- DUMMY DATA
-- =============================================

-- Users (passwords are BCrypt of 'Admin@123' and 'User@123')
INSERT INTO users (name, email, password, role) VALUES
('Super Admin',  'admin@storybook.com', '$2a$10$7QFoX1pVZ8oVnHMeNqICMuFArFIbJjLjwOQ2uKMG1xkVCwqMSWbFO', 'ADMIN'),
('John Doe',     'john@storybook.com',  '$2a$10$8tGQk3r4H6Z5kq1nPz5e8.Cv5pYBVJyBLrF3hD2yCFpBk9SJKlVoC', 'USER'),
('Jane Smith',   'jane@storybook.com',  '$2a$10$8tGQk3r4H6Z5kq1nPz5e8.Cv5pYBVJyBLrF3hD2yCFpBk9SJKlVoC', 'USER');

-- Authors
INSERT INTO authors (name, bio) VALUES
('J.K. Rowling',       'British author best known for the Harry Potter fantasy series.'),
('George R.R. Martin', 'American novelist and short story writer of fantasy, horror, and science fiction.'),
('Agatha Christie',    'English writer known for her detective novels and short story collections.'),
('J.R.R. Tolkien',     'English author and professor, creator of Middle-earth and The Lord of the Rings.');

-- Categories
INSERT INTO categories (name, description) VALUES
('Fantasy',   'Stories involving magic, mythical creatures, and imaginary worlds.'),
('Mystery',   'Suspenseful stories centered around solving a crime or puzzle.'),
('Adventure', 'Action-packed stories of exploration and daring quests.'),
('Thriller',  'Fast-paced stories designed to create tension and excitement.');

-- Storybooks
INSERT INTO storybooks (title, description, author_id, category_id, price, audio_url, cover_image_url) VALUES
('The Sorcerer''s Stone',        'A young boy discovers he is a wizard and enrolls in a school of magic.',                        1, 1, 9.99,  'https://cdn.storybook.com/audio/sorcerers-stone.mp3',          'https://cdn.storybook.com/covers/sorcerers-stone.jpg'),
('The Chamber of Secrets',       'Harry returns to Hogwarts and uncovers the mystery of the Chamber of Secrets.',                  1, 1, 9.99,  'https://cdn.storybook.com/audio/chamber-of-secrets.mp3',        'https://cdn.storybook.com/covers/chamber-of-secrets.jpg'),
('The Prisoner of Azkaban',      'Harry learns about a dangerous escaped prisoner with a mysterious connection to his past.',       1, 1, 9.99,  'https://cdn.storybook.com/audio/prisoner-of-azkaban.mp3',       'https://cdn.storybook.com/covers/prisoner-of-azkaban.jpg'),
('A Game of Thrones',            'Noble families fight for control of the mythical Seven Kingdoms.',                               2, 1, 12.99, 'https://cdn.storybook.com/audio/game-of-thrones.mp3',           'https://cdn.storybook.com/covers/game-of-thrones.jpg'),
('A Clash of Kings',             'The Seven Kingdoms are on the brink of war as rival claimants to the Iron Throne emerge.',       2, 3, 12.99, 'https://cdn.storybook.com/audio/clash-of-kings.mp3',            'https://cdn.storybook.com/covers/clash-of-kings.jpg'),
('A Storm of Swords',            'The War of the Five Kings reaches its bloody climax.',                                           2, 3, 12.99, 'https://cdn.storybook.com/audio/storm-of-swords.mp3',           'https://cdn.storybook.com/covers/storm-of-swords.jpg'),
('Murder on the Orient Express', 'Detective Hercule Poirot investigates a murder aboard a luxury train.',                          3, 2, 7.99,  'https://cdn.storybook.com/audio/orient-express.mp3',            'https://cdn.storybook.com/covers/orient-express.jpg'),
('And Then There Were None',     'Ten strangers are lured to an isolated island and begin to die one by one.',                     3, 2, 7.99,  'https://cdn.storybook.com/audio/and-then-there-were-none.mp3',  'https://cdn.storybook.com/covers/and-then-there-were-none.jpg'),
('Death on the Nile',            'Poirot investigates a murder during a cruise on the Nile River.',                                3, 4, 7.99,  'https://cdn.storybook.com/audio/death-on-the-nile.mp3',         'https://cdn.storybook.com/covers/death-on-the-nile.jpg'),
('The Fellowship of the Ring',   'A young hobbit embarks on a perilous quest to destroy a powerful ring.',                        4, 1, 11.99, 'https://cdn.storybook.com/audio/fellowship-of-the-ring.mp3',    'https://cdn.storybook.com/covers/fellowship-of-the-ring.jpg'),
('The Two Towers',               'The Fellowship is broken as the quest to destroy the One Ring continues across Middle-earth.',   4, 3, 11.99, 'https://cdn.storybook.com/audio/two-towers.mp3',                'https://cdn.storybook.com/covers/two-towers.jpg'),
('The Return of the King',       'The final battle for Middle-earth is fought as Frodo nears Mount Doom.',                        4, 1, 11.99, 'https://cdn.storybook.com/audio/return-of-the-king.mp3',        'https://cdn.storybook.com/covers/return-of-the-king.jpg');