# Quickstart - Frontend de Empleados con Login

## Prerrequisitos
- Node.js 20+
- npm 10+
- Java 17
- Maven 3.9+
- Docker y Docker Compose

## 1) Levantar backend + PostgreSQL
```bash
docker compose -f docker/docker-compose.yml up -d
mvn spring-boot:run
```

## 2) Crear app Angular 19 en monorepo
```bash
cd frontend
npm install
```

## 3) Ejecutar frontend
```bash
npm run start
```

Frontend disponible en `http://localhost:4200`.

## 4) Probar login (email + contraseña)
1. Abrir pantalla de login.
2. Capturar `email` y `password`.
3. Enviar a `POST http://localhost:8080/api/auth/login`.
4. Verificar redirección al módulo de empleados en autenticación exitosa.

## 5) Probar CRUD de empleados
- Listar empleados desde la vista principal.
- Crear empleado con formulario válido.
- Editar empleado existente.
- Eliminar empleado con confirmación.
- Verificar mensajes de error cuando backend responda 4xx/5xx.

## 6) Verificar protección de rutas
- Sin sesión activa, navegar a rutas CRUD debe redirigir a login.
- Con sesión activa, rutas CRUD deben ser accesibles.

## 7) Verificar contrato
- Contrato del feature: `specs/001-frontend-empleados-login/contracts/openapi.yaml`.
- Confirmar que login usa `email` + `password`.
