# ActividadMicroServicios - Spring Boot API

Aplicación **Spring Boot 4.0.4** con **Java 21** que implementa un servicio REST profesional para gestionar productos con base de datos MySQL.

## 🚀 Características

- ✅ **API REST profesional** con manejo de errores centralizado
- ✅ **Estructura de capas** (Controller → Service → Repository)
- ✅ **DTOs** para separación de responsabilidades
- ✅ **Validaciones** con Jakarta Validation
- ✅ **Variables de entorno** configurables
- ✅ **Perfiles** para dev/prod (application.yml)
- ✅ **Base de datos** MySQL con JPA/Hibernate
- ✅ **Transaccionalidad** en operaciones
- ✅ **Auditoría** con timestamps de creación/actualización
- ✅ **Logging estructurado**

## 📋 Requisitos

- Java 21+
- Maven 3.9+
- MySQL 8.0+ (o compatible)

## ⚙️ Instalación

### 1. Clonar el repositorio
```bash
git clone <repository-url>
cd ActividadMicroServicios
```

### 2. Configurar Base de Datos
```bash
# Crear base de datos en MySQL
CREATE DATABASE productos_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 3. Configurar Variables de Entorno
Copia el archivo `.env.example` a `.env.local`:
```bash
cp .env.example .env.local
```

Edita `.env.local` con tus credenciales de MySQL:
```env
DB_HOST=localhost
DB_PORT=3306
DB_NAME=productos_db
DB_USER=root
DB_PASSWORD=tuContraseña
SERVER_PORT=8080
SPRING_PROFILES_ACTIVE=dev
```

## 🏃 Ejecución

### Desarrollo
```bash
# Con perfil dev
./mvnw spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"

# O simplemente (usa dev por defecto)
./mvnw spring-boot:run
```

### Compilación
```bash
./mvnw clean compile
```

### Testing
```bash
./mvnw test
```

### Build para Producción
```bash
./mvnw clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

## 📡 API Endpoints

Base URL: `http://localhost:8080/api/v1`

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| **GET** | `/productos` | Listar todos los productos |
| **GET** | `/productos/{id}` | Obtener producto por ID |
| **POST** | `/productos` | Crear nuevo producto |
| **PUT** | `/productos/{id}` | Actualizar producto |
| **DELETE** | `/productos/{id}` | Eliminar producto |

### Ejemplo: Crear Producto

**Request:**
```bash
curl -X POST http://localhost:8080/api/v1/productos \
  -H "Content-Type: application/json" \
  -d '{"nombre": "Laptop Dell", "precio": 999.99}'
```

**Response (201 Created):**
```json
{
  "success": true,
  "message": "Producto creado exitosamente",
  "data": {
    "id": 1,
    "nombre": "Laptop Dell",
    "precio": 999.99
  }
}
```

### Ejemplo: Listar Productos

**Request:**
```bash
curl http://localhost:8080/api/v1/productos
```

**Response (200 OK):**
```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "nombre": "Laptop Dell",
      "precio": 999.99
    },
    {
      "id": 2,
      "nombre": "Mouse Logitech",
      "precio": 25.99
    }
  ],
  "count": 2
}
```

## 🏗️ Arquitectura

### Capas de la Aplicación

```
HTTP Request
    ↓
ProductoController (REST endpoint)
    ↓
ProductoService (Lógica de negocio)
    ↓
ProductoRepository (Acceso a datos JPA)
    ↓
Producto Entity (Modelo JPA)
    ↓
MySQL Database
```

### Validaciones

Se aplican validaciones automáticas usando `@Valid` en los DTOs:
- `nombre`: No puede estar vacío
- `precio`: Debe ser mayor a 0

**Error de validación (400 Bad Request):**
```json
{
  "timestamp": "2026-03-25T01:45:00",
  "status": 400,
  "error": "Error de validación",
  "errors": {
    "nombre": "El nombre del producto es requerido",
    "precio": "El precio debe ser mayor a 0"
  }
}
```

## 📂 Estructura del Proyecto

```
src/main/
├── java/com/example/demo/
│   ├── Producto.java                 # Entidad JPA con auditoría
│   ├── ProductoRepository.java       # Repositorio JPA
│   ├── ProductoController.java       # Controlador REST
│   ├── DemoApplication.java          # Clase principal
│   ├── DataInitializer.java          # Inicializador de datos
│   ├── dto/
│   │   └── ProductoDTO.java         # DTO con validaciones
│   ├── service/
│   │   └── ProductoService.java     # Lógica de negocio
│   └── exception/
│       ├── GlobalExceptionHandler.java
│       └── ResourceNotFoundException.java
└── resources/
    ├── application.yml              # Config base
    ├── application-dev.yml          # Config desarrollo
    └── application-prod.yml         # Config producción
```

## 🔒 Seguridad

- Validación de entrada en todos los endpoints
- Manejo seguro de excepciones
- Manejo de SQL injection mediante JPA parameterizado
- Variables sensibles en `application-prod.yml` requieren enviroment-vars

## 📊 Base de Datos

Tabla `productos`:
```sql
CREATE TABLE productos (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(100) NOT NULL,
  precio DECIMAL(10, 2) NOT NULL,
  fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  fecha_actualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

## 🧪 Testing

El proyecto incluye configuración para testing con:
- **JUnit 5**
- **Mockito**
- **AssertJ**
- **Spring Boot Test**

Tests incluidos:
- `DemoApplicationTests.java` - Test de contexto

Para agregar más tests, ver la documentación en `ESTRUCTURA_PROFESIONAL.md`.

## 🐛 Troubleshooting

### Error: "Connection refused"
- Verifica que MySQL esté corriendo
- Comprueba las credenciales en `application-dev.yml`

### Error: "database not found"
```bash
# Crea la base de datos
mysql -u root -p
CREATE DATABASE productos_db;
```

### Error de compilación Java
- Verifica que tengas Java 21 instalado: `java -version`
- Asegúrate que JAVA_HOME apunta a Java 21

## 📚 Documentación Adicional

- Ver [ESTRUCTURA_PROFESIONAL.md](ESTRUCTURA_PROFESIONAL.md) para detalles de arquitectura
- Ver [CONFIGURACION_MYSQL.md](CONFIGURACION_MYSQL.md) para setup de MySQL
- Ver [ANALISIS_REQUERIMIENTOS.md](ANALISIS_REQUERIMIENTOS.md) para especificaciones

## 📝 Licencia

Este proyecto es de código abierto y está disponible bajo la licencia MIT.

## 👤 Autor

Proyecto educativo de microservicios con Spring Boot.

---

**Última actualización:** Marzo 2026 | **Versión:** 1.0.0
