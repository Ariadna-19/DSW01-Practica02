# Implementation Plan: Frontend de Empleados con Login

**Branch**: `001-frontend-empleados-login` | **Date**: 2026-03-11 | **Spec**: `/specs/001-frontend-empleados-login/spec.md`
**Input**: Feature specification from `/specs/001-frontend-empleados-login/spec.md`

**Note**: This template is filled in by the `/speckit.plan` command. See `.specify/templates/plan-template.md` for the execution workflow.

## Summary

Construir un frontend Angular 19 en estructura monorepo para login con **email y contraseña** y
gestión CRUD de empleados. El frontend consumirá contratos HTTP documentados en OpenAPI para
autenticación y operaciones de empleados, con protección de rutas por sesión autenticada.

## Technical Context

<!--
  ACTION REQUIRED: Replace the content in this section with the technical details
  for the project. The structure here is presented in advisory capacity to guide
  the iteration process.
-->

**Language/Version**: TypeScript 5.x (Angular 19) + Java 17 (backend existente)  
**Primary Dependencies**: Angular 19, Angular Router, Angular Forms, Angular HttpClient, RxJS, Spring Boot API existente  
**Storage**: N/A en frontend (persistencia delegada a PostgreSQL vía backend)  
**Testing**: Angular unit/integration tests (Jasmine/Karma) + pruebas backend existentes  
**Target Platform**: Navegadores web modernos en Linux (entorno local con Docker)
**Project Type**: Aplicación web frontend + backend Spring Boot en monorepo  
**Performance Goals**: >=95% login válido <3s y >=95% operaciones CRUD válidas <3s en entorno local  
**Constraints**: Login obligatorio con email+contraseña; guard de rutas para CRUD; compatibilidad con política de seguridad backend y OpenAPI  
**Scale/Scope**: 1 app frontend (login + listado + detalle + alta + edición + baja de empleados)

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

- [x] Confirms Spring Boot 3 and Java 17 as implementation baseline
- [x] Confirms HTTP Basic Auth impact and credential strategy (dev vs production)
- [x] Confirms PostgreSQL persistence impact (schema/data changes documented)
- [x] Confirms Docker runtime impact (container/compose updates identified)
- [x] Confirms OpenAPI impact for every API change
- [x] Confirms Angular 19 + monorepo impact (frontend scope, workspace paths, and CI/scripts)

**Post-Design Re-check**: PASS. Los artefactos de diseño mantienen Angular 19 en monorepo,
documentan login por email y contraseña, trazan impacto en seguridad/backend, y preservan
alineación con PostgreSQL, Docker y OpenAPI.

## Project Structure

### Documentation (this feature)

```text
specs/001-frontend-empleados-login/
├── plan.md              # This file (/speckit.plan command output)
├── research.md          # Phase 0 output (/speckit.plan command)
├── data-model.md        # Phase 1 output (/speckit.plan command)
├── quickstart.md        # Phase 1 output (/speckit.plan command)
├── contracts/
│   └── openapi.yaml     # Phase 1 output (/speckit.plan command)
└── tasks.md             # Phase 2 output (/speckit.tasks command - NOT created by /speckit.plan)
```

### Source Code (repository root)
<!--
  ACTION REQUIRED: Replace the placeholder tree below with the concrete layout
  for this feature. Delete unused options and expand the chosen structure with
  real paths (e.g., apps/admin, packages/something). The delivered plan must
  not include Option labels.
-->

```text
src/
├── main/
│   ├── java/com/dsw01/practica02/
│   └── resources/
└── test/
    └── java/com/dsw01/practica02/

apps/
└── frontend/
    ├── src/
    │   ├── app/
    │   │   ├── auth/
    │   │   ├── empleados/
    │   │   ├── core/
    │   │   └── shared/
    │   └── environments/
    └── tests/

docs/
└── api/

docker/
└── docker-compose.yml
```

**Structure Decision**: Monorepo único con backend Spring Boot existente en `src/` y nuevo
frontend Angular 19 en `apps/frontend/`; contratos del feature en `specs/001-frontend-empleados-login/contracts/`.

## Complexity Tracking

> **Fill ONLY if Constitution Check has violations that must be justified**

No constitution violations identified; complexity exceptions are not required.

| Violation | Why Needed | Simpler Alternative Rejected Because |
|-----------|------------|-------------------------------------|
| N/A | N/A | N/A |
