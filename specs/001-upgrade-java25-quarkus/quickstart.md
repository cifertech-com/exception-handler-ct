# Quickstart & Verification Guide: Java 25 & Quarkus 3.40.1.1 Baseline

**Feature**: `001-upgrade-java25-quarkus`  
**Date**: 2026-10-22  
**Status**: Ready for Implementation  

---

## Prerequisites

- **Java Development Kit (JDK)**: Java 25 installed and configured (`JAVA_HOME` pointing to JDK 25).
- **Apache Maven**: Version 3.9+ installed (`mvn -version`).
- **Git**: Working tree on branch `001-upgrade-java25-quarkus`.

---

## 1. Clean Build & Unit Test Verification

To compile the entire multi-module project against Java 25 and execute the test suites:

```bash
# Verify active JDK
java -version

# Build all modules and execute tests
mvn clean test
```

### Expected Outcome:
- `core-exceptions` compiles cleanly with Java 25 bytecode (`release 25`).
- `quarkus-exceptions-handler` compiles and runs all test suites via Surefire 3.5.2 without reflection or classloader errors.
- `BUILD SUCCESS` with 0 test failures or errors.

---

## 2. Jandex Index Generation Verification

Verify that the Jandex plugin creates the index file in `quarkus-exceptions-handler` for automatic Quarkus `@Provider` discovery:

```bash
mvn package -DskipTests
jar -tf quarkus-exceptions-handler/target/quarkus-exceptions-handler-*.jar | grep "META-INF/jandex.idx"
```

### Expected Outcome:
`META-INF/jandex.idx` is present in the packaged JAR.

---

## 3. License Header Compliance

Verify that Apache 2.0 license headers are intact across all Java source files:

```bash
mvn license:check
```

### Expected Outcome:
All source files comply with the license policy; process exits with code 0.

---

## 4. GitHub Actions CI Verification

Verify that local workflow configuration aligns with Java 25:

```bash
grep -n "java-version: '25'" .github/workflows/*.yml
```

### Expected Outcome:
Both `pr-checks.yml` and `publish.yml` show `java-version: '25'`.
