# ExceptionHandlerCT Constitution

This document defines the foundational engineering, architectural, and clean-code principles governing the `ExceptionHandlerCT` project and any future module or feature development guided by Spec Kit.

---

## Core Principles

### I. Modular Inward Dependency & Zero-Dependency Core (NON-NEGOTIABLE)
- **Strict Inward Flow:** Dependencies must strictly flow inwards: `Consuming Application → Integration Adapter (quarkus-exceptions-handler) → Core Domain (core-exceptions)`. The core module must **NEVER** depend on integration modules, application frameworks, or web transport layers.
- **Zero-Dependency Core:** `core-exceptions` must remain 100% pure standard Java (Java 21) with zero external runtime dependencies. No Quarkus, Spring, Jakarta REST (`jakarta.ws.rs.*`), Bean Validation, Jackson, or external logging libraries are permitted in the core.
- **Transitive Inheritance:** Consuming services import only the integration adapter (`quarkus-exceptions-handler`) and inherit `core-exceptions` transitively.

### II. Clean Code in Lambdas & Stream Pipelines
- **Zero Logic/Instantiation in Lambdas:** Never place business logic, data mapping, complex builders, or inline exception instantiation inside lambda expressions (`.orElseThrow(...)`, `.map(...)`, `.filter(...)`).
- **Method Reference Delegation:** Lambdas must delegate via method references or to descriptive private methods (e.g. `.orElseThrow(this::productNotFoundException)` instead of inline `new NotFoundException(...)`).
- **Private Method Extraction:** Complex transformations and operational steps must be extracted into single-responsibility private methods that clearly state business intent (`validatePrerequisites`, `buildErrorPayload`).

### III. Method Signature Discipline (Maximum 3 Parameters)
- **Parameter Limit:** No public method in use cases, services, handlers, or adapters may exceed 3 parameters.
- **Parameter Objects & Commands:** When an operation requires more than 3 input values, encapsulate them into a dedicated domain command or parameter object (e.g., `ErrorContext`, `ExceptionDetails`).
- **Typed Filter Enums:** When filtering or querying by criteria sharing primitive types, use a type-safe discriminator `enum` rather than overloaded primitive arguments.

### IV. Static Final Utility Mappers
- **Stateless Static Mapping:** Utility and mapper classes must be declared `public final` with a `private` constructor.
- **No Injected Bean State:** Mappers must expose pure static methods only. Do not inject them as stateful beans or rely on runtime bytecode generation when manual static mapping is feasible and transparent.

### V. Secure 3-Tier Exception Handling & Information Leakage Prevention
- **3-Tier Hierarchy:** The framework integration layer must maintain a deterministic 3-tier hierarchy:
  1. **Domain Errors (`ApiException`):** Handled dynamically by `ExceptionHandler` returning the specific HTTP status code and structured business payload.
  2. **Validation Errors (`ConstraintViolationException`):** Handled by `ValidationExceptionHandler` returning HTTP 400 with a clean, flat `Map<String, String>` (`field -> error message`).
  3. **Fatal / Uncaught Errors (`Throwable`):** Handled by `ExceptionGlobalHandler` returning HTTP 500 with a sanitized, generic message.
- **Zero Information Leakage:** Stack traces, internal hostnames, or SQL fragments must **NEVER** be leaked in HTTP response bodies to clients. Stack traces must be logged internally at `Level.SEVERE` at the integration boundary.

### VI. Zero-Configuration Consumer Contract (Jandex & MicroProfile Config)
- **Jandex Indexing:** All integration modules targeting Quarkus must run `jandex-maven-plugin` to embed a Jandex index (`META-INF/jandex.idx`), guaranteeing automatic `@Provider` discovery without manual consumer registration.
- **Externalized Configuration:** Fallback messages and error text must be configurable via MicroProfile Config (`@ConfigProperty(name = "exception.handler.default-message", ...)`) with production-ready defaults.

