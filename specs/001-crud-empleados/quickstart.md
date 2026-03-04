# Quickstart - CRUD de Empleados

## Prerequisitos
- Java 17
- Maven 3.9+
- Docker y Docker Compose

## 1) Levantar PostgreSQL con Docker
Usar el compose incluido en el repositorio:

```bash
docker compose -f docker/docker-compose.yml up -d
```

Esto levanta PostgreSQL accesible en `localhost:5432` y base `dsw01_practica02`.

## 2) Configurar variables si aplica
Verificar que `src/main/resources/application.properties` tenga:
- URL JDBC hacia PostgreSQL
- Usuario y contraseña de BD
- Usuario/contraseña de Basic Auth para entorno local

Variables opcionales:
- `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASSWORD`
- `SPRING_SECURITY_USER_NAME`, `SPRING_SECURITY_USER_PASSWORD`, `SPRING_SECURITY_USER_ROLES`

## 3) Ejecutar la aplicación
```bash
mvn spring-boot:run
```

## 4) Probar autenticación básica
Credenciales de desarrollo:
- usuario: `admin`
- contraseña: `admin123`

## 5) Probar endpoints CRUD
Base URL: `http://localhost:8080`
- `POST /api/empleados`
- `GET /api/empleados?page=0` (paginación fija de 5 registros)
- `GET /api/empleados/{clave}`
- `PUT /api/empleados/{clave}`
- `DELETE /api/empleados/{clave}`

Formato de clave esperado:
- `E-0001`, `E-0002`, `E-0003`, ...

## 6) Validar documentación Swagger
- UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## 7) Casos rápidos de validación
- Crear empleado con strings <= 100 (esperado: 201)
- Crear/actualizar con > 100 chars (esperado: 400)
- Verificar que la creación retorna `clave` con prefijo `E-` y correlativo con padding
- Verificar que `GET /api/empleados?page=0` retorna máximo 5 registros
- Consultar clave inexistente (esperado: 404)
- Consumir endpoint sin auth (esperado: 401)
