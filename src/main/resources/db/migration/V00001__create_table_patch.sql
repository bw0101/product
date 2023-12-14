-- Drop the table if it already exists
DROP TABLE IF EXISTS Patch;


CREATE TABLE IF NOT EXISTS Patch (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    customer VARCHAR(255),
    type VARCHAR(50),
    version VARCHAR(50),
    site VARCHAR(255),
    status VARCHAR(50)
);