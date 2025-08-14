-- Create database
CREATE DATABASE IF NOT EXISTS pahana_edu;
USE pahana_edu;

-- =========================
-- 1. Customers Table
-- =========================
CREATE TABLE customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    account_number VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    address TEXT NOT NULL,
    telephone VARCHAR(20) NOT NULL
);

-- =========================
-- 2. Items Table
-- =========================
CREATE TABLE items (
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    item_code VARCHAR(50) NOT NULL,
    title VARCHAR(150) NOT NULL,
    author VARCHAR(100),
    category VARCHAR(50) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    stock INT DEFAULT 0 NOT NULL
);

-- =========================
-- 4. Bills Table
-- =========================
CREATE TABLE bills (
    bill_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    bill_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE
);

-- =========================
-- 5. Bill Items Table
-- =========================
CREATE TABLE bill_items (
    bill_item_id INT AUTO_INCREMENT PRIMARY KEY,
    bill_id INT NOT NULL,
    item_id INT NOT NULL,
    units_consumed INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (bill_id) REFERENCES bills(bill_id) ON DELETE CASCADE,
    FOREIGN KEY (item_id) REFERENCES items(item_id) ON DELETE CASCADE
);

-- =========================
-- 6. Sample Data
-- =========================


-- Customers
INSERT INTO customers (account_number, name, address, telephone) VALUES
('CUST001', 'John Doe', '123 Main Street', '0771234567'),
('CUST002', 'Jane Smith', '456 Second Street', '0779876543');

-- Items
INSERT INTO items (item_code, title, author, category, price, stock) VALUES
('BK001', 'Java Programming', 'James Gosling', 'Book', 2500.00, 15),
('BK002', 'HTML & CSS Guide', 'Jon Duckett', 'Book', 1800.00, 20),
('ST001', 'Notebook', NULL, 'Stationery', 150.00, 50);

-- Bills
INSERT INTO bills (customer_id, total_amount) VALUES
(1, 2650.00),
(2, 1800.00);

-- Bill Items
INSERT INTO bill_items (bill_id, item_id, units_consumed, price) VALUES
(1, 1, 1, 2500.00),
(1, 3, 3, 450.00),
(2, 2, 1, 1800.00);

