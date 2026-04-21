-- Migration: Create addresses table
-- Description: Enderecos de entrega vinculados a usuarios

CREATE TABLE IF NOT EXISTS addresses (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    street VARCHAR(180) NOT NULL,
    neighborhood VARCHAR(120) NOT NULL,
    zip_code VARCHAR(20) NOT NULL,
    city VARCHAR(120) NOT NULL,
    state VARCHAR(120) NOT NULL,
    country VARCHAR(120) NOT NULL,
    complement VARCHAR(200) NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL DEFAULT NULL,
    CONSTRAINT fk_addresses_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_addresses_user_id ON addresses(user_id);
CREATE INDEX IF NOT EXISTS idx_addresses_zip_code ON addresses(zip_code);

COMMENT ON TABLE addresses IS 'Enderecos de entrega por usuario';
