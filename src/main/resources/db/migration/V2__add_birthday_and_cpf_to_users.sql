-- Migration: Add birthday and cpf columns to users table
-- Description: Adds birthday (date) and cpf (string) columns to the users table

-- Add birthday column
ALTER TABLE users ADD COLUMN IF NOT EXISTS birthday DATE;

-- Add cpf column with unique constraint
ALTER TABLE users ADD COLUMN IF NOT EXISTS cpf VARCHAR(11);

-- Create unique index on cpf (only for non-null values)
CREATE UNIQUE INDEX IF NOT EXISTS idx_users_cpf_unique ON users(cpf) WHERE cpf IS NOT NULL;

-- Add comments to new columns
COMMENT ON COLUMN users.birthday IS 'User date of birth';
COMMENT ON COLUMN users.cpf IS 'User CPF (Brazilian tax ID), must be unique and contain 11 digits';
