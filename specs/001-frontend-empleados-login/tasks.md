# Tasks: Frontend de Empleados con Login

**Input**: Design documents from `/specs/001-frontend-empleados-login/`  
**Prerequisites**: `plan.md` (required), `spec.md` (required), `research.md`, `data-model.md`, `contracts/openapi.yaml`, `quickstart.md`

**Tests**: No se agregan tareas de pruebas automáticas en esta fase porque la especificación no exige enfoque TDD; la validación se cubre con criterios independientes por historia y quickstart.

**Organization**: Tareas agrupadas por historia de usuario para implementación y validación independiente.

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Inicializar estructura monorepo y aplicación Angular 19 para el frontend.

- [x] T001 Crear estructura base monorepo para frontend en apps/frontend/
- [x] T002 Inicializar aplicación Angular 19 con routing en apps/frontend/package.json
- [x] T003 [P] Configurar scripts de desarrollo/build en apps/frontend/package.json
- [x] T004 [P] Configurar entorno API base URL en apps/frontend/src/environments/environment.ts
- [x] T005 [P] Crear layout base y rutas raíz en apps/frontend/src/app/app.routes.ts

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Infraestructura transversal que bloquea cualquier historia de usuario.

**⚠️ CRITICAL**: Ninguna historia puede iniciar antes de completar esta fase.

- [x] T006 Crear cliente HTTP base y manejo centralizado de errores en apps/frontend/src/app/core/http/api-client.service.ts
- [x] T007 [P] Implementar servicio de sesión/autenticación de frontend en apps/frontend/src/app/core/auth/auth-session.service.ts
- [x] T008 [P] Implementar guard de rutas autenticadas en apps/frontend/src/app/core/auth/auth.guard.ts
- [x] T009 [P] Implementar interceptor para encabezados/sesión en apps/frontend/src/app/core/http/auth.interceptor.ts
- [x] T010 Conectar guard e interceptor en configuración global de app en apps/frontend/src/app/app.config.ts
- [x] T011 [P] Alinear contrato backend para login con email/password en src/main/java/com/dsw01/practica02/dto/LoginRequest.java
- [x] T012 [P] Ajustar endpoint de login para aceptar email/password en src/main/java/com/dsw01/practica02/controller/AuthController.java
- [x] T013 [P] Ajustar OpenAPI del feature para login por email/password en specs/001-frontend-empleados-login/contracts/openapi.yaml

**Checkpoint**: Base técnica lista para historias de usuario.

---

## Phase 3: User Story 1 - Iniciar sesión con email y password (Priority: P1) 🎯 MVP

**Goal**: Permitir autenticación por email y contraseña y acceso seguro al módulo de empleados.

**Independent Test**: Desde la pantalla login, un usuario con email/password válidos entra al módulo; con credenciales inválidas permanece en login con error claro.

### Implementation for User Story 1

- [x] T014 [P] [US1] Crear página de login con formulario email/password en apps/frontend/src/app/auth/login/login.page.ts
- [x] T015 [P] [US1] Crear plantilla y estilos de login en apps/frontend/src/app/auth/login/login.page.html
- [x] T016 [US1] Implementar validaciones de formulario (email requerido y formato, password requerido) en apps/frontend/src/app/auth/login/login.form.ts
- [x] T017 [US1] Implementar servicio de autenticación contra POST /api/auth/login en apps/frontend/src/app/auth/data-access/auth-api.service.ts
- [x] T018 [US1] Integrar login exitoso con sesión y redirección a empleados en apps/frontend/src/app/auth/login/login.page.ts
- [x] T019 [US1] Mostrar mensaje de error de autenticación fallida en apps/frontend/src/app/auth/login/login.page.html
- [x] T020 [US1] Implementar cierre de sesión y limpieza de sesión en apps/frontend/src/app/core/auth/auth-session.service.ts
- [x] T021 [US1] Proteger rutas de empleados con auth guard en apps/frontend/src/app/app.routes.ts

**Checkpoint**: US1 funcional y demostrable de forma independiente.

---

## Phase 4: User Story 2 - Consultar empleados en el frontend (Priority: P2)

**Goal**: Visualizar listado y detalle de empleados para usuarios autenticados.

**Independent Test**: Con sesión activa, el usuario puede abrir listado, seleccionar empleado y ver su detalle; sin sesión, la ruta redirige a login.

### Implementation for User Story 2

- [x] T022 [P] [US2] Implementar servicio de consulta de empleados (list/detail) en apps/frontend/src/app/empleados/data-access/empleados-api.service.ts
- [x] T023 [P] [US2] Crear modelo de vista de empleado en apps/frontend/src/app/empleados/domain/empleado.model.ts
- [x] T024 [US2] Crear página de listado de empleados en apps/frontend/src/app/empleados/list/empleados-list.page.ts
- [x] T025 [US2] Implementar plantilla de listado con navegación a detalle en apps/frontend/src/app/empleados/list/empleados-list.page.html
- [x] T026 [US2] Crear página de detalle de empleado en apps/frontend/src/app/empleados/detail/empleado-detail.page.ts
- [x] T027 [US2] Implementar plantilla de detalle de empleado en apps/frontend/src/app/empleados/detail/empleado-detail.page.html
- [x] T028 [US2] Registrar rutas de listado y detalle en apps/frontend/src/app/app.routes.ts

**Checkpoint**: US2 funcional sin depender de creación/edición/borrado.

---

## Phase 5: User Story 3 - Gestionar CRUD de empleados (Priority: P3)

**Goal**: Crear, actualizar y eliminar empleados desde el frontend.

