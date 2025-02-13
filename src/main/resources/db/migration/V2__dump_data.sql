-- Sample Data
INSERT INTO products (name, price)
VALUES ('Laptop', 1200.00);
INSERT INTO products (name, price)
VALUES ('Smartphone', 800.00);
INSERT INTO promotions (type, description)
VALUES ('BUY_ONE_GET_HALF_OFF', 'Buy 1 get 50% off the second');
INSERT INTO customers (name, phone, address)
VALUES ('Ruby', '0123456789', '123 Dongnai St');
INSERT INTO customers (name, phone, address)
VALUES ('Joseph', '9876543210', '456 Dongnai St');
INSERT INTO users (username, password, role)
VALUES ('admin', '$2a$10$1GkC95Ss4EcT4KDtVl6EEORhaguTz7GhyL7PnRpwGDrTu06NqGxPG', 'ADMIN');
INSERT INTO users (username, password, role)
VALUES ('customer', '$2a$10$cqp7U3308q9jzCtiK7RJ.ud4cwoZfnF11BpcE8eLaM4Xr9p0E52DO', 'CUSTOMER');
