CREATE TABLE IF NOT EXISTS push_subscription (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    endpoint VARCHAR(500) NOT NULL,
    p256dh VARCHAR(255) NOT NULL,
    auth VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    INDEX idx_user_id (user_id),
    INDEX idx_endpoint (endpoint)
);

CREATE TABLE IF NOT EXISTS notification_enabled (
    user_id VARCHAR(255) PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    channel VARCHAR(50) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    INDEX idx_type_channel (type, channel)
);

CREATE TABLE IF NOT EXISTS notification_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    created_at DATETIME(6),
    updated_at DATETIME(6),
    INDEX idx_user_id (user_id),
    INDEX idx_created_at (created_at)
);
