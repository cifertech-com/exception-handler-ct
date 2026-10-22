# Public API & Provider Contracts: ExceptionHandlerCT 2.0.0

**Feature**: `001-upgrade-java25-quarkus`  
**Date**: 2026-10-22  
**Target Runtime**: Java 25 / Quarkus 3.40.1.1 LTS  

---

## 1. Domain Exception Contract (`core-exceptions`)

### Package: `com.cifertech.exceptionhandler.exceptions`

```java
public class ApiException extends RuntimeException {
    public ApiException(HttpCode httpCode, Object details);
    public HttpCode getHttpCode();
    public Object getDetails();
}
```

#### Consumer Usage Example
```java
throw new NotFound("Resource with identifier 42 not found");
```

---

## 2. JAX-RS Provider Interface Contracts (`quarkus-exceptions-handler`)

### Package: `com.cifertech.exceptionhandler`

All mappers implement `jakarta.ws.rs.ext.ExceptionMapper<T>` and are annotated with `@jakarta.ws.rs.ext.Provider`.

### 2.1 `ExceptionHandler` (Domain Mapper)
- **Signature**: `public class ExceptionHandler implements ExceptionMapper<ApiException>`
- **HTTP Output**: Status mapped from `ApiException.getHttpCode().getCode()`.
- **Response Entity**:
  ```json
  // When detail is provided:
  {
    "error": "Resource not found",
    "timestamp": "2026-10-22T18:00:00Z"
  }
  // When detail is null:
  {
    "message": "Oops, something unexpected happens"
  }
  ```

### 2.2 `ValidationExceptionHandler` (Bean Validation Mapper)
- **Signature**: `public class ValidationExceptionHandler implements ExceptionMapper<ConstraintViolationException>`
- **HTTP Output**: `400 Bad Request`
- **Response Entity**: Flat Map of field names to error messages:
  ```json
  {
    "email": "must be a well-formed email address",
    "age": "must be greater than or equal to 18"
  }
  ```

### 2.3 `ExceptionGlobalHandler` (Fatal / Uncaught Fallback Mapper)
- **Signature**: `public class ExceptionGlobalHandler implements ExceptionMapper<Throwable>`
- **HTTP Output**: `500 Internal Server Error`
- **Response Entity**: Sanitized JSON message:
  ```json
  {
    "message": "Oops, something unexpected happens"
  }
  ```
- **Logging Guarantee**: The complete cause and stack trace is logged at `Level.SEVERE` to server logs and is never transmitted in the HTTP body.
