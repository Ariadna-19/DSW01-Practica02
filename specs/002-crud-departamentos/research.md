# Phase 0 Research - CRUD de Departamentos

## Decision 1: Estrategia de clave primaria para departamentos
- **Decision**: Usar `id BIGSERIAL` como clave primaria técnica en `departamentos`.
- **Rationale**: Simplifica joins, mantiene integridad referencial robusta y alinea con el requerimiento explícito `id`.
- **Alternatives considered**:
  - `clave` textual tipo `D-0001`: descartado porque la especificación de este feature pide `id`.
  - UUID: descartado por sobrecomplejidad para alcance actual.

## Decision 2: Política de eliminación de departamentos
- **Decision**: Política **RESTRICT** al eliminar departamentos con empleados asociados.
- **Rationale**: Evita pérdida accidental de datos y cumple FR-005/SC-003.
- **Alternatives considered**:
  - `ON DELETE CASCADE`: descartado por riesgo de borrar empleados en cadena.
  - `ON DELETE SET NULL`: descartado porque el vínculo empleado-departamento es obligatorio para este dominio.

## Decision 3: Script SQL y migración de relación
- **Decision**: Actualizar `schema.sql` para crear `departamentos(id, nombre, descripcion)` y agregar `departamento_id` en `empleados` con FK.
- **Rationale**: Mantiene inicialización reproducible del entorno local y permite integración directa con Spring Boot.
- **Alternatives considered**:
  - Migración externa (Flyway/Liquibase): válida, pero fuera del alcance inmediato y no requerida para este repositorio actual.

## Decision 4: Modelo JPA de relación
- **Decision**: Mapear `Departamento` 1:N `Empleado` con `@OneToMany(mappedBy="departamento")` y `Empleado` con `@ManyToOne @JoinColumn(name="departamento_id")`.
- **Rationale**: Refleja cardinalidad del negocio y alinea capa dominio con esquema SQL.
- **Alternatives considered**:
  - Relación unidireccional solo en `Empleado`: descartada por menor expresividad para consultas de departamento con empleados.

## Decision 5: Estructura DAO
- **Decision**: `DepartamentoRepository extends JpaRepository<Departamento, Long>` con consultas de negocio (`existsByNombreIgnoreCase`, búsqueda por nombre si aplica) y ajuste de `EmpleadoRepository` con soporte por `departamento.id`.
- **Rationale**: Reutiliza Spring Data JPA, reduce código boilerplate y facilita validaciones previas a persistencia.
- **Alternatives considered**:
  - DAO manual con `EntityManager`: descartado por mayor esfuerzo de mantenimiento.

## Decision 6: Compatibilidad con CRUD de empleados existente
- **Decision**: Mantener endpoints actuales de empleados y ampliar payload para recibir `departamentoId` en create/update.
- **Rationale**: Cumple FR-008 al no romper rutas existentes y agrega validación explícita de FK.
- **Alternatives considered**:
  - Crear endpoints nuevos para empleados por departamento únicamente: descartado por no cubrir compatibilidad pedida.

## Decision 7: Contrato OpenAPI y ejemplo de uso
- **Decision**: Documentar endpoints de departamentos y ejemplos de flujo conjunto (crear departamento -> crear empleado asociado -> bloqueo de eliminación con empleados).
- **Rationale**: Permite validar técnicamente el feature de manera reproducible y auditable.
- **Alternatives considered**:
  - Documentación solo en README: descartada por menor trazabilidad de contrato.
