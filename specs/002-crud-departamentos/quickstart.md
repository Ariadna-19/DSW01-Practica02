# Quickstart - CRUD de Departamentos

## Prerrequisitos
- Java 17
- Maven 3.9+
- Docker y Docker Compose

## 1) Levantar PostgreSQL
```bash
docker compose -f docker/docker-compose.yml up -d
```

## 2) Aplicar script SQL (paso 1 del plan)
El script se mantiene en:
- `src/main/resources/schema.sql`

Debe incluir:
- Creación de `departamentos(id, nombre, descripcion)`.
- Alter de `empleados` para agregar `departamento_id`.
- FK `empleados.departamento_id -> departamentos.id` con restricción de borrado.

## 3) Ejecutar API
```bash
mvn spring-boot:run
```

## 4) Credenciales Basic Auth
- usuario: `admin`
- contraseña: `admin123`

## 5) Ejemplo de uso end-to-end (paso 5 del plan)

### 5.1 Crear departamento
```bash
curl -u admin:admin123 -X POST http://localhost:8080/api/departamentos \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Recursos Humanos","descripcion":"Gestión de talento"}'
```
Respuesta esperada: `201` con `id` del departamento.

### 5.2 Crear empleado asociado al departamento
```bash
curl -u admin:admin123 -X POST http://localhost:8080/api/empleados \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Ana","direccion":"Calle 1","telefono":"555-1111","departamentoId":1}'
```
Respuesta esperada: `201`.

### 5.3 Intentar eliminar departamento con empleados
```bash
curl -u admin:admin123 -X DELETE http://localhost:8080/api/departamentos/1 -i
```
Respuesta esperada: `409 Conflict` (integridad referencial).

### 5.4 Listar departamentos
```bash
curl -u admin:admin123 http://localhost:8080/api/departamentos
```
Respuesta esperada: `200`.

## 6) Ver contrato OpenAPI
- `specs/002-crud-departamentos/contracts/openapi.yaml`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
