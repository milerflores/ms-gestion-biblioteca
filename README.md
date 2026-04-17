## ms-biblioteca — API REST de Gestión de Biblioteca Universitaria

 Sistema backend desarrollado con **Java 21 + Spring Boot 3.5** que permite gestionar el préstamo de libros en una biblioteca universitaria. Incluye autenticación sin estado con **JWT**, control de acceso por roles (**ADMIN / USUARIO**) y persistencia en **PostgreSQL** mediante JPA e Hibernate.

---

## Tecnologías utilizadas

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 21 | Lenguaje principal |
| Spring Boot | 3.5.12 | Framework base |
| Spring Security | 6.x | Autenticación y autorización |
| Spring Data JPA | 3.x | Acceso a datos |
| Hibernate | 6.x | ORM / mapeo de entidades |
| PostgreSQL | 15+ | Base de datos relacional |
| JWT (jjwt) | 0.11.5 | Autenticación sin estado |
| MapStruct | 1.6.3 | Mapeo DTO ↔ Entidad |
| Lombok | Latest | Reducción de boilerplate |
| SpringDoc OpenAPI | 2.8.8 | Documentación Swagger UI |

---
 ## Requisitos previos

Antes de ejecutar el proyecto asegúrate de tener instalado:

- **JDK 21** o superior
- **Maven 3.8+**
- **PostgreSQL 15+** corriendo en el puerto `5433`
- Un cliente SQL (pgAdmin, DBeaver, etc.)

---

## Configuración de la base de datos

**Paso 1:** Crea la base de datos y el schema en PostgreSQL:

```sql
CREATE DATABASE "BD_GESTION_BIBLIOTECA";

\c BD_GESTION_BIBLIOTECA

CREATE SCHEMA bd_biblioteca;
```

**Paso 2:** Crea las secuencias necesarias para los IDs:

```sql
SET search_path TO bd_biblioteca;

CREATE SEQUENCE seq_tm_libro      START 1 INCREMENT 1;
CREATE SEQUENCE seq_tm_usuario    START 1 INCREMENT 1;
CREATE SEQUENCE seq_tm_rol        START 1 INCREMENT 1;
CREATE SEQUENCE seq_tt_prestamo   START 1 INCREMENT 1;
CREATE SEQUENCE seq_tt_prestamo_detalle START 1 INCREMENT 1;
```

> **Nota:** Las tablas se crean automáticamente al iniciar la aplicación gracias a `ddl-auto: update`.

**Paso 3:** Inserta los roles base:

```sql
SET search_path TO bd_biblioteca;

INSERT INTO tm_rol (nidrol, snombre, sdescripcion, nestado)
VALUES (nextval('seq_tm_rol'), 'ADMIN', 'Administrador del sistema', 1),
       (nextval('seq_tm_rol'), 'USUARIO', 'Usuario estándar', 1);
```

---

## Instalación y ejecución

**Paso 1:** Clona el repositorio:

```bash
git clone https://github.com/tu-usuario/ms-biblioteca.git
cd ms-biblioteca
```

**Paso 2:** Configura la conexión a tu base de datos en `src/main/resources/application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5433/BD_GESTION_BIBLIOTECA?currentSchema=bd_biblioteca
    username: postgres
    password: TU_CONTRASEÑA
```

**Paso 3:** Compila y ejecuta:

```bash
mvn clean install
mvn spring-boot:run
```

**Paso 4:** Verifica que la aplicación esté corriendo:

```
http://localhost:8070/api/v1/swagger-ui/index.html
```

---
