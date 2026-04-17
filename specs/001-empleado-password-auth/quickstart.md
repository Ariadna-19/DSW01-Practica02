# Quickstart - Autenticación de empleados

## Prerrequisitos
- Java 17
- Maven 3.9+
- Docker y Docker Compose

## 1) Levantar PostgreSQL
```bash
docker compose -f docker/docker-compose.yml up -d
```

## 2) Ejecutar la aplicación
```bash
mvn spring-boot:run
```

## 3) Crear un empleado con credenciales (CRUD compatible)
```bash
curl -u admin:admin123 -X POST http://localhost:8080/api/empleados \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Ana Ruiz",
    "direccion": "Calle 10",
    "telefono": "555123456",
    "departamentoId": 1,
    "username": "aruiz",
    "password": "MiPassword123"
  }'
```

Resultado esperado:
- HTTP 201
- Respuesta incluye `username`
- Respuesta NO incluye `password_hash`

## 4) Probar login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"aruiz","password":"MiPassword123"}'
```

Resultado esperado:
- HTTP 200 con `authenticated=true`

## 5) Validar login fallido
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"aruiz","password":"incorrecta"}'
```

Resultado esperado:
- HTTP 200 con `authenticated=false` y mensaje genérico

## 6) Ejemplo de uso en prueba de integración
```java
@SpringBootTest
@AutoConfigureMockMvc
class AuthLoginIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void loginExitoso() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"username":"aruiz","password":"MiPassword123"}
                """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.authenticated").value(true));
    }
}
```

## 7) Chequeos rápidos
- `schema.sql` contiene `username` y `password_hash` en `empleados`
- `username` es único
- `password_hash` usa formato bcrypt
- CRUD de empleados sigue funcionando con Basic Auth
