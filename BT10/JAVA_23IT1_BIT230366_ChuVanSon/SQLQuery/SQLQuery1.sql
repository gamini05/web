CREATE DATABASE BookStore;

Use BookStore;

CREATE TABLE Category (
    id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE Book (
    id INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    publish DATE,
    isbn VARCHAR(20) NOT NULL,
    categoryId INT,
    FOREIGN KEY (categoryId) REFERENCES Category(id)
);

SELECT * FROM Book
SELECT * FROM Category