# Implementation Plan: CRUD de Departamentos

**Branch**: `002-crud-departamentos` | **Date**: 2026-03-10 | **Spec**: `/specs/002-crud-departamentos/spec.md`
**Input**: Feature specification from `/specs/002-crud-departamentos/spec.md`

**Note**: This template is filled in by the `/speckit.plan` command. See `.specify/templates/plan-template.md` for the execution workflow.

## Summary

Implementar CRUD de departamentos en Spring Boot con PostgreSQL y relación 1:N con empleados,
integrando cambios de esquema, nueva entidad y DAO, ajuste del modelo de empleado para `departamento_id`
y documentación OpenAPI. El plan de implementación se ejecuta en cinco pasos: (1) script SQL,
(2) entidad `Departamento`, (3) DAO CRUD de departamentos, (4) modificación de `Empleado` y su DAO,
y (5) ejemplo de uso para validación funcional end-to-end.

## Technical Context

**Language/Version**: Java 17  
**Primary Dependencies**: Spring Boot 3 (Web, Data JPA, Validation, Security), PostgreSQL JDBC, Springdoc OpenAPI  
**Storage**: PostgreSQL (schema.sql inicializado por Spring)  
**Testing**: JUnit 5 + Spring Boot Test + MockMvc  
**Target Platform**: Linux server y desarrollo local con Docker
**Project Type**: backend web-service monolítico  
**Performance Goals**: >=95% de operaciones CRUD de departamentos <2s en carga normal  
**Constraints**: HTTP Basic obligatorio, integridad referencial al eliminar departamentos (RESTRICT), no romper CRUD existente de empleados  
**Scale/Scope**: 2 recursos relacionados (`departamentos`, `empleados`), 5 endpoints para departamentos + ajuste de payload de empleados

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

- [x] Confirms Spring Boot 3 and Java 17 as implementation baseline
- [x] Confirms HTTP Basic Auth impact and credential strategy (dev vs production)
- [x] Confirms PostgreSQL persistence impact (schema/data changes documented)
- [x] Confirms Docker runtime impact (container/compose updates identified)
- [x] Confirms Swagger/OpenAPI impact for every API change

**Post-Design Re-check**: PASS. Los artefactos de diseño (research/data-model/contracts/quickstart)
mantienen cumplimiento con Spring Boot 3 + Java 17, Basic Auth, PostgreSQL, Docker y OpenAPI.

## Project Structure

### Documentation (this feature)

```text
specs/002-crud-departamentos/
├── plan.md
├── research.md
├── data-model.md
├── quickstart.md
├── contracts/
│   └── openapi.yaml
└── tasks.md
```

### Source Code (repository root)

```text
src/
├── main/
│   ├── java/com/dsw01/practica02/
│   │   ├── controller/
│   │   │   ├── DepartamentoController.java
│   │   │   └── EmpleadoController.java
│   │   ├── domain/
│   │   │   ├── Departamento.java
│   │   │   └── Empleado.java
│   │   ├── dto/
│   │   │   ├── DepartamentoCreateRequest.java
│   │   │   ├── DepartamentoResponse.java
│   │   │   ├── DepartamentoUpdateRequest.java
│   │   │   ├── EmpleadoCreateRequest.java
│   │   │   └── EmpleadoUpdateRequest.java
│   │   ├── repository/
│   │   │   ├── DepartamentoRepository.java
│   │   │   └── EmpleadoRepository.java
│   │   ├── service/
│   │   │   ├── DepartamentoService.java
│   │   │   └── EmpleadoService.java
│   │   └── config/
│   │       ├── SecurityConfig.java
│   │       └── OpenApiConfig.java
│   └── resources/
│       ├── application.properties
│       └── schema.sql
└── test/
   └── java/com/dsw01/practica02/

docker/
└── docker-compose.yml
```

**Structure Decision**: Se mantiene estructura monolítica Spring Boot existente y se extiende de forma
incremental sobre módulos ya presentes para minimizar riesgo de regresión.

## Implementation Steps (Paso a Paso)

1. **Script SQL**: actualizar `schema.sql` para crear `departamentos(id, nombre, descripcion)` y agregar
  `departamento_id` en `empleados` con foreign key restrictiva.
2. **Entidad Departamento**: ajustar `Departamento` al nuevo contrato (`id`, `nombre`, `descripcion`)
  y mapear relación 1:N con `Empleado`.
3. **DAO para CRUD**: definir/ajustar `DepartamentoRepository` para CRUD + consultas de soporte,
  y uso desde `DepartamentoService`.
4. **Modificación en Empleado**: adaptar entidad/DTO/servicio/repositorio para trabajar con `departamento_id`
  y validar existencia de departamento.
5. **Ejemplo de uso**: documentar flujo en `quickstart.md` y contrato `openapi.yaml` con ejemplos
  de creación de departamento, creación de empleado asociado y validación de borrado restringido.

## Complexity Tracking

No constitution violations identified; no complexity exceptions required.

| Violation | Why Needed | Simpler Alternative Rejected Because |
|-----------|------------|-------------------------------------|
| N/A | N/A | N/A |
