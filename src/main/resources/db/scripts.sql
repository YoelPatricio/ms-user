-- Script para crear la tabla 'users'
CREATE TABLE users (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    created TIMESTAMP,
    modified TIMESTAMP,
    last_login TIMESTAMP,
    token VARCHAR(512),
    is_active BOOLEAN
);

-- Script para crear la tabla 'phones'
CREATE TABLE phones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    number VARCHAR(20) NOT NULL,
    citycode VARCHAR(10) NOT NULL,
    countrycode VARCHAR(10) NOT NULL,
    user_id VARCHAR(36),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
