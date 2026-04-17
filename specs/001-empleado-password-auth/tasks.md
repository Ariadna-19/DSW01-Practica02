# Tasks: Autenticación de empleados

**Input**: Design documents from `/specs/001-empleado-password-auth/`  
**Prerequisites**: plan.md (required), spec.md (required for user stories), research.md, data-model.md, contracts/

**Tests**: Se incluyen tareas de prueba porque el alcance solicita explícitamente una prueba de autenticación y ejemplo de uso.

**Organization**: Tasks are grouped by user story to enable independent implementation and testing of each story.

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Preparar dependencias y estructura mínima para implementar autenticación sin romper el CRUD actual.

- [ ] T001 Verificar y ajustar dependencia JDBC en `pom.xml` para uso de `JdbcTemplate`
- [ ] T002 [P] Crear DTOs base de autenticación en `src/main/java/com/dsw01/practica02/dto/LoginRequest.java` y `src/main/java/com/dsw01/practica02/dto/LoginResponse.java`
- [ ] T003 [P] Crear excepción de autenticación en `src/main/java/com/dsw01/practica02/exception/InvalidCredentialsException.java`

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Infraestructura compartida obligatoria antes de implementar historias.

**⚠️ CRITICAL**: Ninguna historia debe empezar antes de completar esta fase.

- [ ] T004 Implementar script SQL para modificar `empleados` agregando `username` y `password_hash` en `src/main/resources/schema.sql`
- [ ] T005 [P] Configurar restricción de unicidad para `username` en `src/main/resources/schema.sql`
- [ ] T006 [P] Ajustar seguridad para permitir `POST /api/auth/login` y mantener Basic Auth en CRUD en `src/main/java/com/dsw01/practica02/config/SecurityConfig.java`
- [ ] T007 Definir repositorio JDBC de credenciales en `src/main/java/com/dsw01/practica02/repository/AuthJdbcRepository.java`

**Checkpoint**: Base de datos, seguridad y acceso JDBC listos para historias de usuario.

---

## Phase 3: User Story 1 - Login con credenciales (Priority: P1) 🎯 MVP

**Goal**: Permitir que un empleado se autentique con `username` y `password`.

**Independent Test**: Ejecutar login con usuario/contraseña correctos e incorrectos y validar respuesta genérica de fallo.

### Tests for User Story 1

- [ ] T008 [P] [US1] Crear prueba de integración de login en `src/test/java/com/dsw01/practica02/AuthLoginIntegrationTest.java`
- [ ] T009 [P] [US1] Crear prueba de credenciales inválidas en `src/test/java/com/dsw01/practica02/AuthLoginInvalidCredentialsIntegrationTest.java`

### Implementation for User Story 1

- [ ] T010 [US1] Crear servicio de autenticación en `src/main/java/com/dsw01/practica02/service/AuthService.java`
- [ ] T011 [US1] Implementar método `login(username, password)` en `src/main/java/com/dsw01/practica02/service/AuthService.java`
- [ ] T012 [US1] Implementar validación de contraseña hash con `PasswordEncoder.matches` en `src/main/java/com/dsw01/practica02/service/AuthService.java`
- [ ] T013 [US1] Exponer endpoint `POST /api/auth/login` en `src/main/java/com/dsw01/practica02/controller/AuthController.java`
- [ ] T014 [US1] Registrar manejo de errores de autenticación en `src/main/java/com/dsw01/practica02/config/GlobalExceptionHandler.java`

**Checkpoint**: Login funcional y verificable de forma independiente.

---

## Phase 4: User Story 2 - Alta de credenciales por empleado (Priority: P2)

**Goal**: Guardar y mantener credenciales por empleado dentro del flujo CRUD existente.

**Independent Test**: Crear y actualizar empleado validando persistencia de `username` y `password_hash` sin exponer hash.

### Implementation for User Story 2

- [ ] T015 [US2] Actualizar entidad `Empleado` con `username` y `passwordHash` en `src/main/java/com/dsw01/practica02/domain/Empleado.java`
- [ ] T016 [P] [US2] Actualizar request de creación con credenciales en `src/main/java/com/dsw01/practica02/dto/EmpleadoCreateRequest.java`
- [ ] T017 [P] [US2] Actualizar request de actualización con credenciales opcionales en `src/main/java/com/dsw01/practica02/dto/EmpleadoUpdateRequest.java`
- [ ] T018 [P] [US2] Actualizar response de empleado para incluir `username` y excluir hash en `src/main/java/com/dsw01/practica02/dto/EmpleadoResponse.java`
- [ ] T019 [US2] Integrar hash de contraseña al crear empleado en `src/main/java/com/dsw01/practica02/service/EmpleadoService.java`
- [ ] T020 [US2] Integrar actualización de hash al actualizar empleado en `src/main/java/com/dsw01/practica02/service/EmpleadoService.java`
- [ ] T021 [US2] Agregar consulta JPA por username en `src/main/java/com/dsw01/practica02/repository/EmpleadoRepository.java`

