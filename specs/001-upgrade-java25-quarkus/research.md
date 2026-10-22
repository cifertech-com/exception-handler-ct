# Research & Technical Decisions: Java 25 & Quarkus 3.40.1.1 Upgrade

**Feature**: `001-upgrade-java25-quarkus`  
**Date**: 2026-10-22  
**Status**: Completed  

---

## 1. Java 25 Compiler Baseline & Maven Configuration

### Decision
Configure Java 25 compiler settings in the root `pom.xml` using both `<maven.compiler.release>25</maven.compiler.release>` and source/target properties:
```xml
<properties>
    <maven.compiler.source>25</maven.compiler.source>
    <maven.compiler.target>25</maven.compiler.target>
    <maven.compiler.release>25</maven.compiler.release>
</properties>
```
Additionally, enforce `maven-compiler-plugin` version `3.13.0` to ensure robust handling of Java 25 bytecode.

### Rationale
- The `--release 25` flag configures the compiler against the official Java 25 platform APIs, preventing inadvertent linkage against proprietary or host-specific JDK classes.
- Defining source and target ensures legacy tooling and IDE integrations that query source/target properties remain fully functional.

### Alternatives Considered
- *Using only `<maven.compiler.source>`/`<target>`*: Rejected because it allows accidental compilation against non-standard runtime APIs present in the local JDK environment.
- *Maintaining Java 21 with multi-release JARs*: Rejected because the explicit requirement is to upgrade the primary runtime baseline to Java 25 LTS to eliminate CVEs and modernize the library.

---

## 2. Quarkus 3.40.1.1 LTS Dependency Governance

### Decision
Import `io.quarkus:quarkus-bom:3.40.1.1` in the `<dependencyManagement>` section of the parent `pom.xml`:
```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>io.quarkus</groupId>
            <artifactId>quarkus-bom</artifactId>
            <version>3.40.1.1</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```
In `quarkus-exceptions-handler/pom.xml`, omit hardcoded version tags for:
- `jakarta.ws.rs:jakarta.ws.rs-api` (`provided`)
- `jakarta.validation:jakarta.validation-api` (`provided`)
- `org.eclipse.microprofile.config:microprofile-config-api` (`provided`)
- `org.junit.jupiter:junit-jupiter-engine` (`test`)
- `org.mockito:mockito-core` and `mockito-junit-jupiter` (`test`)
- `org.jboss.resteasy:resteasy-core` (`test`)

### Rationale
- Quarkus 3.40.1.1 was released specifically to patch security advisories in the Quarkus 3.40 LTS stream.
- Using `quarkus-bom` delegates version curation directly to Quarkus, ensuring zero version drift and consistent classloader behavior across all downstream microservices.

### Alternatives Considered
- *Individual dependency version bumping*: Rejected because manually bumping each library risks version conflicts between RESTEasy, SmallRye, and Jakarta specifications.

---

## 3. Maven Build & Testing Tooling Alignment

### Decision
Upgrade build and verification plugins to current releases compatible with Java 25 class file formats:
- **`io.smallrye:jandex-maven-plugin`**: `3.2.7` (enables correct scanning and indexing of Java 25 bytecode for `@Provider` discovery in `META-INF/jandex.idx`).
- **`org.apache.maven.plugins:maven-surefire-plugin`**: `3.5.2` (full JUnit 5 execution support on Java 25).
- **`org.apache.maven.plugins:maven-compiler-plugin`**: `3.13.0`.
- **`com.mycila:license-maven-plugin`**: `4.6` (for Apache 2.0 license validation).
- **`org.codehaus.mojo:flatten-maven-plugin`**: `1.6.0` (retained for CI-friendly `${revision}` resolution).
- **`org.apache.maven.plugins:maven-javadoc-plugin`**: `3.11.2` (supports Java 25 doclet).

### Rationale
Older plugin releases (e.g. Surefire 3.2.x or Jandex 3.1.x) fail with `IllegalArgumentException: Unsupported class file major version` when processing Java 25 class files.

### Alternatives Considered
- *Retaining current plugin versions*: Fails during build on Java 25 environments.

---

## 4. CI/CD Pipeline Modernization (GitHub Actions)

### Decision
Update `.github/workflows/pr-checks.yml` and `.github/workflows/publish.yml` to specify:
```yaml
- name: Set up JDK 25
  uses: actions/setup-java@v4
  with:
    java-version: '25'
    distribution: 'temurin'
    cache: maven
```

### Rationale
Ensures that all PR validation (automated unit tests, license formatting, and CodeQL security analysis) and Maven Central release deployments execute against the official Eclipse Temurin Java 25 JDK.

---

## 5. Architectural & Constitutional Invariants Verification

### Invariant Checks
1. **Principle I (Zero-Dependency Core)**:
   - `core-exceptions/pom.xml` must NOT import `quarkus-bom` or any runtime library. It compiles against Java 25 standard library only.
2. **Principle III & IV (Discipline & Utility Mappers)**:
   - No public handler method exceeds 3 parameters.
   - `ResponseBuilder` and utility helpers remain `public final` with private constructors and pure static methods.
3. **Principle V (3-Tier Hierarchy & Zero Information Leakage)**:
   - `ExceptionHandler` (Domain/ApiException), `ValidationExceptionHandler` (ConstraintViolationException), and `ExceptionGlobalHandler` (Throwable) retain exact status mapping and internal SEVERE logging without client stack trace leaks.
4. **Principle VII (Synchronous Documentation & SemVer `2.0.0`)**:
   - Version revision set to `2.0.0`.
   - Update `.agents/modules/core-exceptions.md`, `.agents/modules/quarkus-exceptions-handler.md`, `.agents/modules/README.md`, and root `README.md`.
