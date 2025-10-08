-- Add email_subscription table

CREATE TABLE IF NOT EXISTS email_subscription (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    INDEX idx_user_id (user_id),
    INDEX idx_email (email)
);
