-- Migration: Add delivery number and neighborhood to orders
-- Description: Separa rua, numero e bairro no endereco de entrega

ALTER TABLE orders
    ADD COLUMN IF NOT EXISTS delivery_number VARCHAR(20) NOT NULL DEFAULT '';

ALTER TABLE orders
    ADD COLUMN IF NOT EXISTS delivery_neighborhood VARCHAR(120) NOT NULL DEFAULT '';
