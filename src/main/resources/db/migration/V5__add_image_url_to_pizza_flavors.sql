-- Migration: Add image_url column to pizza_flavors table
-- Description: Adds image_url column to store the path/URL of the pizza flavor image

ALTER TABLE pizza_flavors ADD COLUMN IF NOT EXISTS image_url VARCHAR(500);

-- Add comment to new column
COMMENT ON COLUMN pizza_flavors.image_url IS 'URL or path to the pizza flavor image';
