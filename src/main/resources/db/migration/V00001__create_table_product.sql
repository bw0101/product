-- Drop the table if it already exists
DROP TABLE IF EXISTS Product;


CREATE TABLE IF NOT EXISTS Product (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(255),
    value BIGINT,
    status VARCHAR(255)
);