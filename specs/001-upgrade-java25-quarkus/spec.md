# Feature Specification: Upgrade Java Baseline to 25, Quarkus to 3.40.1.1, and Dependency Modernization

**Feature Branch**: `001-upgrade-java25-quarkus`

**Created**: 2026-10-22

**Status**: Draft

**Input**: User description: "se requiere actualizar las versiones de Java a la versión 25 tambien la version de quarkus a la version 3.40.1.1 debido a vulnerabilidades encontradas. tambien revisar las demas dependencias y ver que podemos actualizar."

## Clarifications

### Session 2026-10-22

- Q: ¿Cómo debe incrementarse la versión de la librería (`revision`) en el `pom.xml`, considerando que elevar el requisito de bytecode a Java 25 impide su uso a los consumidores que sigan en Java 21? → A: Incremento a versión mayor (`2.0.0`), reflejando el cambio de compatibilidad mínima de JDK bajo SemVer.
- Q: ¿Se debe incorporar `io.quarkus:quarkus-bom:3.40.1.1` en la sección `<dependencyManagement>` del POM para gobernar las versiones de dependencias, o se mantendrán las versiones gestionadas explícita e individualmente? → A: Importar `io.quarkus:quarkus-bom:3.40.1.1` en `<dependencyManagement>` del POM para alinear automáticamente todas las dependencias del ecosistema Quarkus y mitigar CVEs conocidas.
- Q: ¿Debe incluirse en el alcance de esta funcionalidad la actualización de los flujos de trabajo de GitHub Actions (`pr-checks.yml` y `publish.yml`) para configurar JDK 25? → A: Actualizar los flujos de GitHub Actions (`pr-checks.yml` y `publish.yml`) para usar JDK 25 (distribución temurin) garantizando la continuidad de CI/CD.

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Secure Runtime Execution on Java 25 and Quarkus 3.40.1.1 (Priority: P1)

As a backend engineer building or upgrading microservices on Quarkus 3.40.1.1 and Java 25, I want to use `ExceptionHandlerCT` without encountering bytecode incompatibility errors, classloader issues, or security vulnerability alerts, so that my applications maintain high security standards and benefit from the latest LTS runtime stability.

**Why this priority**: Security vulnerabilities in legacy dependencies pose an immediate risk in production environments. Upgrading the baseline runtime to Java 25 and aligning with Quarkus 3.40.1.1 LTS resolves security advisories and ensures continued vendor support.

**Independent Test**: Build and run `ExceptionHandlerCT` test suites using a Java 25 environment with Quarkus 3.40.1.1 target alignments, verifying that all exception mappers, validation handlers, and global exception handlers operate cleanly and produce expected HTTP responses.

**Acceptance Scenarios**:

1. **Given** a service running on Java 25 with Quarkus 3.40.1.1 runtime, **When** `ExceptionHandlerCT` (version incremented) is imported, **Then** all `@Provider` exception mappers are auto-discovered via Jandex and active without runtime bytecode failure.
2. **Given** an application throwing a domain `ApiException` or validation exception, **When** the exception occurs under Java 25 runtime, **Then** the appropriate HTTP status code and standardized, sanitized response payload are returned.

---

### User Story 2 - Comprehensive Dependency Vulnerability Remediation (Priority: P2)

As a security auditor and maintainer, I want all build plugins, test frameworks, and transitively resolved runtime dependencies audited and updated to current, vulnerability-free releases, so that no critical or high CVEs remain in the codebase.

**Why this priority**: Outdated testing and build dependencies introduce supply chain risks, CI security alerts, and false positives in automated compliance pipelines.

**Independent Test**: Execute security vulnerability scanning (e.g., dependency check / CodeQL / Maven audit) and verify zero reported high or critical severity CVEs.

**Acceptance Scenarios**:

1. **Given** the upgraded dependency manifest in Maven, **When** dependency audit and vulnerability scanning tools analyze the project, **Then** zero critical or high severity CVEs are reported.
2. **Given** test framework dependencies (JUnit 5, Mockito, RESTEasy test core) and build plugins (Surefire, Jandex, License, Flatten), **When** building with Maven, **Then** all plugins execute cleanly without deprecation warnings or runtime crashes.

---

### User Story 3 - Backward Compatibility & Architectural Invariant Preservation (Priority: P3)

As a developer maintaining existing consumer applications, I want the upgrade to preserve full backward compatibility of the public API and preserve the Zero-Dependency Core architecture, so that my existing code does not break when upgrading the library version.

**Why this priority**: Seamless adoption requires that consumers do not have to refactor existing domain exceptions or error structures when updating to the new library version.

**Independent Test**: Verify that existing public exception classes, mapper signatures, and configuration properties retain their exact contracts and that `core-exceptions` has zero third-party runtime dependencies.

**Acceptance Scenarios**:

