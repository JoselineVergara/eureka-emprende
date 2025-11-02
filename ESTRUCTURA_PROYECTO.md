# Estructura de Carpetas del Proyecto

Este proyecto sigue una estructura típica de una aplicación Spring Boot basada en Java, organizada para facilitar la escalabilidad, mantenibilidad y claridad del código.

## Arquitectura del Proyecto

### Tipo de Arquitectura: Monolito Modular

La aplicación está diseñada bajo el patrón de monolito modular. Esto significa que todo el código reside en una única base de código y se despliega como una sola aplicación (JAR), pero internamente está organizado en módulos que separan los distintos dominios y responsabilidades. Esta arquitectura permite una alta cohesión interna y facilita la evolución futura hacia microservicios si fuera necesario.

#### Características principales:
- **Un solo punto de entrada:** `EurekaApplication.java` inicia toda la aplicación.
- **Separación por dominios:** Cada carpeta dentro de `src/main/java/com/example/eureka/` representa un dominio o responsabilidad (ejemplo: `auth`, `entrepreneurship`, `notificacion`).
- **Módulos internos:** Los módulos contienen subcarpetas para controladores, servicios, repositorios, DTOs, mappers, excepciones, utilidades, etc., siguiendo buenas prácticas de arquitectura limpia y separación de responsabilidades.
- **Comunicación directa:** Los módulos pueden interactuar entre sí mediante llamadas directas, lo que simplifica la integración y el desarrollo.
- **Despliegue único:** Toda la funcionalidad se empaqueta y despliega como una sola aplicación.

#### Ventajas:
- Facilidad de desarrollo y despliegue.
- Organización clara y mantenible.
- Permite escalar y refactorizar hacia microservicios en el futuro.

#### Desventajas:
- Si el proyecto crece mucho, puede volverse más difícil de mantener sin una correcta modularización.
- Todos los módulos comparten el mismo ciclo de vida y recursos.

## Estructura General

```
├── compose.yaml
├── Dockerfile
├── eureka-emprende.iml
├── mvnw / mvnw.cmd
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── eureka/
│   │   │               ├── EurekaApplication.java
│   │   │               ├── auth/
│   │   │               ├── config/
│   │   │               ├── domain/
│   │   │               ├── entrepreneurship/
│   │   │               ├── exception/
│   │   │               ├── general/
│   │   │               ├── infrastructure/
│   │   │               ├── notificacion/
│   │   │               ├── security/
│   │   │               └── shared/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/
│               └── example/
│                   └── eureka/
│                       └── EurekaApplicationTests.java
└── target/
```

## Explicación de Carpetas y Archivos

- **compose.yaml / Dockerfile**: Archivos para la configuración y despliegue de la aplicación en contenedores Docker y orquestación con Docker Compose.
- **pom.xml**: Archivo de configuración de Maven, define dependencias y plugins del proyecto.
- **mvnw / mvnw.cmd**: Wrapper de Maven para ejecutar comandos sin requerir Maven instalado globalmente.
- **eureka-emprende.iml**: Archivo de configuración del proyecto para el IDE (IntelliJ).

### src/main/java/com/example/eureka/
Carpeta principal del código fuente Java. Organizada por dominios y responsabilidades:

- **EurekaApplication.java**: Clase principal que arranca la aplicación Spring Boot.
- **auth/**: Lógica de autenticación y autorización (servicios, controladores, DTOs, repositorios).
- **config/**: Configuraciones globales (seguridad, Jackson, AWS, etc.).
- **domain/**: Modelos de dominio, enums y entidades principales.
- **entrepreneurship/**: Funcionalidad relacionada con emprendimientos (controladores, servicios, repositorios, mappers, etc.).
- **exception/**: Manejo de excepciones personalizadas y globales.
- **general/**: Funcionalidad general reutilizable en el proyecto.
- **infrastructure/**: Integraciones con servicios externos (ej. almacenamiento, AWS S3).
- **notificacion/**: Lógica de notificaciones (servicios, DTOs, repositorios).
- **security/**: Seguridad de la aplicación (JWT, filtros, configuración).
- **shared/**: Utilidades y componentes compartidos entre módulos.

### src/main/resources/
Archivos de configuración y recursos estáticos:
- **application.properties**: Configuración de la aplicación (puertos, credenciales, etc.).

### src/test/java/com/example/eureka/
Pruebas unitarias y de integración:
- **EurekaApplicationTests.java**: Pruebas de la clase principal y configuración.

### target/
Carpeta generada por Maven con los artefactos compilados, clases, reportes y archivos temporales.

---

Esta estructura permite separar claramente las responsabilidades, facilita el trabajo en equipo y la escalabilidad del proyecto. Cada carpeta agrupa componentes relacionados, siguiendo buenas prácticas de desarrollo en Java y Spring Boot.
