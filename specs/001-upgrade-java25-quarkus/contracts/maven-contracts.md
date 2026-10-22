# Maven POM & Dependency Governance Contracts

**Feature**: `001-upgrade-java25-quarkus`  
**Date**: 2026-10-22  

---

## 1. Parent POM Contract (`pom.xml`)

```xml
<properties>
    <revision>2.0.0</revision>
    <maven.compiler.source>25</maven.compiler.source>
    <maven.compiler.target>25</maven.compiler.target>
    <maven.compiler.release>25</maven.compiler.release>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
</properties>

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

---

## 2. Core Exceptions Module (`core-exceptions/pom.xml`)

- **Strict Zero-Dependency**: Must NOT declare any `<dependencies>` section with external libraries.
- Inherits compiler settings (`source=25`, `target=25`, `release=25`).

---

## 3. Quarkus Integration Module (`quarkus-exceptions-handler/pom.xml`)

All external versions delegated to `quarkus-bom`:
- `io.github.yacson3287:core-exceptions` (`${project.version}`)
- `jakarta.ws.rs:jakarta.ws.rs-api` (`provided`)
- `jakarta.validation:jakarta.validation-api` (`provided`)
- `org.eclipse.microprofile.config:microprofile-config-api` (`provided`)
- `org.junit.jupiter:junit-jupiter-engine` (`test`)
- `org.mockito:mockito-core` (`test`)
- `org.mockito:mockito-junit-jupiter` (`test`)
- `org.jboss.resteasy:resteasy-core` (`test`)

### Plugins
- `io.smallrye:jandex-maven-plugin:3.2.7` (creates `META-INF/jandex.idx`)
- `org.apache.maven.plugins:maven-surefire-plugin:3.5.2` (runs JUnit 5 tests)
