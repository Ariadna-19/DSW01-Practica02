# Feature Specification: Autenticación de empleados

**Feature Branch**: `[001-empleado-auth]`  
**Created**: 2026-03-10  
**Status**: Draft  
**Input**: User description: "Agregar autenticación de usuario y contraseña para cada empleado en este proyecto Java con Maven y PostgreSQL."

## User Scenarios & Testing *(mandatory)*

<!--
  IMPORTANT: User stories should be PRIORITIZED as user journeys ordered by importance.
  Each user story/journey must be INDEPENDENTLY TESTABLE - meaning if you implement just ONE of them,
  you should still have a viable MVP (Minimum Viable Product) that delivers value.
  
  Assign priorities (P1, P2, P3, etc.) to each story, where P1 is the most critical.
  Think of each story as a standalone slice of functionality that can be:
  - Developed independently
  - Tested independently
  - Deployed independently
  - Demonstrated to users independently
-->

### User Story 1 - Login con credenciales (Priority: P1)

Como empleado, quiero iniciar sesión con mi usuario y contraseña para acceder de forma segura al sistema.

**Why this priority**: Sin login no existe control de acceso individual por empleado, por lo que el sistema no cumple el objetivo principal de seguridad.

**Independent Test**: Puede validarse creando un empleado con credenciales válidas y ejecutando la operación de login con combinaciones correctas e incorrectas.

**Acceptance Scenarios**:

1. **Given** un empleado activo con usuario y contraseña válidos, **When** envía sus credenciales en la operación de login, **Then** el sistema confirma autenticación exitosa.
2. **Given** un usuario existente y una contraseña incorrecta, **When** envía sus credenciales en la operación de login, **Then** el sistema rechaza la autenticación con mensaje de error genérico.
3. **Given** un usuario inexistente, **When** envía una solicitud de login, **Then** el sistema rechaza la autenticación sin revelar si el usuario existe.

---

### User Story 2 - Alta de credenciales por empleado (Priority: P2)

Como administrador del sistema, quiero que cada empleado tenga credenciales almacenadas para permitir autenticación individual.

**Why this priority**: Asegura que los empleados existentes y nuevos tengan identidad autenticable persistida en base de datos.

**Independent Test**: Puede probarse creando o actualizando empleados y verificando que los campos de usuario y hash de contraseña se persisten correctamente.

**Acceptance Scenarios**:

1. **Given** un empleado nuevo, **When** se registra con usuario y contraseña, **Then** el sistema guarda el usuario y el hash de contraseña en la base de datos.
2. **Given** un empleado existente, **When** se actualiza su contraseña, **Then** el sistema reemplaza el hash anterior por uno nuevo.

---

### User Story 3 - Validación robusta de autenticación (Priority: P3)

Como responsable de seguridad, quiero reglas consistentes de validación de credenciales para reducir accesos indebidos y errores de operación.

**Why this priority**: Refuerza la seguridad y la experiencia operativa, aunque depende de que ya exista login y persistencia de credenciales.

**Independent Test**: Puede validarse con pruebas de límites de entrada, usuarios duplicados y credenciales vacías.

**Acceptance Scenarios**:

1. **Given** un intento de login con campos vacíos, **When** se procesa la solicitud, **Then** el sistema devuelve error de validación sin intentar autenticar.
2. **Given** dos empleados con el mismo usuario, **When** se intenta guardar el segundo registro, **Then** el sistema rechaza la operación por violar unicidad.

---

### Edge Cases

- Login con `username` válido pero `password` vacío o nulo.
- Login con mayúsculas/minúsculas diferentes en `username` respecto al valor almacenado.
- Creación o actualización de empleado con `username` ya existente.
- Empleados históricos sin credenciales previas después de aplicar el cambio de esquema.
- Intentos repetidos de login fallido en corto intervalo.

## Requirements *(mandatory)*