1. **Given** an existing codebase using `core-exceptions`, **When** compiled against the updated library version, **Then** no compilation errors or binary incompatibilities occur.
2. **Given** the `core-exceptions` module, **When** inspecting its compiled artifact and POM, **Then** it contains zero third-party dependencies, conforming strictly to the project constitution.

---

### Edge Cases

- How does the system handle build environments where Java 25 compiler flags or plugin versions require newer Maven versions or updated Surefire configurations?
  - *Resolution*: Ensure build plugins (e.g., `maven-compiler-plugin`, `maven-surefire-plugin`, `flatten-maven-plugin`) are upgraded to versions with explicit Java 25 bytecode compatibility.
- How does the system handle potential changes or deprecations in Jakarta REST or MicroProfile Config specifications introduced in the Quarkus 3.40.x stream?
  - *Resolution*: Validate that method signatures used in `ExceptionHandler`, `ValidationExceptionHandler`, and `ExceptionGlobalHandler` adhere strictly to the provided API boundaries without relying on deprecated Quarkus-internal classes.
- How does the system prevent information leakage in uncaught fatal errors across newer JVM versions?
  - *Resolution*: Confirm that `ExceptionGlobalHandler` sanitizes all responses, logging stack traces internally at `Level.SEVERE` while returning only non-sensitive error messages to clients.

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: System MUST configure Java 25 as the compilation source and target baseline across the root project and all modules (`core-exceptions`, `quarkus-exceptions-handler`).
- **FR-002**: System MUST import `io.quarkus:quarkus-bom:3.40.1.1` in the `<dependencyManagement>` section of the root POM to govern and align the Quarkus integration module (`quarkus-exceptions-handler`) and its underlying Jakarta REST and MicroProfile specifications with the official LTS security-patched baseline.
- **FR-003**: System MUST update all provided and test-scoped dependencies (including Jakarta REST API, Jakarta Validation API, MicroProfile Config API, JUnit 5, Mockito, and RESTEasy test core) to stable, vulnerability-free releases compatible with Java 25 and Quarkus 3.40.1.1.
- **FR-004**: System MUST update all Maven build plugins (including `jandex-maven-plugin`, `maven-surefire-plugin`, `license-maven-plugin`, `flatten-maven-plugin`, `maven-source-plugin`, `maven-javadoc-plugin`) to versions supporting Java 25 bytecode processing.
- **FR-005**: `core-exceptions` MUST maintain its zero-dependency invariant, requiring only standard Java 25 language features with no external libraries.
- **FR-006**: The library MUST preserve full backward compatibility for all public classes, exceptions, and response formats defined in the 3-tier exception handling architecture.
- **FR-007**: System MUST increment the project `<revision>` version in `pom.xml` to `2.0.0` (Major version bump under Semantic Versioning) to reflect the breaking runtime baseline change to Java 25, and update module documentation (`.agents/modules/`) and root `README.md` to reflect the updated runtime baselines.
- **FR-008**: System MUST update all continuous integration and deployment pipelines (`.github/workflows/pr-checks.yml` and `.github/workflows/publish.yml`) to set up JDK 25 (`temurin` distribution), ensuring automated PR validation, CodeQL static analysis, license formatting, and Maven Central release workflows run cleanly.

### Key Entities *(include if feature involves data)*

- **Project Dependency Manifest**: Defines the build properties, compiler source/target bytecode version (Java 25), dependency scopes (`provided`, `test`), and plugin configuration across the multi-module POM hierarchy.
- **ExceptionHandler Configuration & Discovery Metadata**: Includes the compiled Jandex index (`META-INF/jandex.idx`) and MicroProfile Config property mappings (`exception.handler.default-message`) required for zero-configuration integration in Quarkus 3.40.1.1.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: 100% of unit tests across all modules pass cleanly when built against the Java 25 baseline.
- **SC-002**: Zero critical or high-severity CVE vulnerability findings identified in project dependencies and build plugins upon completion of the upgrade.
- **SC-003**: 100% backward binary and source compatibility maintained for all public exception classes and HTTP error payload structures.
- **SC-004**: Multi-module Maven build (`mvn clean package`) completes successfully with Jandex index generated and Apache 2.0 license headers validated.
- **SC-005**: All architectural documentation in `.agents/modules/` and root `README.md` are synchronized with the Java 25 and Quarkus 3.40.1.1 baselines.

## Assumptions

- The target runtime environment has Java 25 JDK available for compilation and deployment.
- No breaking changes are required in the public API contract of `ApiException`, `ErrorResponse`, or the three exception mappers.
- Quarkus 3.40.1.1 maintains compatibility with Jakarta REST 3.1 and Jakarta Validation 3.0 API boundaries.
- Build and test dependencies (e.g. Mockito, RESTEasy test runner) have official releases that run cleanly under Java 25.
