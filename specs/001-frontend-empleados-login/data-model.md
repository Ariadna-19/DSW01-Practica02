# Data Model - Frontend de Empleados con Login

## Entity: CredencialesLogin

### Description
Datos capturados en la pantalla de acceso para autenticar al usuario.

### Fields
- **email**
  - Type: `string`
  - Constraints: obligatorio, formato email válido
- **password**
  - Type: `string`
  - Constraints: obligatorio, no vacío

### Validation Rules
- `email` debe cumplir formato de correo válido.
- `password` no puede estar vacío.

## Entity: SesionUsuario

### Description
Estado de autenticación del usuario dentro de la aplicación frontend.

### Fields
- **authenticated**
  - Type: `boolean`
  - Constraints: obligatorio
- **userEmail**
  - Type: `string | null`
  - Constraints: presente cuando `authenticated = true`
- **issuedAt**
  - Type: `datetime | null`
  - Constraints: opcional, para trazabilidad de sesión

### State Transitions
- **UNAUTHENTICATED -> AUTHENTICATED**: login exitoso con `email` y `password` válidos.
- **AUTHENTICATED -> UNAUTHENTICATED**: cierre de sesión o expiración de sesión.

## Entity: EmpleadoView

### Description
Representación de empleado para vistas de listado, detalle y formularios CRUD.

### Fields
- **clave**
  - Type: `string`
  - Constraints: obligatorio, identificador único del empleado
- **nombre**
  - Type: `string`
  - Constraints: obligatorio
- **direccion**
  - Type: `string`
  - Constraints: obligatorio
- **telefono**
  - Type: `string`
  - Constraints: obligatorio
- **departamentoId**
  - Type: `number | null`
  - Constraints: opcional según contrato backend vigente
- **username**
  - Type: `string | null`
  - Constraints: opcional según contrato backend vigente

### Validation Rules
- En create/update los campos obligatorios deben estar completos antes de enviar.
- Los errores de validación deben mapearse a mensajes de formulario comprensibles.

## Relationships
- `SesionUsuario` controla acceso a pantallas que operan sobre `EmpleadoView`.
- `CredencialesLogin` es entrada para crear/actualizar `SesionUsuario`.
