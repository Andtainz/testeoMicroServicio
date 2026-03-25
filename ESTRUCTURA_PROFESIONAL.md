# Guía de Estructura Profesional del Proyecto

## 📁 Estructura de Carpetas

```
src/main/java/com/example/demo/
├── Producto.java                 # Entidad JPA
├── ProductoRepository.java       # Repositorio de acceso a datos
├── ProductoController.java       # Controlador REST
├── DemoApplication.java          # Clase principal de Spring Boot
├── DataInitializer.java          # Inicializador de datos
├── dto/
│   └── ProductoDTO.java         # Data Transfer Object
├── service/
│   └── ProductoService.java     # Lógica de negocio
└── exception/
    ├── GlobalExceptionHandler.java
    └── ResourceNotFoundException.java

src/main/resources/
├── application.yml              # Configuración base
├── application-dev.yml          # Configuración desarrollo
└── application-prod.yml         # Configuración producción
```

## 🔧 Variables de Entorno

Las siguientes variables se pueden configurar:
- `DB_HOST`: Host de la base de datos (default: localhost)
- `DB_PORT`: Puerto de MySQL (default: 3306)
- `DB_NAME`: Nombre de la base de datos (default: productos_db)
- `DB_USER`: Usuario de MySQL (default: root)
- `DB_PASSWORD`: Contraseña de MySQL (default: root)
- `SERVER_PORT`: Puerto del servidor (default: 8080)
- `SPRING_PROFILES_ACTIVE`: Perfil activo (dev/prod)

## 🚀 Cómo Ejecutar

### Desarrollo
```bash
./mvnw spring-boot:run
# O con perfil específico
./mvnw spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

### Testing
```bash
./mvnw test
```

### Construcción
```bash
./mvnw clean package
```

## 📝 Endpoints Disponibles

- `GET  /api/v1/productos` - Listar todos los productos
- `GET  /api/v1/productos/{id}` - Obtener producto por ID
- `POST /api/v1/productos` - Crear nuevo producto
- `PUT  /api/v1/productos/{id}` - Actualizar producto
- `DELETE /api/v1/productos/{id}` - Eliminar producto

## 📋 Ejemplo de Requests

### Crear Producto (POST)
```json
{
  "nombre": "Laptop HP",
  "precio": 999.99
}
```

### Actualizar Producto (PUT)
```json
{
  "nombre": "Laptop HP Actualizado",
  "precio": 1099.99
}
```

## 🏗️ Capas de la Aplicación

1. **Controller**: Maneja las solicitudes HTTP
2. **Service**: Lógica de negocio y transacciones
3. **Repository**: Acceso a datos
4. **Entity**: Modelo de datos
5. **DTO**: Transferencia de datos entre capas
6. **Exception handlers**: Manejo centralizado de errores

## ✨ Características Implementadas

✅ Estructura de capas (MVC/clean architecture)
✅ DTOs para separación de capas
✅ Validación de datos con Jakarta Validation
✅ Manejo centralizado de excepciones
✅ Configuración con perfiles (dev/prod)
✅ Variables de entorno desde application.yml
✅ Transaccionalidad en servicios
✅ Logging estructurado
✅ Auditoría de cambios (fechaCreacion, fechaActualizacion)
✅ Respuestas JSON estructuradas
