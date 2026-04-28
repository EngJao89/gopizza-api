-- Migration: Add image_url to order items
-- Description: Persiste URL da imagem de pizza/produto no item do pedido

ALTER TABLE order_items
    ADD COLUMN IF NOT EXISTS image_url VARCHAR(500);
