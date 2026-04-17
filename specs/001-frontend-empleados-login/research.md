# Phase 0 Research - Frontend de Empleados con Login

## Decision 1: Estrategia de monorepo para frontend
- **Decision**: Incorporar app Angular 19 en `apps/frontend` dentro del mismo repositorio.
- **Rationale**: Cumple principio constitucional de monorepo y permite coordinación directa con backend y contratos.
- **Alternatives considered**:
  - Repositorio separado para frontend: descartado por incumplir política de monorepo.
  - Ubicar frontend en raíz `frontend/`: válido, pero `apps/frontend` escala mejor para futuras apps.

## Decision 2: Flujo de autenticación en frontend
- **Decision**: Login explícito con `email` y `password` contra `POST /api/auth/login`.
- **Rationale**: Respeta requerimiento funcional del feature y provee UX de login dedicada.
- **Alternatives considered**:
  - HTTP Basic directo por request desde el formulario: descartado por UX deficiente y acoplamiento excesivo.
  - Mantener solo `admin/admin123` fijo: descartado por no cumplir login por email y contraseña.

## Decision 3: Integración con política de seguridad backend
- **Decision**: Mantener cumplimiento de seguridad del backend documentando excepción de login público (`/api/auth/login`) y protección de endpoints CRUD.
- **Rationale**: Permite autenticación inicial sin romper el marco de seguridad existente.
- **Alternatives considered**:
  - Exponer CRUD sin protección para frontend: descartado por violar constitución.
  - Obligar Basic Auth en login: descartado por contradicción funcional.

## Decision 4: Modelo de sesión frontend
- **Decision**: Gestionar estado autenticado en capa `core/auth` con guard de rutas y cierre de sesión explícito.
- **Rationale**: Aísla responsabilidades de sesión y evita lógica duplicada en componentes CRUD.
- **Alternatives considered**:
  - Validar autenticación en cada componente: descartado por duplicación y mayor riesgo de errores.

## Decision 5: Contrato de datos de empleados en frontend
- **Decision**: Consumir contrato de empleados con operaciones list/detail/create/update/delete y mensajes de error estandarizados.
- **Rationale**: Permite trazabilidad entre requerimientos, UI y backend OpenAPI.
- **Alternatives considered**:
  - Contratos implícitos sin archivo: descartado por baja verificabilidad.

## Decision 6: Testing objetivo para feature
- **Decision**: Priorizar pruebas de login, guard de rutas y operaciones CRUD críticas en frontend.
- **Rationale**: Cubre riesgos principales de negocio y seguridad del feature.
- **Alternatives considered**:
  - Solo validación manual: descartada por menor repetibilidad.

## Decision 7: Ejecución local y reproducibilidad
- **Decision**: Quickstart con backend + PostgreSQL vía Docker y frontend Angular 19 en modo dev.
- **Rationale**: Mantiene onboarding simple y consistente con la constitución.
- **Alternatives considered**:
  - Setup sin Docker: descartado por menor paridad entre entornos.
