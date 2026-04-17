# Feature Specification: CRUD de Departamentos

**Feature Branch**: `002-crud-departamentos`  
**Created**: 2026-03-10  
**Status**: Draft  
**Input**: User description: "Genera la especificación técnica para implementar un CRUD de Departamentos relacionado con la tabla Empleados en este proyecto Java con PostgreSQL. Incluye: cambios en base de datos, nuevas clases necesarias, relación entre entidades, estructura de DAO"

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Registrar y consultar departamentos (Priority: P1)

Como usuario autenticado, quiero crear departamentos y consultar su detalle/listado para organizar a los empleados por área.

**Why this priority**: Sin alta y consulta de departamentos no existe base funcional para relacionar empleados con su área.

**Independent Test**: Puede validarse creando un departamento con datos válidos, consultándolo por identificador y verificando que aparece en el listado general.

**Acceptance Scenarios**:

1. **Given** un usuario autenticado, **When** crea un departamento con `nombre` y `descripcion` válidos, **Then** el sistema guarda el registro y devuelve su `id`.
2. **Given** departamentos existentes, **When** el usuario consulta el listado de departamentos, **Then** el sistema devuelve todos los registros con sus campos principales.
3. **Given** un departamento existente, **When** el usuario consulta por `id`, **Then** el sistema devuelve el departamento correspondiente.

---

### User Story 2 - Actualizar departamentos (Priority: P2)

Como usuario autenticado, quiero modificar nombre y descripción de un departamento para mantener la estructura organizacional actualizada.

**Why this priority**: La actualización preserva consistencia de datos operativos una vez que el departamento ya existe.

**Independent Test**: Puede validarse actualizando un departamento existente y verificando en una consulta posterior que se mantienen el `id` y la relación con empleados.

**Acceptance Scenarios**:

1. **Given** un departamento existente, **When** el usuario envía una actualización válida, **Then** el sistema persiste los nuevos datos conservando el mismo `id`.
2. **Given** un `id` inexistente, **When** el usuario intenta actualizar, **Then** el sistema responde con error de recurso no encontrado.

---

### User Story 3 - Eliminar departamentos con integridad referencial (Priority: P3)

Como usuario autenticado, quiero eliminar departamentos que ya no se usan sin romper la consistencia de los empleados existentes.

**Why this priority**: Cierra el ciclo CRUD y evita datos obsoletos, respetando reglas de relación con empleados.

**Independent Test**: Puede validarse eliminando un departamento sin empleados y verificando que desaparece; además, intentando eliminar uno con empleados y verificando rechazo.

**Acceptance Scenarios**:

1. **Given** un departamento sin empleados asociados, **When** el usuario solicita eliminación, **Then** el sistema elimina el registro.
2. **Given** un departamento con empleados asociados, **When** el usuario solicita eliminación, **Then** el sistema rechaza la operación con mensaje de integridad referencial.

### Edge Cases

