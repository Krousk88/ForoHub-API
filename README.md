# ForoHub-API
# ForoHub - Challenge Alura Latam 🚀

ForoHub es una API REST desarrollada en Java con Spring Boot que simula el funcionamiento del foro de Alura. Permite la gestión de tópicos, respuestas, usuarios y cursos, implementando autenticación y persistencia de datos.

## 🔨 Funcionalidades
- **CRUD de Tópicos:** Crear, listar, actualizar y eliminar preguntas del foro.
- **Autenticación:** Sistema de login seguro utilizando **Spring Security** y **JWT**.
- **Persistencia:** Gestión de base de datos relacional y migraciones con **Flyway**.
- **Validaciones:** Reglas de negocio para evitar duplicados y asegurar datos íntegros.

## 🛠️ Tecnologías Utilizadas
- [Java 17+](https://www.oracle.com)
- [Spring Boot 3](https://spring.io)
- [Spring Data JPA](https://spring.io)
- [Spring Security](https://spring.io)
- [MySQL](https://www.mysql.com) / [PostgreSQL](https://www.postgresql.org)
- [Maven](https://maven.apache.org)

## 🚀 Instalación y Ejecución
1. Clona el repositorio:
   `git clone https://github.com`
2. Configura tus variables de entorno en `src/main/resources/application.properties` (Base de datos y JWT Secret).
3. Compila y ejecuta con Maven:
   `mvn spring-boot:run`

## 📖 Documentación
Una vez en ejecución, puedes consultar los endpoints en:
- **Swagger UI:** `http://localhost:8080/swagger-ui.html`

