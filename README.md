# ExceptionHandlerCT

ExceptionHandlerCT is a modular, lightweight, and highly standardized global exception-handling library designed specifically for **Java 21** and **Quarkus** applications. 

By utilizing this library, you avoid repetitive try-catch blocks and manual HTTP response formatting across your microservices. Instead, you can simply throw a domain-specific exception (e.g., `NotFound`, `BadRequest`), and the library will automatically intercept it and return a standardized, clean JSON error response to the client.

## 📦 Features
- **Standardized JSON Responses**: Consistent error formatting across all your Quarkus microservices.
- **Pre-built HTTP Exceptions**: Includes a comprehensive suite of 4xx and 5xx exceptions out-of-the-box (e.g., `BadRequest`, `Conflict`, `NotFound`, `InternalServerError`).
- **Jandex Indexed**: The `quarkus-handler` module is pre-indexed with Jandex, meaning Quarkus automatically discovers the `@Provider` without additional configuration.
- **Modular Design**: Split into a `core` module (pure Java exceptions) and a `quarkus-handler` module (Quarkus/Jakarta REST specific mappers).

---

## 🚀 Installation (Local Development)

Currently, the library is meant to be installed in your local Maven repository (`~/.m2/repository`). 

To install it locally, clone or download this repository, open a terminal in the root directory, and run:

```bash
mvn clean install -DskipTests
```

---

## 🛠️ How to Import into a Quarkus Project

Once the library is installed locally, you can easily integrate it into any Quarkus microservice by adding a single dependency to your target project's `pom.xml`.

Add the `quarkus-handler` module to your dependencies (the `core` module will be imported automatically as a transitive dependency):

```xml
<dependency>
    <groupId>com.cifertech.exceptionhandler</groupId>
    <artifactId>quarkus-handler</artifactId>
    <version>1.0.0</version>
</dependency>
```

*(Note: Ensure your target project is using Java 21, as this library is compiled with `maven.compiler.release` set to 21).*

---

## 💡 Usage Examples

Using the library is extremely straightforward. You do not need to register the Exception Mapper manually. Just throw the specific exceptions provided by the library from your Services or Controllers (Resources).

### Example 1: Throwing a 404 Not Found

```java
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import com.cifertech.exceptionhandler.exceptions._4xx.NotFound;

@Path("/users")
public class UserResource {

    @GET
    @Path("/{id}")
    public User getUser(@PathParam("id") Long id) {
        User user = database.findById(id);
        
        if (user == null) {
            // Simply throw the exception, the library handles the rest!
            throw new NotFound("User with ID " + id + " was not found in the system.");
        }
        
        return user;
    }
}
```

### Example 2: Throwing a 400 Bad Request

```java
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import com.cifertech.exceptionhandler.exceptions._4xx.BadRequest;

@Path("/auth")
public class AuthResource {

    @POST
    @Path("/login")
    public String login(LoginRequest request) {
        if (request.getPassword().length() < 8) {
            throw new BadRequest("Password must be at least 8 characters long.");
        }
        return "Token...";
    }
}
```

### Available Exceptions
The library provides dedicated exception classes for almost all standard HTTP status codes. Some of the most commonly used include:

- `BadRequest` (400)
- `Unauthorized` (401)
- `Forbidden` (403)
- `NotFound` (404)
- `Conflict` (409)
- `UnprocessableEntity` (422)
- `InternalServerError` (500)
- `BadGateway` (502)

You can explore the `com.cifertech.exceptionhandler.exceptions` package for the full list.

---

## ⚙️ Configuration

By default, if an unknown exception is thrown or no specific detail is provided, the library will return the message `"Oops, something unexpected happens"`. 

You can customize this default message by adding the following property to your target application's `application.properties` (or `.yml`):

```properties
exception.handler.default-message=A custom unexpected error message here
```

---

## 🔍 How It Works Under the Hood

The library contains a class annotated with `@Provider` and `ExceptionMapper<ApiException>`. 
When your application throws any exception that inherits from `ApiException` (which all the `_4xx` and `_5xx` classes do), the Quarkus REST (RESTEasy) engine intercepts it before it reaches the client.

The `ExceptionGlobalHandler` then extracts the HTTP status code, the error message, and the timestamp from the thrown exception, wrapping them into a standardized JSON payload and returning it to the client with the correct HTTP headers.
