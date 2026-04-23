-- Migration: Add product value field
-- Description: Adiciona coluna de valor/preco aos produtos

ALTER TABLE products
    ADD COLUMN valor NUMERIC(12,2);

UPDATE products
SET valor = 0.01
WHERE valor IS NULL;

ALTER TABLE products
    ALTER COLUMN valor SET NOT NULL;

COMMENT ON COLUMN products.valor IS 'Valor do produto';
