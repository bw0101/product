-- Drop the table if it already exists
DROP TABLE IF EXISTS Customer;

CREATE TABLE IF NOT EXISTS Customer (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255)
);