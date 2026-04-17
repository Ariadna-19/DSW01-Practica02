# Data Model - Autenticación de empleados

## Entity: Empleado

### Description
Entidad principal existente del dominio que se extiende para soportar autenticación por usuario/contraseña.

### Fields
- **clave**
  - Type: `VARCHAR(20)`
  - Constraints: primary key, not null
- **nombre**
  - Type: `VARCHAR(100)`
  - Constraints: not null, longitud 1..100
- **direccion**
  - Type: `VARCHAR(100)`
  - Constraints: not null, longitud 1..100
- **telefono**
  - Type: `VARCHAR(100)`
  - Constraints: not null, longitud 1..100
- **departamento_id**
  - Type: `BIGINT`
  - Constraints: foreign key a `departamentos(id)`, not null
- **username**
  - Type: `VARCHAR(60)`
  - Constraints: not null, unique, min 3 caracteres, case-insensitive en validación de unicidad funcional
- **password_hash**
  - Type: `VARCHAR(100)`
  - Constraints: not null, almacena hash bcrypt completo, nunca se expone en respuestas API

### Validation Rules
- `username` obligatorio en creación y actualización de credenciales.
- `password` en texto plano solo existe en requests de entrada y se transforma inmediatamente a hash.
- `password_hash` solo se escribe desde capa de servicio/autenticación.
- `username` duplicado debe provocar error de validación de negocio.

## Value Object: LoginRequest

### Description
Payload de autenticación para operación de login.

### Fields
- **username**: `string`, obligatorio, no vacío
- **password**: `string`, obligatorio, no vacío

## Value Object: LoginResult

### Description
Resultado del proceso de autenticación.

### Fields
- **authenticated**: `boolean`
- **empleadoClave**: `string | null`
- **message**: `string` (genérico para no filtrar existencia de usuario)

## Relationships
- `Empleado` mantiene relación existente `ManyToOne` con `Departamento`.
- `LoginRequest` y `LoginResult` no persisten en BD; representan contrato de aplicación.

## State Transitions
- **SET_CREDENTIALS**: empleado sin credenciales -> empleado con `username` y `password_hash`.
- **UPDATE_PASSWORD**: empleado con credenciales -> reemplazo de `password_hash`.
- **LOGIN_SUCCESS**: credenciales válidas -> `authenticated = true`.
- **LOGIN_FAILURE**: usuario inexistente o contraseña inválida -> `authenticated = false`.
