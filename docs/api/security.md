# Seguridad del módulo de empleados

## Autenticación básica

Todos los endpoints bajo `/api/empleados/**` requieren HTTP Basic Authentication.

Valores de desarrollo por defecto:

- Usuario: `admin`
- Contraseña: `admin123`

## Variables de entorno recomendadas

Para entornos distintos a desarrollo, sobrescribir credenciales con variables de entorno:

- `SPRING_SECURITY_USER_NAME`
- `SPRING_SECURITY_USER_PASSWORD`
- `SPRING_SECURITY_USER_ROLES`

## Variables de base de datos

- `DB_HOST` (default: `localhost`)
- `DB_PORT` (default: `5432`)
- `DB_NAME` (default: `dsw01_practica02`)
- `DB_USER` (default: `postgres`)
- `DB_PASSWORD` (default: `postgres`)

## Nota operativa

No usar las credenciales por defecto en producción. Deben inyectarse desde secretos del entorno.
