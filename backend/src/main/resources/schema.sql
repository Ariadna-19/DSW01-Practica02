CREATE SEQUENCE IF NOT EXISTS empleado_clave_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS departamentos (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS empleados (
    clave VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(100) NOT NULL,
    telefono VARCHAR(100) NOT NULL,
    username VARCHAR(60) NOT NULL,
    password_hash VARCHAR(100) NOT NULL,
    departamento_id BIGINT NOT NULL,
    CONSTRAINT fk_empleados_departamento
        FOREIGN KEY (departamento_id)
        REFERENCES departamentos(id)
        ON DELETE RESTRICT
);

ALTER TABLE empleados ADD COLUMN IF NOT EXISTS username VARCHAR(60);
ALTER TABLE empleados ADD COLUMN IF NOT EXISTS password_hash VARCHAR(100);
ALTER TABLE empleados ADD COLUMN IF NOT EXISTS departamento_id BIGINT;

INSERT INTO departamentos (nombre, descripcion)
SELECT 'General', 'Departamento por defecto para migración'
WHERE NOT EXISTS (SELECT 1 FROM departamentos);

UPDATE empleados
SET departamento_id = (SELECT id FROM departamentos ORDER BY id LIMIT 1)
WHERE departamento_id IS NULL;

UPDATE empleados
SET username = COALESCE(NULLIF(username, ''), lower(replace(clave, '-', ''))),
    password_hash = COALESCE(NULLIF(password_hash, ''), '$2a$10$0A1b2C3d4E5f6G7h8I9jUu4j9Qx0q2dQraNfMruM8WQWmIftnM8M2')
WHERE username IS NULL OR username = '' OR password_hash IS NULL OR password_hash = '';

ALTER TABLE empleados ALTER COLUMN username SET NOT NULL;
ALTER TABLE empleados ALTER COLUMN password_hash SET NOT NULL;
ALTER TABLE empleados ALTER COLUMN departamento_id SET NOT NULL;

ALTER TABLE empleados DROP CONSTRAINT IF EXISTS fk_empleados_departamento;

ALTER TABLE empleados
    ADD CONSTRAINT fk_empleados_departamento
    FOREIGN KEY (departamento_id)
    REFERENCES departamentos(id)
    ON DELETE RESTRICT;

CREATE UNIQUE INDEX IF NOT EXISTS uq_empleados_username ON empleados (lower(username));
