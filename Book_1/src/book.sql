DROP DATABASE IF EXISTS books;
CREATE DATABASE IF NOT EXISTS books;
USE books;

--Opprett tabellen for bøker
CREATE TABLE IF NOT EXISTS Book (
	isbn VARCHAR(6) PRIMARY KEY,
	published DATE,
	title VARCHAR(255) NOT NULL,
	author VARCHAR(255) NOT NULL,
	numberOfPages INT, 
	genre VARCHAR(50),
	minutesPerPage INT
);

-- Opprett tabellen for kapitler
CREATE TABLE IF NOT EXISTS Chapter (
	id INT AUTO_INCREMENT PRIMARY KEY,
	book_isbn VARCHAR(6) NOT NULL,
	title VARCHAR(255) NOT NULL,
	numberOfPages INT,
	FOREIGN KEY (book_isbn) REFERENCES Book(isbn)
);

INSERT INTO Book (isbn, published, title, author, numberOfPages, genre, minutesPerPage)
VALUES ('123456', '2023-01-15', 'The Mysterious Case', 'Sherlock Holmes', 300, 'CRIME', 2);

INSERT INTO Book (isbn, published, title, author, numberOfPages, genre, minutesPerPage)
VALUES ('654321', '2022-08-20', 'The Silent Witness', 'Detective Smith', 280, 'CRIME', 2);

INSERT INTO Book (isbn, published, title, author, numberOfPages, genre, minutesPerPage)
VALUES ('098765', '2021-11-05', 'Code Red: Cyber Espionage', 'Agent X', 350, 'ACTION', 3);

-- Bok: The Mysterious Case (ISBN: 123456)
INSERT INTO Chapter (book_isbn, title, numberOfPages)
VALUES ('123456', 'Chapter 1: The Stolen Diamond', 20),
	('123456', 'Chapter 2: The Hidden Clues', 18),
	('123456', 'Chapter 3: The Cryptic Message', 22);

-- Bok: The Silent Witness (ISBN: 654321)
INSERT INTO Chapter (book_isbn, title, numberOfPages)
VALUES ('654321', 'Prologue: A Dark Night', 15),
	('654321', 'Chapter 1: Crime Scene', 25),
	('654321', 'Chapter 2: Unraveling the Mystery', 20);

-- Bok: Code Red: Cyber Espionage (ISBN: 098765)
INSERT INTO Chapter (book_isbn, title, numberOfPages)
VALUES ('098765', 'Chapter 1: Breach Detected', 30),
	('098765', 'Chapter 2: The Hackers Trail', 28),
	('098765', 'Chapter 3: Countdown to Chaoes', 32);

