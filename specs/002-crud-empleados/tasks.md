# Tasks: CRUD de Empleados

**Input**: Design documents from `/specs/002-crud-empleados/`  
**Prerequisites**: `plan.md` (required), `spec.md` (required), `research.md`, `data-model.md`, `contracts/openapi.yaml`, `quickstart.md`

**Tests**: No se incluyen tareas de pruebas automáticas porque la especificación no exige TDD ni tests obligatorios en esta fase.

**Organization**: Tareas agrupadas por historia de usuario para implementación y validación independiente.

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Ajustes base de configuración y estructura del proyecto.

- [ ] T001 Validar dependencias Spring Boot 3, JPA, Security, PostgreSQL y OpenAPI en pom.xml
- [ ] T002 Alinear estructura de paquetes backend en src/main/java/com/dsw01/practica02/
- [ ] T003 [P] Validar propiedades base de aplicación para entorno local en src/main/resources/application.properties

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Infraestructura bloqueante antes de cualquier historia.

**⚠️ CRITICAL**: Ninguna historia puede iniciarse antes de completar esta fase.

- [ ] T004 Definir/ajustar esquema SQL base y secuencias en src/main/resources/schema.sql
- [ ] T005 [P] Alinear entidad Empleado con restricciones de dominio en src/main/java/com/dsw01/practica02/domain/Empleado.java
- [ ] T006 [P] Alinear repositorio de empleados para paginación y búsqueda por clave en src/main/java/com/dsw01/practica02/repository/EmpleadoRepository.java
- [ ] T007 [P] Implementar/ajustar generador de clave E-0001 en src/main/java/com/dsw01/practica02/service/ClaveEmpleadoGenerator.java
- [ ] T008 [P] Asegurar seguridad HTTP Basic sobre endpoints CRUD en src/main/java/com/dsw01/practica02/config/SecurityConfig.java
- [ ] T009 [P] Asegurar configuración OpenAPI con esquema basicAuth en src/main/java/com/dsw01/practica02/config/OpenApiConfig.java
- [ ] T010 [P] Alinear runtime de PostgreSQL para desarrollo en docker/docker-compose.yml
- [ ] T011 Consolidar manejo de errores de dominio en src/main/java/com/dsw01/practica02/config/GlobalExceptionHandler.java

**Checkpoint**: Base técnica lista para construir historias de usuario.

---

## Phase 3: User Story 1 - Registrar y consultar empleados (Priority: P1) 🎯 MVP

**Goal**: Permitir alta y consulta (detalle + listado paginado de 5) de empleados autenticados.

**Independent Test**: Crear un empleado válido, consultarlo por clave y listarlo en página 0 verificando tamaño máximo 5 y formato de clave.

### Implementation for User Story 1

- [ ] T012 [P] [US1] Alinear DTO de creación con validaciones de campos en src/main/java/com/dsw01/practica02/dto/EmpleadoCreateRequest.java
- [ ] T013 [P] [US1] Alinear DTO de respuesta de empleado en src/main/java/com/dsw01/practica02/dto/EmpleadoResponse.java
- [ ] T014 [P] [US1] Alinear DTO de respuesta paginada en src/main/java/com/dsw01/practica02/dto/EmpleadoPageResponse.java
- [ ] T015 [US1] Implementar caso de uso de creación en src/main/java/com/dsw01/practica02/service/EmpleadoService.java
- [ ] T016 [US1] Implementar caso de uso de consulta por clave en src/main/java/com/dsw01/practica02/service/EmpleadoService.java
- [ ] T017 [US1] Implementar caso de uso de listado paginado (size=5) en src/main/java/com/dsw01/practica02/service/EmpleadoService.java
- [ ] T018 [US1] Exponer endpoint POST /api/empleados en src/main/java/com/dsw01/practica02/controller/EmpleadoController.java
- [ ] T019 [US1] Exponer endpoint GET /api/empleados/{clave} en src/main/java/com/dsw01/practica02/controller/EmpleadoController.java
- [ ] T020 [US1] Exponer endpoint GET /api/empleados?page=0 en src/main/java/com/dsw01/practica02/controller/EmpleadoController.java
- [ ] T021 [US1] Sincronizar contrato OpenAPI para create y read en specs/002-crud-empleados/contracts/openapi.yaml

**Checkpoint**: US1 funcional y validable de forma independiente.

---

## Phase 4: User Story 2 - Actualizar datos de empleados (Priority: P2)

**Goal**: Permitir actualización de datos conservando la clave del empleado.

**Independent Test**: Actualizar un empleado existente y verificar que conserva clave y refleja cambios; intentar actualizar clave inexistente y obtener 404.

### Implementation for User Story 2

- [ ] T022 [P] [US2] Alinear DTO de actualización con validaciones en src/main/java/com/dsw01/practica02/dto/EmpleadoUpdateRequest.java
- [ ] T023 [US2] Implementar caso de uso de actualización por clave en src/main/java/com/dsw01/practica02/service/EmpleadoService.java
- [ ] T024 [US2] Exponer endpoint PUT /api/empleados/{clave} en src/main/java/com/dsw01/practica02/controller/EmpleadoController.java
- [ ] T025 [US2] Sincronizar contrato OpenAPI para update en specs/002-crud-empleados/contracts/openapi.yaml

