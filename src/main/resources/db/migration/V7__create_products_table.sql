-- Migration: Create products table
-- Description: Cadastro de produtos com marca, título, descrição, conteúdo e imagem

CREATE TABLE IF NOT EXISTS products (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    marca VARCHAR(120) NOT NULL,
    titulo VARCHAR(200) NOT NULL,
    descricao TEXT NOT NULL,
    conteudo TEXT NOT NULL,
    image_url VARCHAR(500) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL DEFAULT NULL,
    CONSTRAINT products_marca_titulo_unique UNIQUE (marca, titulo)
);

CREATE INDEX IF NOT EXISTS idx_products_marca ON products(marca);
CREATE INDEX IF NOT EXISTS idx_products_titulo ON products(titulo);

COMMENT ON TABLE products IS 'Produtos com informações de catálogo';
COMMENT ON COLUMN products.marca IS 'Marca do produto';
COMMENT ON COLUMN products.titulo IS 'Título do produto';
COMMENT ON COLUMN products.descricao IS 'Descrição resumida';
COMMENT ON COLUMN products.conteudo IS 'Conteúdo detalhado (texto longo)';
COMMENT ON COLUMN products.image_url IS 'URL da imagem (ex.: /api/images/arquivo.jpg)';
