CREATE TABLE product (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    value BIGINT,
    status VARCHAR(255)
);
INSERT INTO product (name, value, status) VALUES ('Product 1', 100, 'Available');
INSERT INTO product (name, value, status) VALUES ('Product 2', 200, 'Available');
INSERT INTO product (name, value, status) VALUES ('Product 3', 150, 'Unavailable');
INSERT INTO product (name, value, status) VALUES ('Product 4', 250, 'Available');
INSERT INTO product (name, value, status) VALUES ('Product 5', 300, 'Unavailable');
INSERT INTO product (name, value, status) VALUES ('Product 6', 600, 'Pending');