- Intento de crear o actualizar departamento con `nombre` vacío.
- Intento de consultar, actualizar o eliminar un departamento con `id` inexistente.
- Intento de eliminar departamento referenciado por uno o más empleados.
- Empleado nuevo creado con `departamento_id` inexistente.
- Datos de departamento con longitudes superiores a las permitidas por el dominio.

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: El sistema MUST proveer CRUD completo de departamentos (crear, listar, consultar por `id`, actualizar y eliminar).
- **FR-002**: El sistema MUST crear y mantener una tabla `departamentos` con campos `id`, `nombre` y `descripcion`.
- **FR-003**: El sistema MUST relacionar empleados con departamentos bajo cardinalidad uno-a-muchos (un departamento puede tener muchos empleados).
- **FR-004**: El sistema MUST incluir `departamento_id` en la tabla de empleados como llave foránea válida hacia `departamentos.id`.
- **FR-005**: El sistema MUST impedir eliminación de un departamento cuando existan empleados asociados a dicho departamento.
- **FR-006**: El sistema MUST rechazar operaciones que intenten asignar empleados a departamentos inexistentes.
- **FR-007**: El sistema MUST devolver errores claros para departamentos no encontrados y violaciones de integridad referencial.
- **FR-008**: El sistema MUST conservar compatibilidad con el CRUD de empleados existente, sin romper endpoints ni contratos ya implementados.
- **FR-009**: El sistema MUST exponer DTOs de entrada y salida para operaciones de departamentos con validaciones de campos requeridos.
- **FR-010**: El sistema MUST definir estructura DAO para departamentos y adaptación del DAO de empleados para incluir navegación por departamento.
- **FR-011**: El sistema MUST incorporar nuevas clases de dominio, repositorio, servicio, controlador y excepciones necesarias para el recurso departamentos.
- **FR-012**: El sistema MUST aplicar autenticación básica a los endpoints del CRUD de departamentos con el mismo esquema de seguridad del proyecto.
- **FR-013**: El sistema MUST actualizar la documentación OpenAPI para incluir todos los endpoints y modelos de departamentos.
- **FR-014**: El sistema MUST actualizar el esquema de base de datos y su arranque en Docker para reflejar la nueva relación entre empleados y departamentos.

### Constitution Alignment *(mandatory for backend features)*

- **CA-001**: La funcionalidad MUST ejecutarse en Spring Boot 3 con Java 17.
- **CA-002**: La autenticación MUST aplicar HTTP Basic en todos los endpoints CRUD de departamentos, con la misma política que empleados.
- **CA-003**: La funcionalidad MUST reflejar impacto en PostgreSQL con definición de `departamentos` y llave foránea en `empleados`.
- **CA-004**: La funcionalidad MUST reflejar impacto Docker para levantar API + PostgreSQL con el nuevo esquema.
- **CA-005**: La funcionalidad MUST exponer actualización de Swagger/OpenAPI para operaciones y modelos de departamentos.

### Key Entities *(include if feature involves data)*

- **Departamento**: Representa una unidad organizacional con `id`, `nombre` y `descripcion`.
- **Empleado**: Mantiene su información actual y agrega referencia obligatoria `departamento_id` hacia un `Departamento` válido.
- **Relación Departamento-Empleado**: Un departamento agrupa múltiples empleados; cada empleado pertenece a un único departamento.
- **Estructura DAO esperada**: Un DAO/repositorio para `Departamento` con operaciones CRUD y consultas por identificador/nombre, y un DAO/repositorio de `Empleado` actualizado para filtrar o validar por `departamento_id`.

## Assumptions

- El identificador `id` de `departamentos` se genera automáticamente y es único.
- `nombre` de departamento es obligatorio y `descripcion` puede ser opcional según reglas de dominio existentes.
- La política de eliminación será restrictiva (no se elimina departamento con empleados asociados).
- La seguridad reutiliza el esquema HTTP Basic vigente en el proyecto.

## Dependencies

- Base de datos PostgreSQL disponible para aplicar cambios de esquema.
- Módulo de empleados existente operativo para validar no-regresión.
- Contrato OpenAPI y documentación de seguridad habilitados para actualización.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: El 100% de operaciones de alta y consulta de departamentos válidos persiste y recupera información correctamente.
- **SC-002**: El 100% de intentos de asignar empleados a departamentos inexistentes es rechazado con error comprensible.
- **SC-003**: El 100% de intentos de eliminar departamentos con empleados asociados es bloqueado sin pérdida de datos.
- **SC-004**: Al menos el 95% de operaciones CRUD de departamentos responde en menos de 2 segundos bajo carga normal.
- **SC-005**: El 100% de endpoints del CRUD de empleados existentes mantiene su comportamiento previo tras integrar departamentos.
- **SC-006**: El 100% de endpoints del CRUD de departamentos queda documentado y disponible en Swagger/OpenAPI.
