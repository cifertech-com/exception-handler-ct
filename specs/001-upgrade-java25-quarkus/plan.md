# Implementation Plan: Upgrade Java Baseline to 25, Quarkus to 3.40.1.1, and Dependency Modernization

**Branch**: `001-upgrade-java25-quarkus` | **Date**: 2026-10-22 | **Spec**: [spec.md](spec.md)

**Input**: Feature specification from [`specs/001-upgrade-java25-quarkus/spec.md`](spec.md)

---

## Summary

Modernize `ExceptionHandlerCT` to Java 25 LTS bytecode standards and align the Quarkus integration module with Quarkus 3.40.1.1 LTS emergency security maintenance release to remediate known vulnerabilities. The multi-module POM hierarchy is upgraded with `io.quarkus:quarkus-bom:3.40.1.1` in `<dependencyManagement>`, build/test tooling (Surefire, Jandex, License plugins) is updated for Java 25 compatibility, CI/CD GitHub Actions workflows are upgraded to JDK 25, and the project version is incremented to `2.0.0` following Semantic Versioning rules while strictly preserving the Zero-Dependency Core invariant.

---

## Technical Context

**Language/Version**: Java 25 (Standard bytecode release target via `--release 25`)  
**Primary Dependencies**: `io.quarkus:quarkus-bom:3.40.1.1` (in `<dependencyManagement>`), Jakarta REST 3.1 (`provided`), Jakarta Validation 3.0 (`provided`), MicroProfile Config 3.0 (`provided`)  
**Storage**: N/A (Stateless library)  
**Testing**: JUnit 5 (`junit-jupiter-engine`), Mockito (`mockito-core`, `mockito-junit-jupiter`), RESTEasy test core (`resteasy-core`), Maven Surefire Plugin `3.5.2`  
**Target Platform**: Java 25 runtime / Quarkus 3.40.1.1 LTS microservices running in Linux/Containers/JVM  
**Project Type**: Multi-module Java Maven library (`core-exceptions`, `quarkus-exceptions-handler`)  
**Performance Goals**: Zero runtime overhead on exception paths; instant static exception mapping  
**Constraints**: Zero runtime dependencies in `core-exceptions`; 100% backward compatibility of public APIs and response payload schemas; maximum 3 parameters per method; clean code in lambdas  
**Scale/Scope**: 2 submodules, 40+ exception classes, 3 JAX-RS `@Provider` exception mappers, 2 GitHub Actions workflows  

---

## Constitution Check

*GATE: Pre-research and post-design verification.*

| Principle | Requirement | Plan Status |
|---|---|:---:|
| **I. Zero-Dependency Core** | `core-exceptions` must contain 0 third-party runtime dependencies. | **PASS**: Only standard Java 25 APIs used; no BOM or framework dependencies in core. |
| **II. Clean Code in Lambdas** | Zero logic/instantiation inside stream or Optional lambdas; method reference delegation. | **PASS**: All mappers maintain clean delegation without inline logic. |
| **III. Method Signature Discipline** | Maximum 3 parameters per public method in services, handlers, and mappers. | **PASS**: Exception mapper signatures (`toResponse(E exception)`) comply (1 parameter). |
| **IV. Static Final Utility Mappers** | Utility/mapper classes declared `public final` with `private` constructors. | **PASS**: Preserved across all helper classes. |
| **V. Secure 3-Tier Hierarchy** | Deterministic handling: Domain (`ApiException`), Validation (`ConstraintViolationException`), Fatal (`Throwable`) with zero information leakage. | **PASS**: All 3 tiers preserved with internal `Level.SEVERE` logging and sanitized error payloads. |
| **VI. Zero-Configuration Contract** | Jandex index (`META-INF/jandex.idx`) generated automatically; config externalized via MicroProfile Config. | **PASS**: `jandex-maven-plugin` updated to `3.2.7` to index Java 25 bytecode. |
| **VII. Synchronous Documentation & SemVer** | `.agents/modules/`, `README.md`, and `pom.xml` (`<revision>`) updated synchronously; SemVer major bump for Java 25. | **PASS**: Version set to `2.0.0`; docs scheduled for synchronous update. |

---

## Project Structure

### Documentation & Design Artifacts (this feature)

```text
specs/001-upgrade-java25-quarkus/
├── spec.md              # Feature specification
├── plan.md              # This implementation plan
├── research.md          # Technical research & decisions (Phase 0)
├── data-model.md        # Domain entities, payloads & config model (Phase 1)
├── quickstart.md        # Build & verification instructions (Phase 1)
├── contracts/           # Interface & POM contracts (Phase 1)
│   ├── api-contracts.md
│   └── maven-contracts.md
├── checklists/          # Quality checklist
│   └── requirements.md
└── tasks.md             # Generated in Phase 2 via /speckit-tasks
```

### Source Code (repository root)

```text
.
├── pom.xml                                      # Parent POM (revision 2.0.0, Java 25, quarkus-bom 3.40.1.1)
├── core-exceptions/
│   ├── pom.xml                                  # Java 25 zero-dependency core module
│   └── src/main/java/com/cifertech/exceptionhandler/exceptions/
│       ├── ApiException.java
│       ├── HttpCode.java
│       ├── _4xx/*.java
│       └── _5xx/*.java
├── quarkus-exceptions-handler/
│   ├── pom.xml                                  # Quarkus integration module (Jandex 3.2.7, Surefire 3.5.2)
│   ├── src/main/java/com/cifertech/exceptionhandler/
│   │   ├── ExceptionHandler.java
│   │   ├── ValidationExceptionHandler.java
│   │   └── ExceptionGlobalHandler.java
│   └── src/test/java/com/cifertech/exceptionhandler/
│       ├── ExceptionHandlerTest.java
│       ├── ValidationExceptionHandlerTest.java
│       └── ExceptionGlobalHandlerTest.java
├── .github/workflows/
│   ├── pr-checks.yml                            # GitHub Actions CI with setup-java@v4 (JDK 25)
│   └── publish.yml                              # Maven Central deployment with setup-java@v4 (JDK 25)
├── .agents/modules/
│   ├── core-exceptions.md                       # Module documentation (updated to 2.0.0 / Java 25)
│   ├── quarkus-exceptions-handler.md            # Module documentation (updated to Quarkus 3.40.1.1 / Java 25)
│   └── README.md                                # Modules index (version sync)
└── README.md                                    # Root repository README (updated usage & versions)
```

**Structure Decision**: Multi-module Maven repository preserving clean modular architecture: parent POM orchestrates versioning and dependency governance, `core-exceptions` remains framework-agnostic pure Java, and `quarkus-exceptions-handler` exposes the Quarkus integration adapter.

---

## Complexity Tracking

> **No Constitution violations detected. All gates pass cleanly.**