**Independent Test**: Con sesión activa, el usuario puede crear, editar y eliminar empleados, observando cada resultado en listado/detalle.

### Implementation for User Story 3

- [x] T029 [P] [US3] Extender API de empleados con create/update/delete en apps/frontend/src/app/empleados/data-access/empleados-api.service.ts
- [x] T030 [P] [US3] Crear formulario reusable de empleado en apps/frontend/src/app/empleados/ui/empleado-form.component.ts
- [x] T031 [US3] Implementar página de alta de empleado en apps/frontend/src/app/empleados/create/empleado-create.page.ts
- [x] T032 [US3] Implementar página de edición de empleado en apps/frontend/src/app/empleados/edit/empleado-edit.page.ts
- [x] T033 [US3] Implementar acción de eliminación con confirmación en apps/frontend/src/app/empleados/detail/empleado-detail.page.ts
- [x] T034 [US3] Mostrar mensajes de éxito/error para operaciones CRUD en apps/frontend/src/app/empleados/ui/empleado-feedback.service.ts
- [x] T035 [US3] Registrar rutas de create/edit en apps/frontend/src/app/app.routes.ts

**Checkpoint**: US3 funcional e independiente para validación completa de CRUD.

---

## Phase 6: Polish & Cross-Cutting Concerns

**Purpose**: Cierre de documentación y validación integral del feature.

- [x] T036 [P] Actualizar guía de ejecución del frontend en monorepo en specs/001-frontend-empleados-login/quickstart.md
- [x] T037 [P] Documentar impacto de seguridad (login email/password + endpoints protegidos) en docs/api/security.md
- [x] T038 Verificar consistencia final entre implementación y contrato en specs/001-frontend-empleados-login/contracts/openapi.yaml
- [ ] T039 Verificar ejecución integrada backend+frontend con Docker/local en docker/docker-compose.yml

---

## Dependencies & Execution Order

### Phase Dependencies

- **Phase 1 (Setup)**: sin dependencias.
- **Phase 2 (Foundational)**: depende de Setup y bloquea todas las historias.
- **Phase 3-5 (User Stories)**: dependen de Foundational; se ejecutan en prioridad P1 → P2 → P3 para entrega incremental.
- **Phase 6 (Polish)**: depende de completar historias objetivo.

### User Story Dependencies

- **US1 (P1)**: inicia tras Phase 2 y entrega MVP (login email/password).
- **US2 (P2)**: inicia tras Phase 2, pero recomienda apoyarse en sesión de US1.
- **US3 (P3)**: inicia tras Phase 2 y requiere navegación/autorización establecida por US1.

Dependency graph:
- US1 → US2
- US1 → US3

### Within Each User Story

- Definir servicios/modelos antes de páginas.
- Páginas antes de rutas finales.
- Integración de errores/mensajes al final de cada historia.

### Parallel Opportunities

- Setup: T003, T004 y T005 en paralelo tras T001/T002.
- Foundational: T007, T008, T009, T011, T012 y T013 en paralelo tras T006/T010.
- US1: T014 y T015 en paralelo; T017 y T019 en paralelo tras formulario.
- US2: T022 y T023 en paralelo; T024/T026 pueden avanzar por separado.
- US3: T029 y T030 en paralelo; T031 y T032 en paralelo.
- Polish: T036 y T037 en paralelo.

---

## Parallel Example: User Story 1

```bash
Task: "T014 [US1] Crear página de login en apps/frontend/src/app/auth/login/login.page.ts"
Task: "T015 [US1] Crear plantilla y estilos de login en apps/frontend/src/app/auth/login/login.page.html"
Task: "T017 [US1] Implementar servicio auth-api en apps/frontend/src/app/auth/data-access/auth-api.service.ts"
```

## Parallel Example: User Story 2

```bash
Task: "T022 [US2] Implementar servicio list/detail en apps/frontend/src/app/empleados/data-access/empleados-api.service.ts"
Task: "T023 [US2] Crear modelo de empleado en apps/frontend/src/app/empleados/domain/empleado.model.ts"
Task: "T026 [US2] Crear página detalle en apps/frontend/src/app/empleados/detail/empleado-detail.page.ts"
```

## Parallel Example: User Story 3

```bash
Task: "T029 [US3] Extender API con create/update/delete en apps/frontend/src/app/empleados/data-access/empleados-api.service.ts"
Task: "T030 [US3] Crear componente de formulario en apps/frontend/src/app/empleados/ui/empleado-form.component.ts"
Task: "T031 [US3] Implementar página create en apps/frontend/src/app/empleados/create/empleado-create.page.ts"
```

---

## Implementation Strategy

### MVP First (User Story 1 Only)

1. Completar Phase 1 y Phase 2.
2. Completar Phase 3 (US1).
3. Validar login con email/password y protección de rutas.
4. Demo/entrega inicial.

### Incremental Delivery

1. Entregar US1 (MVP autenticación).
2. Añadir US2 (consulta de empleados).
3. Añadir US3 (CRUD completo).
4. Cerrar con polish transversal.

### Parallel Team Strategy

1. Desarrollo conjunto en setup/foundational.
2. Reparto por historia: Dev A (US1), Dev B (US2), Dev C (US3).
3. Integración y validación por checkpoints de historia.

---

## Notes

- Todas las tareas siguen formato checklist estricto: `- [ ] T### [P?] [US?] Descripción con ruta`.
- Se prioriza login con **email y contraseña** según requerimiento explícito del feature.
- Cada historia puede validarse de forma independiente con su criterio de prueba.
