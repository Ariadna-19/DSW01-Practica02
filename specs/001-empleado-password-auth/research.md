# Phase 0 Research - Autenticación de empleados

## Decision 1: Formato y almacenamiento del hash de contraseña
- **Decision**: Almacenar `password_hash` como cadena bcrypt completa (incluye versión, costo y salt) en columna `VARCHAR(100)` dentro de `empleados`.
- **Rationale**: bcrypt es estándar para contraseñas, resistente a fuerza bruta comparado con hashes rápidos, y el formato completo evita columnas adicionales para salt.
- **Alternatives considered**:
  - `PBKDF2` con salt en columna separada: válido, pero añade complejidad de mapeo y validación.
  - `Argon2`: más fuerte en algunos escenarios, pero no se requiere para alcance MVP y añade dependencia/configuración adicional.
  - `SHA-256` con salt: descartado por no ser ideal para hashing de contraseñas.

## Decision 2: Validación de login
- **Decision**: Implementar `login(username, password)` en `AuthService` y validar con `PasswordEncoder.matches(passwordPlano, passwordHashPersistido)`.
- **Rationale**: evita comparar hashes manualmente, reduce errores criptográficos y mantiene coherencia con Spring Security.
- **Alternatives considered**:
  - Comparar hash generado manualmente por JDBC: descartado por riesgo de implementación insegura.
  - Delegar login solo a `UserDetailsService`: descartado porque se requiere operación explícita de login en el dominio de empleados.

## Decision 3: Tabla objetivo para credenciales
- **Decision**: Modificar la tabla existente `empleados` agregando `username` y `password_hash`.
- **Rationale**: cumple requerimiento explícito, evita joins adicionales y mantiene compatibilidad con CRUD al extender la entidad actual.
- **Alternatives considered**:
  - Crear tabla nueva `empleado_auth`: descartado porque contradice la solicitud de modificar `empleados`.

## Decision 4: Acceso a datos para autenticación con JDBC
- **Decision**: Añadir repositorio JDBC específico (`AuthJdbcRepository`) usando `JdbcTemplate` para consultar credenciales por `username`.
- **Rationale**: satisface la restricción de usar JDBC en autenticación y limita el impacto sobre repositorios JPA existentes.
- **Alternatives considered**:
  - Reutilizar solo `EmpleadoRepository` (JPA): descartado por no cumplir restricción de JDBC.
  - JDBC en controlador: descartado por romper separación de capas.

## Decision 5: Clases nuevas necesarias
- **Decision**: Incorporar `AuthController`, `AuthService`, `AuthJdbcRepository`, `LoginRequest` y `LoginResponse`.
- **Rationale**: encapsula autenticación en flujo dedicado sin acoplar innecesariamente `EmpleadoService` ni romper endpoints CRUD actuales.
- **Alternatives considered**:
  - Agregar login dentro de `EmpleadoService`: descartado por mezclar responsabilidades.
  - Exponer entidades de dominio directamente: descartado por seguridad y desacoplo.

## Decision 6: Compatibilidad con CRUD existente
- **Decision**: Mantener contratos actuales de CRUD y extender DTOs de alta/actualización con campos de credenciales; las respuestas de empleado no incluirán `password_hash`.
- **Rationale**: preserva consumidores existentes, evita fuga de datos sensibles y habilita credenciales para empleados nuevos/existentes.
- **Alternatives considered**:
  - Cambiar payloads de respuesta para incluir datos de auth: descartado por riesgo de seguridad y ruptura de contrato.

## Decision 7: Seguridad y configuración
- **Decision**: Mantener HTTP Basic global del proyecto; permitir acceso público solo a `POST /api/auth/login` y endpoints Swagger/health ya existentes.
- **Rationale**: alinea constitución y habilita login sin credenciales previas.
- **Alternatives considered**:
  - Hacer login autenticado por Basic: descartado porque volvería inútil la operación de login.
  - Migrar a JWT en este feature: descartado por sobrealcance.
