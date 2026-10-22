# Tasks: Upgrade Java Baseline to 25, Quarkus to 3.40.1.1, and Dependency Modernization

**Input**: Design documents from [`specs/001-upgrade-java25-quarkus/`](./)  
**Prerequisites**: [plan.md](plan.md), [spec.md](spec.md), [research.md](research.md), [data-model.md](data-model.md), [contracts/](contracts/), [quickstart.md](quickstart.md)  
**Organization**: Tasks are grouped by user story to enable independent implementation and testing of each story.  

---

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Establish build baseline, SemVer major bump, and CI/CD runtime alignment for Java 25

- [X] T001 Bump project revision to `2.0.0` and configure Java 25 compiler properties (`source=25`, `target=25`, `release=25`) in root `pom.xml`
- [X] T002 [P] Update GitHub Actions CI pipeline to configure JDK 25 (`temurin`) in `.github/workflows/pr-checks.yml`
- [X] T003 [P] Update GitHub Actions release workflow to configure JDK 25 (`temurin`) in `.github/workflows/publish.yml`

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Centralize dependency governance and align compiler tooling across all modules

**⚠️ CRITICAL**: Must complete before user story implementation begins

- [X] T004 Import `io.quarkus:quarkus-bom:3.40.1.1` (`pom`, `import`) within `<dependencyManagement>` in root `pom.xml`
- [X] T005 [P] Upgrade compiler and packaging plugin versions (`maven-compiler-plugin:3.13.0`, `license-maven-plugin:4.6`, `maven-javadoc-plugin:3.11.2`) in root `pom.xml`
- [X] T006 [P] Align Java 25 compiler source and target properties to `25` in `core-exceptions/pom.xml` while ensuring zero external dependencies are introduced

**Checkpoint**: Build foundation established and aligned across parent and core modules.

---

## Phase 3: User Story 1 - Secure Runtime Execution on Java 25 and Quarkus 3.40.1.1 (Priority: P1) 🎯 MVP

**Goal**: Enable seamless execution on Java 25 and Quarkus 3.40.1.1 LTS with automatic `@Provider` discovery and CVE remediation

**Independent Test**: Build `quarkus-exceptions-handler`, verify `META-INF/jandex.idx` presence, and confirm exception mappers compile against Quarkus 3.40.1.1 managed APIs.

### Implementation for User Story 1

- [X] T007 [US1] Remove hardcoded version tags for `jakarta.ws.rs-api`, `jakarta.validation-api`, and `microprofile-config-api` to inherit versions from `quarkus-bom:3.40.1.1` in `quarkus-exceptions-handler/pom.xml`
- [X] T008 [US1] Update Java compiler properties (`source=25`, `target=25`) in `quarkus-exceptions-handler/pom.xml`
- [X] T009 [US1] Configure `io.smallrye:jandex-maven-plugin:3.2.7` in `quarkus-exceptions-handler/pom.xml` for Java 25 class file indexing
- [X] T010 [US1] Verify that `ExceptionHandler.java`, `ValidationExceptionHandler.java`, and `ExceptionGlobalHandler.java` compile cleanly without deprecated API warnings against Quarkus 3.40.1.1 in `quarkus-exceptions-handler/src/main/java/com/cifertech/exceptionhandler/`

**Checkpoint**: User Story 1 (MVP) is fully functional; project compiles on Java 25 and produces Jandex index.

---

## Phase 4: User Story 2 - Comprehensive Dependency Vulnerability Remediation (Priority: P2)

**Goal**: Modernize test frameworks and test execution plugins to eliminate vulnerabilities and run cleanly under Java 25

**Independent Test**: Execute full Maven test suite via Surefire 3.5.2 and confirm all unit tests pass without reflection or security violations.

### Implementation for User Story 2

- [X] T011 [US2] Update testing dependencies (`junit-jupiter-engine`, `mockito-core`, `mockito-junit-jupiter`, `resteasy-core`) to inherit managed versions from `quarkus-bom` or update to Java 25-compatible releases in `quarkus-exceptions-handler/pom.xml`
- [X] T012 [US2] Upgrade `maven-surefire-plugin` to version `3.5.2` in `quarkus-exceptions-handler/pom.xml`
- [X] T013 [P] [US2] Validate and run unit tests for domain exception handling in `quarkus-exceptions-handler/src/test/java/com/cifertech/exceptionhandler/ExceptionHandlerTest.java`
- [X] T014 [P] [US2] Validate and run unit tests for validation exception handling in `quarkus-exceptions-handler/src/test/java/com/cifertech/exceptionhandler/ValidationExceptionHandlerTest.java`
- [X] T015 [P] [US2] Validate and run unit tests for global fallback exception handling in `quarkus-exceptions-handler/src/test/java/com/cifertech/exceptionhandler/ExceptionGlobalHandlerTest.java`