**Checkpoint**: US2 funcional sin depender de US3.

---

## Phase 5: User Story 3 - Eliminar empleados (Priority: P3)

**Goal**: Permitir eliminación de empleados por clave con respuesta 204.

**Independent Test**: Eliminar un empleado existente y confirmar que no aparece en consultas posteriores.

### Implementation for User Story 3

- [ ] T026 [US3] Implementar caso de uso de eliminación por clave en src/main/java/com/dsw01/practica02/service/EmpleadoService.java
- [ ] T027 [US3] Exponer endpoint DELETE /api/empleados/{clave} en src/main/java/com/dsw01/practica02/controller/EmpleadoController.java
- [ ] T028 [US3] Sincronizar contrato OpenAPI para delete en specs/002-crud-empleados/contracts/openapi.yaml

**Checkpoint**: US3 funcional e independiente para validación.

---

## Phase 6: Polish & Cross-Cutting Concerns

**Purpose**: Cierre transversal de documentación, seguridad y ejecución reproducible.

- [ ] T029 [P] Actualizar guía de ejecución y validación manual en specs/002-crud-empleados/quickstart.md
- [ ] T030 [P] Actualizar guía de seguridad y credenciales de desarrollo en docs/api/security.md
- [ ] T031 Verificar consistencia final del contrato con endpoints implementados en specs/002-crud-empleados/contracts/openapi.yaml
- [ ] T032 Verificar configuración final de entorno local en src/main/resources/application.properties

---

## Dependencies & Execution Order

### Phase Dependencies

- **Phase 1 (Setup)**: sin dependencias.
- **Phase 2 (Foundational)**: depende de Phase 1 y bloquea todas las historias.
- **Phase 3-5 (User Stories)**: dependen de Phase 2; se pueden ejecutar en paralelo por capacidad del equipo.
- **Phase 6 (Polish)**: depende de historias completadas.

### User Story Dependencies

- **US1 (P1)**: inicia tras Phase 2 y entrega el MVP.
- **US2 (P2)**: inicia tras Phase 2; depende funcionalmente de tener empleados existentes (flujo de US1).
- **US3 (P3)**: inicia tras Phase 2; depende funcionalmente de tener empleados existentes (flujo de US1).

Dependency graph:
- US1 → US2
- US1 → US3

### Within Each User Story

- DTOs antes de servicio.
- Servicio antes de controlador.
- Controlador antes de sincronización final de contrato.

### Parallel Opportunities

- **Foundational**: T005, T006, T007, T008, T009, T010 pueden ejecutarse en paralelo tras T004.
- **US1**: T012, T013, T014 son paralelizables.
- **US2**: T022 y T025 pueden distribuirse en paralelo.
- **Polish**: T029 y T030 son paralelizables.

---

## Parallel Example: User Story 1

```bash
Task: "T012 [US1] Alinear DTO de creación en src/main/java/com/dsw01/practica02/dto/EmpleadoCreateRequest.java"
Task: "T013 [US1] Alinear DTO de respuesta en src/main/java/com/dsw01/practica02/dto/EmpleadoResponse.java"
Task: "T014 [US1] Alinear DTO de paginación en src/main/java/com/dsw01/practica02/dto/EmpleadoPageResponse.java"
```

## Parallel Example: User Story 2

```bash
Task: "T022 [US2] Alinear DTO de actualización en src/main/java/com/dsw01/practica02/dto/EmpleadoUpdateRequest.java"
Task: "T025 [US2] Sincronizar OpenAPI update en specs/002-crud-empleados/contracts/openapi.yaml"
```

## Parallel Example: User Story 3

```bash
Task: "T026 [US3] Implementar eliminación en src/main/java/com/dsw01/practica02/service/EmpleadoService.java"
Task: "T028 [US3] Sincronizar OpenAPI delete en specs/002-crud-empleados/contracts/openapi.yaml"
```

---

## Implementation Strategy

### MVP First (User Story 1 Only)

1. Completar Phase 1.
2. Completar Phase 2 (bloqueante).
3. Completar Phase 3 (US1).
4. Validar US1 de forma independiente.

### Incremental Delivery

1. Setup + Foundational.
2. Entregar US1 (MVP).
3. Entregar US2.
4. Entregar US3.
5. Ejecutar Polish transversal.

### Parallel Team Strategy

1. Equipo completo en Setup + Foundational.
2. Luego repartir historias por desarrollador (US1/US2/US3).
3. Integrar y validar por checkpoints de cada historia.

---

## Notes

- Todas las tareas siguen formato checklist estricto: `- [ ] T### [P?] [US?] Descripción con ruta de archivo`.
- No se incluyeron tareas de tests automáticos al no estar explícitamente solicitadas en la especificación.
- Cada historia queda definida para ser implementada y verificada de forma independiente.
