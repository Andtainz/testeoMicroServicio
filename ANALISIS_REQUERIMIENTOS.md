# Análisis de Requerimientos y Diseño de Microservicio
## Caso Práctico: Sistema de Gestión de Productos - E-Commerce

---

## 1. ANÁLISIS DE REQUERIMIENTOS

### 1.1 Descripción del Caso Práctico

Se requiere implementar un **microservicio de gestión de productos** para una plataforma de e-commerce. Este servicio debe ser capaz de:

- Mantener un catálogo de productos
- Permitir consultar información de productos
- Permitir agregar, actualizar y eliminar productos
- Persistir datos de manera confiable
- Estar disponible de forma independiente

### 1.2 Requerimientos Funcionales

| Requerimiento | Descripción | Endpoint |
|---|---|---|
| RF-001 | Listar todos los productos | `GET /productos` |
| RF-002 | Obtener detalles de un producto | `GET /productos/{id}` |
| RF-003 | Crear un nuevo producto | `POST /productos` |
| RF-004 | Actualizar un producto existente | `PUT /productos/{id}` |
| RF-005 | Eliminar un producto | `DELETE /productos/{id}` |

### 1.3 Requerimientos No Funcionales

| Requerimiento | Descripción |
|---|---|
| RNF-001 | **Independencia**: Debe funcionar de forma aislada |
| RNF-002 | **Escalabilidad**: Permite aumentar capacidad sin afectar otros servicios |
| RNF-003 | **Persistencia**: Los datos deben ser almacenados en una base de datos |
| RNF-004 | **Disponibilidad**: Tiempo de respuesta < 500ms |
| RNF-005 | **Documentación**: API clara y bien documentada |

---

## 2. JUSTIFICACIÓN: MICROSERVICIOS vs MONOLÍTICO

### 2.1 Arquitectura Monolítica (Desventajas)

```
┌─────────────────────────────────┐
│   APLICACIÓN MONOLÍTICA         │
├─────────────────────────────────┤
│ - Gestión Productos             │
│ - Gestión Pedidos               │
│ - Gestión Usuarios              │
│ - Gestión Pagos                 │
│ - Gestión Envíos                │
└─────────────────────────────────┘
        │
        └──→ BASE DE DATOS ÚNICA
```

**Problemas:**
- ❌ Si "Gestión de Productos" falla, **toda la aplicación cae**
- ❌ Para escalar el servicio de productos, **debe escalarse toda la aplicación**
- ❌ Cambios en un servicio pueden **romper otros**
- ❌ Difícil de mantener y evolucionar
- ❌ Un pico de tráfico en productos afecta pagos, usuarios, etc.

### 2.2 Arquitectura de Microservicios (Ventajas)

```
┌──────────────────┐    ┌──────────────────┐    ┌──────────────────┐
│  MS Productos    │    │  MS Pedidos      │    │  MS Usuarios     │
├──────────────────┤    ├──────────────────┤    ├──────────────────┤
│ - GET /productos │    │ - GET /pedidos   │    │ - GET /usuarios  │
│ - POST /productos│    │ - POST /pedidos  │    │ - POST /usuarios │
│ - PUT /productos │    │ - PUT /pedidos   │    │ - PUT /usuarios  │
│ - DELETE /...    │    │ - DELETE /...    │    │ - DELETE /...    │
└──────────────────┘    └──────────────────┘    └──────────────────┘
        │                       │                       │
        ↓                       ↓                       ↓
   BD Productos          BD Pedidos              BD Usuarios
```

**Ventajas:**
- ✅ **Independencia**: Si Pedidos falla, Productos sigue funcionando
- ✅ **Escalabilidad**: Escalo solo el servicio que lo necesita
- ✅ **Flexibilidad**: Cada equipo puede usar tecnologías diferentes
- ✅ **Mantenibilidad**: Código más pequeño y enfocado
- ✅ **Resiliencia**: Fallos aislados

### 2.3 Comparativa

