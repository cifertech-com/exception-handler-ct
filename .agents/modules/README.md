# Arquitectura Modular de ExceptionHandlerCT

`ExceptionHandlerCT` es una solución diseñada para estandarizar el manejo de errores en microservicios basados en **Java 21** y **Quarkus**. El proyecto adopta una arquitectura modular desacoplada mediante Maven Multi-Module, separando la definición de dominios de error de la capa de integración con el framework.

---

## 🏛️ Estructura de Módulos

El repositorio se compone de dos módulos principales con responsabilidades estrictamente delimitadas:

```mermaid
flowchart TD
    App["Microservicio Quarkus (Consumidor)"]
    QHandler["quarkus-exceptions-handler (Capa de Integración)"]
    Core["core-exceptions (Capa de Dominio Base)"]

    App -->|Depende de| QHandler
    QHandler -->|Depende transitivamente de| Core

    classDef core fill:#e1f5fe,stroke:#0288d1,stroke-width:2px;
    classDef handler fill:#e8f5e9,stroke:#388e3c,stroke-width:2px;
    classDef app fill:#fff3e0,stroke:#f57c00,stroke-width:2px;

    class Core core;
    class QHandler handler;
    class App app;
```

---

## 📚 Índice de Documentación de Módulos

| Módulo | Documentación Detallada | Responsabilidad Principal | Dependencias Externas |
|---|---|---|---|
| [`core-exceptions`](file:///home/yacson/Proyectos/cifertech/ExcepptionHandlerCT/ExceptionHandlerCT/.agents/modules/core-exceptions.md) | [Ver core-exceptions.md](file:///home/yacson/Proyectos/cifertech/ExcepptionHandlerCT/ExceptionHandlerCT/.agents/modules/core-exceptions.md) | Catálogo de excepciones HTTP (4xx y 5xx), códigos de estado y excepciones base. | **Ninguna** (Java 21 Puro) |
| [`quarkus-exceptions-handler`](file:///home/yacson/Proyectos/cifertech/ExcepptionHandlerCT/ExceptionHandlerCT/.agents/modules/quarkus-exceptions-handler.md) | [Ver quarkus-exceptions-handler.md](file:///home/yacson/Proyectos/cifertech/ExcepptionHandlerCT/ExceptionHandlerCT/.agents/modules/quarkus-exceptions-handler.md) | Interceptores `@Provider` (`ExceptionMapper`), indexación Jandex y serialización JSON. | Jakarta REST, Jakarta Validation, MicroProfile Config |

---

## 🔄 Flujo Global de Intercepción

Cuando un microservicio Quarkus incluye `quarkus-exceptions-handler`, Quarkus detecta automáticamente los mappers gracias al índice Jandex empaquetado en el JAR.

```mermaid
flowchart LR
    subgraph CoreDomain ["1. Dominio de Aplicación"]
        Service["Servicio / Recurso REST"]
        ThrowEx["Lanza Exception"]
        Service --> ThrowEx
    end

    subgraph Interception ["2. Quarkus & Jandex"]
        Router{"¿Qué tipo de excepción?"}
        ThrowEx --> Router
    end

    subgraph HandlerModule ["3. quarkus-exceptions-handler"]
        ApiMap["ExceptionHandler (ApiException)"]
        ValMap["ValidationExceptionHandler (ConstraintViolationException)"]
        GlobMap["ExceptionGlobalHandler (Throwable)"]

        Router -->|Hereda de ApiException| ApiMap
        Router -->|Violación Bean Validation| ValMap
        Router -->|Cualquier otro error / Runtime| GlobMap
    end

    subgraph ClientResponse ["4. Respuesta HTTP"]
        ResStatus["Status Code HTTP + Payload JSON"]
        ApiMap --> ResStatus
        ValMap --> ResStatus
        GlobMap --> ResStatus
    end
```

---

## 💡 Ventajas de este Diseño

1. **Agnosticismo del Núcleo (`core-exceptions`)**:
   - Puede ser utilizado en cualquier proyecto Java (Spring Boot, Micronaut, bibliotecas internas CLI) sin arrastrar Quarkus ni dependencias de frameworks.
2. **Auto-descubrimiento en Quarkus**:
   - Gracias a `jandex-maven-plugin`, no se requiere registrar manualmente los proveedores en `beans.xml` ni añadir clases al scanner de CDI.
3. **Contrato de Respuesta Homogéneo**:
   - Todos los microservicios de la organización devuelven una estructura JSON coherente para errores 4xx y 5xx.
