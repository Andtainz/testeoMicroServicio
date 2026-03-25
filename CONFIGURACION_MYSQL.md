# 🔧 Configuración MySQL - Guía de Instalación

## Paso 1: Instalar MySQL Server

### Opción A: Windows (Descarga directa)
1. Descarga desde [mysql.com/downloads](https://dev.mysql.com/downloads/mysql/)
2. Selecciona **Windows (x86, 64-bit)**
3. Ejecuta el instalador
4. En la instalación, selecciona:
   - ✅ MySQL Server
   - ✅ MySQL Workbench (opcional, para interfaz gráfica)

### Opción B: Windows (Chocolatey)
```powershell
choco install mysql
```

### Opción C: Windows (Docker - Más rápido)
```powershell
docker run --name mysql-productos -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=productos_db -p 3306:3306 -d mysql:8.0
```

---

## Paso 2: Crear la Base de Datos

### Opción A: Con MySQL Workbench (GUI)
1. Abre MySQL Workbench
2. Crea una nueva conexión a `localhost:3306`
3. Usuario: `root` / Contraseña: `root`
4. Click derecho → **Create Schema**
5. Nombre: `productos_db`
6. Click **Apply**

### Opción B: Con línea de comandos (CMD/PowerShell)
```bash
mysql -u root -p
```
Ingresa la contraseña: `root`

Luego ejecuta:
```sql
CREATE DATABASE productos_db;
```

---

## Paso 3: Verificar Conexión

En PowerShell, verifica que MySQL esté corriendo:
```powershell
netstat -ano | findstr :3306
```

Deberías ver algo como:
```
TCP    0.0.0.0:3306           0.0.0.0:0              LISTENING       1234
```

---

## Paso 4: Configuración en la Aplicación

El archivo `application.properties` ya está configurado:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/productos_db
spring.datasource.username=root
spring.datasource.password=root
```

**Si usaste contraseña diferente**, modificar:
```properties
spring.datasource.password=TU_CONTRASEÑA_AQUI
```

---

## Paso 5: Reiniciar la Aplicación

```powershell
$env:JAVA_HOME="C:\Program Files\Java\jdk-21.0.10"
cd "c:\Users\joshu\Desktop\Nueva carpeta\ActividadMicroServicios"
.\mvnw.cmd clean spring-boot:run
```

**Deberías ver en la consola:**
```
Hibernate: create table producto (id bigint not null auto_increment, nombre varchar(255), precio double precision, primary key (id))
✅ Datos iniciales cargados
```

---

## Paso 6: Verificar en MySQL Workbench

1. Abre MySQL Workbench
2. Conéctate a `localhost:3306`
3. En el panel izquierdo, expande **productos_db** → **Tables**
4. Deberías ver la tabla **producto** creada automáticamente
5. Click derecho → **Select Rows - Limit 1000** para ver los datos

---

## Cambiar Contraseña de Root (Opcional)

Si quieres cambiar la contraseña de `root`:

```sql
ALTER USER 'root'@'localhost' IDENTIFIED BY 'nueva_contraseña';
FLUSH PRIVILEGES;
```

Luego actualizar `application.properties`:
```properties
spring.datasource.password=nueva_contraseña
```

---

## Solución de Problemas

### Error: "Access denied for user 'root'@'localhost'"
**Solución:** Verificar contraseña en `application.properties`
```properties
spring.datasource.password=root  # Cambiar si es diferente
```

### Error: "Cannot connect to MySQL server on 'localhost:3306'"
**Solución:** MySQL no está corriendo
```powershell
# En Windows, verificar que el servicio está activo
Get-Service MySQL80  # O el nombre del servicio

# Si no está activo, iniciarlo
Start-Service MySQL80
```

### Error: "Unknown database 'productos_db'"
**Solución:** Crear la base de datos manualmente
```sql
CREATE DATABASE productos_db;
```

---

## Comparativa: H2 vs MySQL

| Aspecto | H2 | MySQL |
|---|---|---|
| **Instalación** | No requiere | Requiere instalación |
| **Tipo** | Embebida (en memoria) | Servidor externo |
| **Datos persistentes** | No (se pierden al reiniciar) | Sí (permanecen) |
| **Producción** | ❌ No recomendado | ✅ Recomendado |
| **Velocidad desarrollo** | ✅ Muy rápido | Normal |
| **Escalabilidad** | Limitada | Excelente |

---

## Próximos Pasos

Una vez configurado MySQL:

1. **Hacer requests en Postman** (funcionan igual que con H2)
2. **Verificar datos en MySQL Workbench**
3. **Escalar para más microservicios**

---

**¿Necesitas ayuda con la configuración de MySQL?**