| Aspecto | Monolítico | Microservicios |
|---|---|---|
| **Complejidad inicial** | Baja | Media |
| **Escalabilidad** | Limitada | Excelente |
| **Independencia** | Nula | Total |
| **Rendimiento** | Bueno | Excelente |
| **Tolerancia a fallos** | Ninguna | Alta |
| **Mantenibilidad** | Difícil (grande) | Fácil (simples) |
| **Para este proyecto** | ❌ No recomendado | ✅ **RECOMENDADO** |

---

## 3. JUSTIFICACIÓN DEL DISEÑO DEL MICROSERVICIO

### 3.1 ¿Por qué Microservicios para este caso?

Este proyecto implementa un **microservicio de productos** porque:

1. **Necesidad de Independencia**
   - El servicio de productos puede estar disponible sin depender de otros servicios
   - Otros servicios (pedidos, pagos) pueden llamarlo bajo demanda

2. **Escalabilidad Diferenciada**
   - Si hay picos de consultas de productos, escalo solo ese microservicio
   - No afecta la capacidad de procesamiento de pagos o pedidos

3. **Equipos Autónomos**
   - Un equipo puede desarrollar, desplegar y mantener este servicio: sin que otros lo aprueben

4. **Facilidad de Mantenimiento**
   - Código pequeño y enfocado únicamente en gestión de productos
   - Fácil de entender, testear y modificar

5. **Tecnología Adecuada**
   - Spring Boot es ideal para microservicios
   - REST es el estándar para comunicación entre servicios

---

## 4. ESPECIFICACIÓN TÉCNICA DEL MICROSERVICIO

### 4.1 Información General

| Propiedad | Valor |
|---|---|
| **Nombre** | Microservicio de Productos |
| **Código** | MS-PRODUCTOS |
| **Tech Stack** | Java 21 + Spring Boot 4.0 |
| **Base de Datos** | H2 (desarrollo) |
| **Puerto** | 8080 |

### 4.2 Modelo de Datos

```java
@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;      // Ej: "Laptop"
    private double precio;       // Ej: 850000.0
}
```

| Campo | Tipo | Descripción | Ejemplo |
|---|---|---|---|
| **id** | Long | Identificador único (autogenerado) | 1, 2, 3... |
| **nombre** | String | Nombre del producto | "Laptop", "Mouse" |
| **precio** | Double | Precio en unidad monetaria | 850000.0 |

### 4.3 Endpoint: GET /productos

**Descripción:** Retorna la lista completa de productos

```
GET http://localhost:8080/productos
```

**Respuesta (Status 200 - OK):**
```json
[
  {
    "id": 1,
    "nombre": "Laptop",
    "precio": 850000
  },
  {
    "id": 2,
    "nombre": "Mouse",
    "precio": 15000
  }
]
```

**Casos de uso:**
- Cargar catálogo en tienda online
- Obtener inventario disponible
- Sincronizar con otros servicios

---

### 4.4 Endpoint: POST /productos

**Descripción:** Crea un nuevo producto

```
POST http://localhost:8080/productos
Content-Type: application/json
```

**Body (Entrada):**
```json
{
  "nombre": "Teclado Mecánico",
  "precio": 125000
}
```

**Respuesta (Status 201 - Created):**
```json
{
  "id": 4,
  "nombre": "Teclado Mecánico",
  "precio": 125000
}
```

**Casos de uso:**
- Agregar nuevo producto al catálogo
- Importar productos desde proveedores
- Cargar inventario inicial

---

### 4.5 Endpoint: PUT /productos/{id}

**Descripción:** Actualiza un producto existente

```
PUT http://localhost:8080/productos/1
Content-Type: application/json
```

**Body (Entrada):**
```json
{
  "nombre": "Laptop Gaming",
  "precio": 1200000
}
```

**Respuesta (Status 200 - OK):**
```json
{
  "id": 1,
  "nombre": "Laptop Gaming",
  "precio": 1200000
}
```

**Casos de uso:**
- Actualizar precio de producto
- Modificar nombre o descripción
- Cambios en especificaciones

---

### 4.6 Endpoint: DELETE /productos/{id}

**Descripción:** Elimina un producto

```
DELETE http://localhost:8080/productos/1
```

**Respuesta (Status 200 - OK):**
```
Producto eliminado
```

