# Implementation Plan: Autenticación de empleados con usuario y contraseña

**Branch**: `001-empleado-auth` | **Date**: 2026-03-10 | **Spec**: `/specs/001-empleado-password-auth/spec.md`
**Input**: Feature specification from `/specs/001-empleado-password-auth/spec.md`

**Note**: This template is filled in by the `/speckit.plan` command. See `.specify/templates/plan-template.md` for the execution workflow.

## Summary

Agregar autenticación por empleado usando `username` y `password` con almacenamiento seguro de
contraseña hash en PostgreSQL. La implementación mantiene el CRUD existente de empleados,
extiende la tabla `empleados` con `username` y `password_hash`, y añade un flujo de login en capa
de servicio/controlador usando JDBC para validar credenciales contra hash bcrypt.

## Step-by-step Implementation Scope

1. Modificar tabla `empleados` para agregar `username` y `password_hash` con restricciones de
  unicidad/no nulo.
2. Actualizar entidad `Empleado` y DTOs para manejar `username` sin exponer `password_hash`.
3. Crear servicio de autenticación (`AuthService`) y repositorio JDBC específico para credenciales.
4. Implementar `login(username, password)` con respuesta de éxito/error genérico.
5. Validar contraseña con `PasswordEncoder.matches` sobre hash bcrypt almacenado.
6. Añadir ejemplo ejecutable en prueba de integración (login exitoso/fallido) para demostrar uso.

## Technical Context

<!--
  ACTION REQUIRED: Replace the content in this section with the technical details
  for the project. The structure here is presented in advisory capacity to guide
  the iteration process.
-->

**Language/Version**: Java 17  
**Primary Dependencies**: Spring Boot 3 (Web, Security, Validation, Data JPA), Spring JDBC, PostgreSQL Driver, Springdoc OpenAPI  
**Storage**: PostgreSQL (`empleados` extendida con credenciales)  
**Testing**: JUnit 5 + Spring Boot Test + MockMvc  
**Target Platform**: Linux server con Docker
**Project Type**: backend web-service monolítico  
**Performance Goals**: 95% de logins válidos < 2 segundos en entorno local/QA  
**Constraints**: usar JDBC en validación de credenciales, mantener compatibilidad con CRUD de empleados, no exponer hash ni contraseña en respuestas  
**Scale/Scope**: autenticación para empleados existentes y nuevos; 1 endpoint de login + ajuste de endpoints CRUD de empleados

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

- [ ] Confirms Spring Boot 3 and Java 17 as implementation baseline
- [ ] Confirms HTTP Basic Auth impact and credential strategy (dev vs production)
- [ ] Confirms PostgreSQL persistence impact (schema/data changes documented)
- [ ] Confirms Docker runtime impact (container/compose updates identified)
- [ ] Confirms Swagger/OpenAPI impact for every API change

- [x] Confirms Spring Boot 3 and Java 17 as implementation baseline
- [x] Confirms HTTP Basic Auth impact and credential strategy (dev vs production)
- [x] Confirms PostgreSQL persistence impact (schema/data changes documented)
- [x] Confirms Docker runtime impact (container/compose updates identified)
- [x] Confirms Swagger/OpenAPI impact for every API change

**Post-Design Re-check**: PASS. El diseño mantiene Spring Boot 3 + Java 17, conserva Basic Auth
para endpoints existentes, documenta impacto en PostgreSQL (`username`, `password_hash`), no
requiere cambios estructurales de Docker y define actualización de contrato OpenAPI para login y
payloads de empleados.

## Project Structure

### Documentation (this feature)

```text
specs/001-empleado-password-auth/
├── plan.md              # This file (/speckit.plan command output)
├── research.md          # Phase 0 output (/speckit.plan command)
├── data-model.md        # Phase 1 output (/speckit.plan command)
├── quickstart.md        # Phase 1 output (/speckit.plan command)
├── contracts/           # Phase 1 output (/speckit.plan command)
└── tasks.md             # Phase 2 output (/speckit.tasks command - NOT created by /speckit.plan)
```

### Source Code (repository root)

```text
src/
├── main/
│   ├── java/com/dsw01/practica02/
│   │   ├── config/
│   │   ├── controller/
│   │   ├── domain/
│   │   ├── dto/
│   │   ├── repository/
│   │   └── service/
│   └── resources/
│       ├── application.properties
│       └── schema.sql
└── test/
    └── java/com/dsw01/practica02/

docs/
└── api/

docker/
└── docker-compose.yml
```

**Structure Decision**: Se mantiene estructura monolítica Spring Boot existente; el feature añade
componentes de autenticación en `controller/service/repository/dto`, extiende `domain` y `schema.sql`,
y actualiza contrato OpenAPI del feature en `specs/001-empleado-password-auth/contracts/`.

## Complexity Tracking

No constitution violations identified; complexity exceptions are not required.

| Violation | Why Needed | Simpler Alternative Rejected Because |
|-----------|------------|-------------------------------------|
| N/A | N/A | N/A |