### VII. Continuous Documentation & Version Synchronization (NON-NEGOTIABLE)
- **Module Documentation Maintenance (`.agents/modules/`):** Whenever a modification, refactoring, or new development is performed, the documentation files in [`.agents/modules/`](file:///home/yacson/Proyectos/cifertech/ExcepptionHandlerCT/ExceptionHandlerCT/.agents/modules) must be synchronously updated. If a new module is created, a dedicated `.md` file with architecture diagrams, responsibilities, and class contracts must be added immediately, and [`.agents/modules/README.md`](file:///home/yacson/Proyectos/cifertech/ExcepptionHandlerCT/ExceptionHandlerCT/.agents/modules/README.md) must be updated to index it.
- **Root README Synchronization:** Whenever changes impact public APIs, configuration properties, usage patterns, or module dependencies, the root [`README.md`](file:///home/yacson/Proyectos/cifertech/ExcepptionHandlerCT/ExceptionHandlerCT/README.md) must be updated to reflect the current state and accurate usage examples.
- **Version Bump in `pom.xml`:** Every functional change, feature addition, or bug fix requires an explicit version bump of `<revision>` in the root [`pom.xml`](file:///home/yacson/Proyectos/cifertech/ExcepptionHandlerCT/ExceptionHandlerCT/pom.xml) following Semantic Versioning (SemVer: `MAJOR.MINOR.PATCH`). A change is never considered complete if its version remains unincremented.

---

## Architectural & Technology Standards

### 1. Technology Stack
- **Language Level:** Java 21 (`maven.compiler.source` and `maven.compiler.target` set to 21).
- **Runtime Target:** Quarkus 3.x / Jakarta REST 3.1 / Jakarta Validation 3.0.
- **Build System:** Apache Maven multi-module with CI-friendly revision token (`${revision}`) and `flatten-maven-plugin`.

### 2. Dependency Scope Governance
- Web and framework APIs in integration modules (`jakarta.ws.rs-api`, `jakarta.validation-api`, `microprofile-config-api`) must strictly use `<scope>provided</scope>` so they are supplied by the runtime container without classpath collisions.
- Testing dependencies (`junit-jupiter`, `mockito`, `resteasy-core`) must strictly use `<scope>test</scope>`.

---

## Development Workflow & Quality Gates

### 1. Quality Gates (PR Checks)
- **Test Suite:** All unit tests must pass (`mvn -B clean test`).
- **License Headers:** Source files must comply with Apache 2.0 license headers (`mvn license:check`).
- **Security Scanning:** Automated CodeQL analysis must run on PRs targeting `main` and `develop`.
- **Documentation & Version Gate:** PRs and tasks must be verified to ensure `.agents/modules/`, `README.md`, and `pom.xml` (`<revision>`) have been properly updated.

### 2. Design Artifact Governance
- All future feature specifications, implementation plans, and task breakdowns generated via Spec Kit (`speckit-specify`, `speckit-plan`, `speckit-tasks`) must verify conformance against the principles in this constitution and the companion reference documents:
  - [`.agents/java-rules.md`](file:///home/yacson/Proyectos/cifertech/ExcepptionHandlerCT/ExceptionHandlerCT/.agents/java-rules.md)
  - [`.agents/architecture-rules.md`](file:///home/yacson/Proyectos/cifertech/ExcepptionHandlerCT/ExceptionHandlerCT/.agents/architecture-rules.md)

---

## Governance

- **Supremacy:** This Constitution is the authoritative architectural and code standard for `ExceptionHandlerCT`. It supersedes informal conventions or ad-hoc practices.
- **Amendment Procedure:** Any change or deprecation of core principles requires a documentation update, version bump (following semantic versioning), and review approval.
- **Enforcement:** Code reviews and automated CI checks must reject changes that violate the Zero-Dependency Core, the 3-tier exception mapping hierarchy, or the clean code lambda rules.

**Version**: 1.1.0 | **Ratified**: 2026-10-22 | **Last Amended**: 2026-10-22
