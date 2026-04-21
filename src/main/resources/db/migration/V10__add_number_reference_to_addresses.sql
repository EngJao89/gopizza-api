-- Migration: Add number reference field to addresses
-- Description: Campo numero/identificador isolado (ex.: QD.10 LT.30)

ALTER TABLE addresses
    ADD COLUMN number_reference VARCHAR(40);

UPDATE addresses
SET number_reference = 'S/N'
WHERE number_reference IS NULL;

ALTER TABLE addresses
    ALTER COLUMN number_reference SET NOT NULL;

COMMENT ON COLUMN addresses.number_reference IS 'Numero/identificador do endereco (ex.: QD.10 LT.30)';
