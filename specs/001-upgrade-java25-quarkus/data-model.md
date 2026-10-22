# Data Model & Entity Specifications: Java 25 & Quarkus 3.40.1.1 Upgrade

**Feature**: `001-upgrade-java25-quarkus`  
**Date**: 2026-10-22  
**Status**: Completed  

---

## 1. Domain Entities & Exception Hierarchy (`core-exceptions`)

The core module provides standard exception entities compiled strictly to Java 25 with zero third-party dependencies.

### 1.1 `ApiException` (Root Domain Exception)
- **Class**: `com.cifertech.exceptionhandler.exceptions.ApiException`
- **Extends**: `java.lang.RuntimeException`
- **Fields**:
  - `httpCode`: `com.cifertech.exceptionhandler.exceptions.HttpCode` (Required, transient enum defining the HTTP status representation)
  - `detail`: `java.lang.Object` (Optional, transient structured business payload or error message)
- **Methods**:
  - `getHttpCode()`: Returns `HttpCode`
  - `getDetails()`: Returns `Object`

### 1.2 `HttpCode` (HTTP Status Enumeration)
- **Class**: `com.cifertech.exceptionhandler.exceptions.HttpCode`
- **Fields**:
  - `code`: `int` (HTTP status number, e.g., 400, 404, 500)
  - `description`: `String` (Canonical standard description)
- **Hierarchy Categorization**:
  - 4xx Client Exceptions (e.g., `BadRequest`, `NotFound`, `Unauthorized`, `Conflict`, `UnprocessableEntity`)
  - 5xx Server Exceptions (e.g., `InternalServerError`, `ServiceUnavailable`, `GatewayTimeout`)

---

## 2. Integration Adapters & Payload Mappings (`quarkus-exceptions-handler`)

The integration module converts exceptions into standardized JAX-RS / Jakarta REST HTTP responses for Quarkus 3.40.1.1.

### 2.1 3-Tier Exception Mapping Payloads

| Tier | Source Exception | HTTP Status | Response Entity Payload | Security / Sanitization Rule |
|---|---|:---:|---|---|
| **Tier 1: Domain Errors** | `ApiException` (and subclasses) | Dynamic (`exception.getHttpCode().getCode()`) | Structured payload from `exception.getDetails()` or fallback `Map.of("message", configMessage)` | Logged internally at `Level.SEVERE`. Details returned if explicitly supplied by business domain. |
| **Tier 2: Validation Errors** | `ConstraintViolationException` | `400 Bad Request` | `Map<String, String>` (`fieldName -> violationMessage`) | Extracts leaf node property path and message; zero stack trace exposure. |
| **Tier 3: Fatal Errors** | `Throwable` (any uncaught runtime/checked error) | `500 Internal Server Error` | `Map.of("message", configMessage)` | Strictly sanitized generic fallback message; internal full stack trace logged via `java.util.logging.Logger` at `Level.SEVERE`. |

### 2.2 Configuration Model (MicroProfile Config)
- **Property**: `exception.handler.default-message`
  - **Type**: `String`
  - **Default**: `"Oops, something unexpected happens"`
  - **Injection**: `@ConfigProperty(name = "exception.handler.default-message", defaultValue = "Oops, something unexpected happens")`
  - **Usage**: Used in `ExceptionHandler` and `ExceptionGlobalHandler` as the sanitized fallback text.

---

## 3. Maven Multi-Module Build Contract

### 3.1 Properties & Revision Schema
- `<revision>`: `2.0.0`
- `<maven.compiler.source>`: `25`
- `<maven.compiler.target>`: `25`
- `<maven.compiler.release>`: `25`
- `<project.build.sourceEncoding>`: `UTF-8`

### 3.2 Quarkus Ecosystem Alignment
- `<dependencyManagement>` imports `io.quarkus:quarkus-bom:3.40.1.1` (`pom`, `import`).
- Direct dependencies inherit versions from BOM:
  - `jakarta.ws.rs:jakarta.ws.rs-api` (`provided`)
  - `jakarta.validation:jakarta.validation-api` (`provided`)
  - `org.eclipse.microprofile.config:microprofile-config-api` (`provided`)
  - `org.junit.jupiter:junit-jupiter-engine` (`test`)
  - `org.mockito:mockito-core` / `mockito-junit-jupiter` (`test`)
  - `org.jboss.resteasy:resteasy-core` (`test`)
