CREATE TABLE url
(
    id SERIAL PRIMARY KEY,
    original_url TEXT NOT NULL,
    code VARCHAR(50) UNIQUE,
    total INT DEFAULT 0,
    site_id INT REFERENCES site(id)
);