**Checkpoint**: CRUD de empleados compatible con credenciales persistidas.

---

## Phase 5: User Story 3 - Validación robusta de autenticación (Priority: P3)

**Goal**: Endurecer validaciones y reglas de seguridad de entrada sin afectar compatibilidad.

**Independent Test**: Intentar login con valores vacíos y crear empleados con `username` duplicado.

### Tests for User Story 3

- [ ] T022 [P] [US3] Crear prueba de validación de campos vacíos en login en `src/test/java/com/dsw01/practica02/AuthLoginValidationIntegrationTest.java`
- [ ] T023 [P] [US3] Crear prueba de duplicado de username en `src/test/java/com/dsw01/practica02/EmpleadoUsernameUniqueIntegrationTest.java`

### Implementation for User Story 3

- [ ] T024 [US3] Aplicar validaciones Bean Validation en `src/main/java/com/dsw01/practica02/dto/LoginRequest.java`
- [ ] T025 [US3] Implementar error de negocio por username duplicado en `src/main/java/com/dsw01/practica02/service/EmpleadoService.java`
- [ ] T026 [US3] Homogeneizar mensaje genérico de fallo de login en `src/main/java/com/dsw01/practica02/service/AuthService.java`

**Checkpoint**: Validaciones y mensajes de error cumplen reglas de seguridad definidas.

---

## Phase 6: Polish & Cross-Cutting Concerns

**Purpose**: Cierre, documentación y verificación integral.

- [ ] T027 [P] Actualizar contrato OpenAPI en `specs/001-empleado-password-auth/contracts/openapi.yaml`
- [ ] T028 [P] Sincronizar documentación de seguridad en `docs/api/security.md`
- [ ] T029 [P] Añadir ejemplo de uso de login en `specs/001-empleado-password-auth/quickstart.md`
- [ ] T030 Ejecutar validación de pruebas de autenticación con `mvn test` y documentar resultado en `specs/001-empleado-password-auth/quickstart.md`

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: inicia inmediatamente.
- **Foundational (Phase 2)**: depende de Setup y bloquea todas las historias.
- **User Stories (Phase 3+)**: dependen de Foundational.
- **Polish (Phase 6)**: depende de completar historias requeridas.

### User Story Dependencies

- **US1 (P1)**: inicia tras Foundational, sin dependencia funcional de otras historias.
- **US2 (P2)**: inicia tras Foundational; integra clases de dominio y servicio existentes.
- **US3 (P3)**: inicia tras Foundational; refuerza validaciones sobre US1/US2.

### Within Each User Story

- Pruebas (cuando existan) antes o en paralelo con implementación inicial.
- DTOs/modelo antes de servicios.
- Servicios antes de controladores.
- Endpoint y manejo de error al final de cada historia.

---

## Parallel Opportunities

- **Setup**: T002 y T003 en paralelo.
- **Foundational**: T005 y T006 en paralelo tras T004.
- **US1**: T008 y T009 en paralelo; T010→T011→T012 en secuencia, T013/T014 después.
- **US2**: T016, T017 y T018 en paralelo; T019 y T020 después de T015.
- **US3**: T022 y T023 en paralelo; T024/T025/T026 según disponibilidad.
- **Polish**: T027, T028 y T029 en paralelo.

---

## Parallel Example: User Story 1

```bash
# Tests de US1 en paralelo
Task: "T008 [US1] Crear prueba de integración de login en src/test/java/com/dsw01/practica02/AuthLoginIntegrationTest.java"
Task: "T009 [US1] Crear prueba de credenciales inválidas en src/test/java/com/dsw01/practica02/AuthLoginInvalidCredentialsIntegrationTest.java"

# Implementaciones desacopladas tras servicio base
Task: "T013 [US1] Exponer endpoint POST /api/auth/login en src/main/java/com/dsw01/practica02/controller/AuthController.java"
Task: "T014 [US1] Registrar manejo de errores en src/main/java/com/dsw01/practica02/config/GlobalExceptionHandler.java"
```

---

## Implementation Strategy

### MVP First (US1)

1. Completar Phase 1 y Phase 2.
2. Completar US1 (T008-T014).
3. Validar login exitoso/fallido de forma independiente.

### Incremental Delivery

1. Entregar MVP con US1.
2. Añadir US2 para persistencia de credenciales en CRUD.
3. Añadir US3 para validaciones robustas.
4. Cerrar con Phase 6 (docs + pruebas + quickstart).
