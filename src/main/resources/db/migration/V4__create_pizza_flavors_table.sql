-- Migration: Create pizza_flavors table
-- Description: Creates the pizza_flavors table for storing pizza flavor information

CREATE TABLE IF NOT EXISTS pizza_flavors (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT NOT NULL,
    available_options JSONB DEFAULT '[]'::jsonb,
    sizes_and_prices JSONB NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL DEFAULT NULL,
    CONSTRAINT pizza_flavors_name_unique UNIQUE (name)
);

-- Create index on name for faster lookups
CREATE INDEX IF NOT EXISTS idx_pizza_flavors_name ON pizza_flavors(name);

-- Create GIN index on JSONB columns for efficient JSON queries
CREATE INDEX IF NOT EXISTS idx_pizza_flavors_options ON pizza_flavors USING GIN (available_options);
CREATE INDEX IF NOT EXISTS idx_pizza_flavors_sizes_prices ON pizza_flavors USING GIN (sizes_and_prices);

-- Add comments to table and columns
COMMENT ON TABLE pizza_flavors IS 'Table to store pizza flavor information';
COMMENT ON COLUMN pizza_flavors.id IS 'Unique identifier for the pizza flavor (UUID)';
COMMENT ON COLUMN pizza_flavors.name IS 'Name of the pizza flavor';
COMMENT ON COLUMN pizza_flavors.description IS 'Description of ingredients';
COMMENT ON COLUMN pizza_flavors.available_options IS 'Available optional extras (e.g., stuffed crust) as JSON array';
COMMENT ON COLUMN pizza_flavors.sizes_and_prices IS 'Available sizes (P, M, G) and their prices as JSON object';
COMMENT ON COLUMN pizza_flavors.created_at IS 'Record creation timestamp';
COMMENT ON COLUMN pizza_flavors.updated_at IS 'Record last update timestamp';
