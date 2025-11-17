-- Khoi tao DATABASE library_management
CREATE DATABASE library_management;
GO

USE library_management;
GO

-- Tao bang Books
CREATE TABLE books (
	book_id INT IDENTITY(1,1) PRIMARY KEY,
	book_name NVARCHAR(255) NOT NULL,
	genre NVARCHAR(100) NOT NULL,
	author NVARCHAR(255) NOT NULL,
	book_length INT NOT NULL,
	publish_year INT NOT NULL,
	book_quantity INT NOT NULL,
	available BIT NOT NULL DEFAULT 1
);

-- Tao bang User
CREATE TABLE [users] (
	user_id INT IDENTITY(1,1) PRIMARY KEY,
	name NVARCHAR(255) NOT NULL,
	phone NVARCHAR(20),
	email NVARCHAR(255),
	dob DATE NOT NULL,
	role NVARCHAR(20) DEFAULT 'reader'
)

-- Tao bang Loan
CREATE TABLE loan (
	loan_id INT IDENTITY(1,1) PRIMARY KEY,
	user_id INT NOT NULL,
	book_id INT NOT NULL, 
	borrow_date DATE NOT NULL,
	expire_date DATE NOT NULL,
	return_date DATE NULL,
	late_fee INT DEFAULT 0,

	FOREIGN KEY (user_id) REFERENCES [users](user_id),
	FOREIGN KEY (book_id) REFERENCES books(book_id)
)

-- Tao du lieu cho bang Books
INSERT INTO books (book_name, genre, author, book_length, publish_year, book_quantity, available)
VALUES
('Dac Nhan Tam', 'Self-help', 'Dale Carnegie', 300, 1936, 5, 1),
('Clean Code', 'Programming', 'Robert C. Martin', 450, 2008, 3, 1),
('Harry Potter', 'Fantasy', 'J.K. Rowling', 400, 1997, 10, 1);

-- Tao du lieu cho bang Users
INSERT INTO [users] (name, phone, email, dob, role)
VALUES
('Nguyen Van A', '0123456789', 'a@gmail.com', '2000-01-01', 'reader'),
('Tran Thi B', '0987654321', 'b@gmail.com', '1995-05-05', 'librarian');

-- Tao du lieu cho bang Loan
INSERT INTO loan (user_id, book_id, borrow_date, expire_date, return_date, late_fee)
VALUES
(1, 1, '2025-11-01', '2025-11-10', NULL, 0),
(2, 2, '2025-11-05', '2025-11-12', NULL, 0);

-- Check xem du lieu co chua
select * from dbo.books
select * from dbo.users
select * from dbo.loan