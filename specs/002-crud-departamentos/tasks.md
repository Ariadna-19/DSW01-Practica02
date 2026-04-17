# Tasks: CRUD de Departamentos

**Input**: Design documents from `/specs/002-crud-departamentos/`  
**Prerequisites**: `plan.md`, `spec.md`, `research.md`, `data-model.md`, `contracts/openapi.yaml`, `quickstart.md`

**Tests**: No se agregan tareas de tests nuevas en esta iteración; se valida con compilación y ejecución manual de quickstart.

**Organization**: Tareas agrupadas por historia de usuario para entrega incremental y validación independiente.

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Preparar base para el CRUD de departamentos.

- [x] T001 Verificar configuración de dependencias Spring/JPA/PostgreSQL/OpenAPI en pom.xml
- [x] T002 Verificar configuración de seguridad HTTP Basic en src/main/java/com/dsw01/practica02/config/SecurityConfig.java

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Cambios base que bloquean historias.

- [x] T003 Implementar script SQL de departamentos y FK en empleados en src/main/resources/schema.sql
- [x] T004 [P] Ajustar entidad Departamento (`id`, `nombre`, `descripcion`) en src/main/java/com/dsw01/practica02/domain/Departamento.java
- [x] T005 [P] Crear/ajustar DAO de departamentos en src/main/java/com/dsw01/practica02/repository/DepartamentoRepository.java
- [x] T006 Ajustar entidad Empleado para `departamentoId` (FK) en src/main/java/com/dsw01/practica02/domain/Empleado.java

**Checkpoint**: Esquema y dominio listos para historias.

---

## Phase 3: User Story 1 - Registrar y consultar departamentos (Priority: P1) 🎯 MVP

**Goal**: Alta y consulta de departamentos.

- [x] T007 [US1] Ajustar DTOs de departamento (create/update/response/page) en src/main/java/com/dsw01/practica02/dto/
- [x] T008 [US1] Implementar lógica de creación/consulta/listado en src/main/java/com/dsw01/practica02/service/DepartamentoService.java
- [x] T009 [US1] Implementar endpoints POST/GET de departamentos en src/main/java/com/dsw01/practica02/controller/DepartamentoController.java

---

## Phase 4: User Story 2 - Actualizar departamentos (Priority: P2)

**Goal**: Actualización de departamentos.

- [x] T010 [US2] Implementar actualización en src/main/java/com/dsw01/practica02/service/DepartamentoService.java
- [x] T011 [US2] Implementar endpoint PUT de departamentos en src/main/java/com/dsw01/practica02/controller/DepartamentoController.java

---

## Phase 5: User Story 3 - Eliminar departamentos con integridad (Priority: P3)

**Goal**: Eliminación con restricción por empleados asociados.

- [x] T012 [US3] Implementar eliminación con manejo de conflicto en src/main/java/com/dsw01/practica02/service/DepartamentoService.java
- [x] T013 [US3] Implementar endpoint DELETE de departamentos en src/main/java/com/dsw01/practica02/controller/DepartamentoController.java

---

## Phase 6: Integración con Empleado y ejemplo

**Purpose**: Completar relación y ejemplo simple de uso.

- [x] T014 Ajustar DTOs y servicio de empleado para recibir/exponer `departamentoId` en src/main/java/com/dsw01/practica02/dto/ y src/main/java/com/dsw01/practica02/service/EmpleadoService.java
- [x] T015 Ajustar controlador de empleados según nuevo contrato en src/main/java/com/dsw01/practica02/controller/EmpleadoController.java
- [x] T016 Agregar ejemplo simple en main de la aplicación en src/main/java/com/dsw01/practica02/Practica02Application.java
- [x] T017 Actualizar contrato OpenAPI de departamentos y empleados en specs/002-crud-departamentos/contracts/openapi.yaml
- [x] T018 Validar compilación Maven sin romper código existente

---

## Dependencies & Execution Order

- Setup -> Foundational -> US1 -> US2 -> US3 -> Integración.
- T004 y T005 se pueden hacer en paralelo tras T003.
- T008 depende de T004/T005.
- T014/T015 dependen de T003/T006.
- T018 al final, tras todos los cambios.
