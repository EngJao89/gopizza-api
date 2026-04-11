-- Indica se o usuário é administrador (UI web/mobile e autorização)

ALTER TABLE users
    ADD COLUMN admin BOOLEAN NOT NULL DEFAULT false;

COMMENT ON COLUMN users.admin IS 'true se o usuário é administrador';
