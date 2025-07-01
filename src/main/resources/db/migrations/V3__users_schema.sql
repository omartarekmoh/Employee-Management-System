-- User Table
CREATE TABLE users (
                          id BIGSERIAL PRIMARY KEY,
                          username VARCHAR(255) UNIQUE NOT NULL,
                          email VARCHAR(255) UNIQUE NOT NULL,
                          password VARCHAR(255) NOT NULL,
                          role VARCHAR(50) NOT NULL CHECK (role IN ('USER', 'ADMIN')),
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);