<!--
  ACTION REQUIRED: The content in this section represents placeholders.
  Fill them out with the right functional requirements.
-->

### Functional Requirements

- **FR-001**: El sistema DEBE permitir autenticación de cada empleado mediante `username` y `password`.
- **FR-002**: El sistema DEBE incorporar y persistir en la entidad de empleados los campos `username` y `password_hash`.
- **FR-003**: El sistema DEBE almacenar la contraseña únicamente en formato hash y nunca en texto plano.
- **FR-004**: El sistema DEBE exponer una operación de login que valide credenciales y retorne resultado de autenticación exitosa o fallida.
- **FR-005**: El sistema DEBE validar credenciales comparando la contraseña recibida con el hash almacenado del empleado.
- **FR-006**: El sistema DEBE rechazar autenticación cuando el usuario no exista o la contraseña no coincida, con una respuesta genérica que no revele cuál dato falló.
- **FR-007**: El sistema DEBE exigir unicidad de `username` por empleado.
- **FR-008**: El sistema DEBE aplicar validación de entrada para impedir usuarios o contraseñas vacías en creación, actualización y login.
- **FR-009**: El sistema DEBE integrarse con la arquitectura actual manteniendo los patrones de capas existentes (controlador, servicio, repositorio).
- **FR-010**: El sistema DEBE implementar la persistencia y validación de credenciales mediante JDBC.

### Constitution Alignment *(mandatory for backend features)*

- **CA-001**: La funcionalidad DEBE ejecutarse en Spring Boot 3 con Java 17, sin introducir dependencias incompatibles.
- **CA-002**: La funcionalidad DEBE mantener el esquema de autenticación HTTP Basic actual y definir explícitamente si el endpoint de login queda público y el resto de endpoints protegidos.
- **CA-003**: La funcionalidad DEBE modificar PostgreSQL agregando `username` y `password_hash` a la tabla de empleados, incluyendo restricciones de no nulidad y unicidad para `username`.
- **CA-004**: La funcionalidad NO requiere cambios en Dockerfile ni `docker-compose`, salvo ajustes de inicialización de esquema si fueran necesarios para levantar la base de datos con los nuevos campos.
- **CA-005**: La funcionalidad DEBE actualizar Swagger/OpenAPI para documentar la operación de login y los cambios en los contratos de entrada/salida de empleados que incluyan credenciales.

### Key Entities *(include if feature involves data)*

- **Empleado**: Representa al usuario interno del sistema; incluye identificador de empleado, datos laborales existentes, `username` único y `password_hash` para autenticación.
- **Solicitud de Login**: Representa los datos enviados para autenticación (`username` y `password`) y se usa para validar acceso.
- **Resultado de Autenticación**: Representa el resultado de la validación de credenciales (éxito o fallo) y el empleado autenticado cuando corresponda.

### Assumptions

- El alcance de este feature cubre autenticación por usuario/contraseña, sin incluir recuperación de contraseña ni bloqueo avanzado de cuentas.
- Los empleados existentes deberán completar o recibir credenciales en una etapa operativa de migración de datos.
- Los mensajes de error de login serán deliberadamente genéricos por seguridad.
- La retención de datos de credenciales seguirá las políticas existentes de datos de empleados del proyecto.

## Success Criteria *(mandatory)*

<!--
  ACTION REQUIRED: Define measurable success criteria.
  These must be technology-agnostic and measurable.
-->

### Measurable Outcomes

- **SC-001**: El 100% de los empleados con credenciales válidas puede autenticarse correctamente en su primer intento.
- **SC-002**: El 100% de intentos con contraseña incorrecta o usuario inexistente es rechazado sin acceso al sistema.
- **SC-003**: Al menos el 95% de las operaciones de login válidas finaliza en menos de 2 segundos en condiciones normales de operación.
- **SC-004**: El 100% de los registros de empleados creados o actualizados después del despliegue incluyen `username` único y `password_hash` válido.