**Casos de uso:**
- Discontinuar un producto
- Eliminar productos defectuosos
- Limpiar inventario obsoleto

---

## 5. ARQUITECTURA DEL MICROSERVICIO

### 5.1 Componentes

```
┌─────────────────────────────────────────┐
│         CLIENTE (Postman/App)           │
└────────────┬──────────────────┬─────────┘
             │                  │
        HTTP Request       HTTP Response
             │                  │
             ↓                  ↓
┌─────────────────────────────────────────┐
│      Spring Boot REST Controller       │
│    (ProductoController.java)            │
│  - GET /productos                       │
│  - POST /productos                      │
│  - PUT /productos/{id}                  │
│  - DELETE /productos/{id}               │
└─────────┬───────────────────────────────┘
          │
          ↓
┌─────────────────────────────────────────┐
│     Spring Data JPA Repository          │
│    (ProductoRepository.java)            │
│  - findAll()                            │
│  - save()                               │
│  - deleteById()                         │
└─────────┬───────────────────────────────┘
          │
          ↓
┌─────────────────────────────────────────┐
│         Hibernate ORM                   │
│    (Mapeo Objeto-Relacional)            │
└─────────┬───────────────────────────────┘
          │
          ↓
┌─────────────────────────────────────────┐
│       Base de Datos H2                  │
│   (Tabla: PRODUCTO)                     │
│  - id (PK)                              │
│  - nombre                               │
│  - precio                               │
└─────────────────────────────────────────┘
```

### 5.2 Flujo de Solicitud

```
1. Cliente envía: GET /productos
            ↓
2. Spring Router dirige a: ProductoController.listarProductos()
            ↓
3. Controller llama: repository.findAll()
            ↓
4. Repository genera SQL: SELECT * FROM PRODUCTO
            ↓
5. H2 ejecuta la query en la BD
            ↓
6. Resultado mapeado a List<Producto>
            ↓
7. Jackson convierte a JSON
            ↓
8. Cliente recibe: [{"id":1, "nombre":"Laptop", ...}]
```

---

## 6. TECNOLOGÍAS UTILIZADAS

### 6.1 Backend

| Tecnología | Versión | Propósito |
|---|---|---|
| **Java** | 21 LTS | Lenguaje de programación |
| **Spring Boot** | 4.0.4 | Framework web y DI |
| **Spring Data JPA** | - | Acceso a datos |
| **Hibernate** | - | ORM (Object-Relational Mapping) |
| **H2 Database** | - | Base de datos embebida |

### 6.2 Testing y Entrega

| Herramienta | Propósito |
|---|---|
| **Postman** | Prueba de endpoints REST |
| **Maven** | Build y dependencias |
| **GitHub/Git** | Control de versiones |

---

## 7. CONCLUSIONES

### 7.1 Análisis Final

Este proyecto demuestra por qué **los microservicios son superiores a una arquitectura monolítica** para aplicaciones empresariales:

✅ **Independencia operativa**: El servicio de productos corre sin depender de otros  
✅ **Escalabilidad**: Puedo escalar solo lo que necesito escalar  
✅ **Mantenibilidad**: Código pequeño, enfocado y fácil de entender  
✅ **Resiliencia**: Los fallos están aislados  
✅ **Productividad**: Equipos autónomos pueden trabajar en paralelo  

### 7.2 Próximos Pasos

Para llevar esto a producción:

1. **Seguridad**: Agregar autenticación JWT
2. **Validación**: Validar entrada de datos
3. **Logs**: Agregar logging centralizado
4. **Monitoring**: Métricas y alertas
5. **CI/CD**: Pipeline de despliegue automático
6. **Documentación**: Swagger/OpenAPI
7. **Comunicación**: Implementar publicador-suscriptor (RabbitMQ, Kafka)

---

## 8. REFERENCIAS

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Building Microservices - Sam Newman](https://www.oreilly.com/library/view/building-microservices/9781491950340/)
- [H2 Database](https://www.h2database.com/)

---

**Documento generado:** 25 de Marzo, 2026  
**Versión:** 1.0  
**Autor:** Equipo de Desarrollo