**Checkpoint**: All test suites pass cleanly on updated test dependencies under Surefire 3.5.2.

---

## Phase 5: User Story 3 - Backward Compatibility & Architectural Invariant Preservation (Priority: P3)

**Goal**: Preserve Zero-Dependency Core, 3-tier error hierarchy, and 100% backward compatibility of public APIs and response contracts

**Independent Test**: Inspect `core-exceptions` packaged artifact for zero third-party dependencies and verify public API classes retain unmodified method signatures.

### Implementation for User Story 3

- [X] T016 [US3] Verify Zero-Dependency Core invariant: confirm `core-exceptions/pom.xml` contains 0 runtime dependencies and only Java 25 standard library APIs are imported across `core-exceptions/src/main/java/com/cifertech/exceptionhandler/exceptions/`
- [X] T017 [US3] Verify backward compatibility of public exception hierarchy and HTTP status mappings in `core-exceptions/src/main/java/com/cifertech/exceptionhandler/exceptions/ApiException.java` and `HttpCode.java`
- [X] T018 [US3] Confirm sanitized error payload contracts and `Level.SEVERE` internal logging in `quarkus-exceptions-handler/src/main/java/com/cifertech/exceptionhandler/ExceptionGlobalHandler.java`

**Checkpoint**: All architectural invariants and backward compatibility guarantees are validated.

---

## Phase 6: Polish & Cross-Cutting Concerns

**Purpose**: Documentation synchronization, license validation, and end-to-end build verification

- [X] T019 [P] Update module documentation to reflect Java 25 and SemVer 2.0.0 in `.agents/modules/core-exceptions.md`
- [X] T020 [P] Update module documentation to reflect Quarkus 3.40.1.1, BOM governance, and Java 25 in `.agents/modules/quarkus-exceptions-handler.md`
- [X] T021 [P] Synchronize module index and version references in `.agents/modules/README.md`
- [X] T022 [P] Update root `README.md` with version `2.0.0`, Java 25 prerequisites, Quarkus 3.40.1.1 baseline, and dependency snippet
- [X] T023 Apply and verify Apache 2.0 license headers across all project files via `mvn license:format`
- [X] T024 Execute complete multi-module verification following [quickstart.md](quickstart.md) via `mvn clean package`

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: No dependencies — executes first.
- **Foundational (Phase 2)**: Depends on Setup completion — BLOCKS all user stories.
- **User Story 1 (Phase 3 - P1 MVP)**: Depends on Foundational completion.
- **User Story 2 (Phase 4 - P2)**: Depends on User Story 1 completion (buildable module needed to execute modernized tests).
- **User Story 3 (Phase 5 - P3)**: Depends on User Stories 1 and 2 completion.
- **Polish (Phase 6)**: Depends on all user stories being implemented and passing.

### Parallel Opportunities

- **Phase 1**: `T002` (`pr-checks.yml`) and `T003` (`publish.yml`) can execute in parallel after `T001`.
- **Phase 2**: `T005` (plugin updates in root POM) and `T006` (`core-exceptions/pom.xml`) can execute in parallel.
- **Phase 4**: `T013`, `T014`, and `T015` (unit test executions for each handler) can run in parallel.
- **Phase 6**: `T019`, `T020`, `T021`, and `T022` (documentation synchronization files) can all execute in parallel.

---

## Implementation Strategy

### MVP First (User Story 1 Only)

1. Complete Phase 1 (Setup: version 2.0.0, Java 25 properties, CI workflows).
2. Complete Phase 2 (Foundational: `quarkus-bom:3.40.1.1`, plugin versions, core POM).
3. Complete Phase 3 (User Story 1: `quarkus-exceptions-handler/pom.xml`, Jandex 3.2.7).
4. **STOP and VALIDATE**: Compile `mvn clean compile` to prove Java 25 & Quarkus 3.40.1.1 compatibility.

### Incremental Delivery

1. Setup + Foundational → Project infrastructure ready.
2. US1 → Compile & Jandex index verified (MVP).
3. US2 → Dependency vulnerability remediation & test suite verified.
4. US3 → Zero-dependency core & backward compatibility confirmed.
5. Polish → Docs synchronized, license formatted, end-to-end package built.
