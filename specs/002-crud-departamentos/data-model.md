# Data Model - CRUD de Departamentos

## Entity: Departamento

### Description
Unidad organizacional a la que pertenecen empleados.

### Fields
- **id**
  - Type: `BIGINT`
  - Constraints: primary key, not null, generated identity/sequence
- **nombre**
  - Type: `VARCHAR(100)`
  - Constraints: not null, longitud 1..100
- **descripcion**
  - Type: `VARCHAR(255)`
  - Constraints: nullable, longitud 0..255

### Validation Rules
- `nombre` obligatorio en creación y actualización.
- `nombre` debe respetar longitud máxima definida (100).
- `descripcion` opcional con longitud máxima de 255.

## Entity: Empleado (modificado)

### Description
Registro de empleado existente, ahora asociado obligatoriamente a un departamento.

### Fields
- **clave**
  - Type: `VARCHAR(20)`
  - Constraints: primary key, formato `E-0001`
- **nombre**
  - Type: `VARCHAR(100)`
  - Constraints: not null
- **direccion**
  - Type: `VARCHAR(100)`
  - Constraints: not null
- **telefono**
  - Type: `VARCHAR(100)`
  - Constraints: not null
- **departamento_id**
  - Type: `BIGINT`
  - Constraints: not null, foreign key -> `departamentos.id`, delete restrict

### Validation Rules
- `departamento_id` obligatorio en creación y actualización de empleado.
- Rechazar operación si `departamento_id` no existe.

## Relationships
- **Departamento 1 --- N Empleado**
  - Un departamento contiene cero o muchos empleados.
  - Un empleado pertenece a un único departamento.

## Read Models

### DepartamentoResponse
- `id`, `nombre`, `descripcion`

### EmpleadoResponse (extendido)
- `clave`, `nombre`, `direccion`, `telefono`, `departamentoId`

## State Transitions
- **Departamento CREATE**: no existe -> persistido con `id` generado.
- **Departamento UPDATE**: existe -> actualizado, conserva `id`.
- **Departamento DELETE**:
  - sin empleados asociados -> eliminado.
  - con empleados asociados -> rechazado por integridad referencial.
- **Empleado CREATE/UPDATE**: válido solo si referencia un `departamento_id` existente.
