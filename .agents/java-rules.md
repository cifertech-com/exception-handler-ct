# ☕ Java & Clean Code Rules (`java-rules.md`)

This document defines language-level clean code standards and Java programming conventions applicable to any Java project in the organization.

---

## 1. Clean Code in Lambdas & Streams

### 1.1. Zero business logic, zero mappings, and zero exception instantiation inside Lambdas
- **Never place business logic, data transformations, complex object builders, or inline exception instantiation inside lambda expressions** (`.orElseGet(...)`, `.orElseThrow(...)`, `.map(...)`, `.filter(...)`, etc.).
- Lambdas must only delegate via method references or to descriptive private methods:
  ```java
  // ❌ INCORRECT: Inline exception instantiation inside lambda
  Item item = productRepositoryPort.findById(id)
          .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));

  // ✅ CORRECT: Lambda delegates via method reference to a descriptive private method
  Item item = productRepositoryPort.findById(id)
          .orElseThrow(this::productNotFoundException);
  ```

### 1.2. Data mapping and object construction in private methods
- In services and use cases, instantiation of builders and complex data transformations must be extracted into dedicated private methods (`buildNewLot`, `buildReceiptMovement`, `buildInitialLotLocation`).
- This eliminates cognitive noise and allows the main method to read sequentially as a high-level flow of business intents.

### 1.3. Single responsibility private methods
- Separate operational steps into private methods with names that clearly express intent:
    - `validatePrerequisites(...)`
    - `deductSourceStock(...)`
    - `incrementDestinationStock(...)`
    - `recordMovement(...)`

---

## 2. Method Signatures & Parameters

### 2.1. Maximum 3 parameters rule
- **No public method in use cases, services, or adapters should accept more than 3 parameters**.
- When an operation requires multiple input values, encapsulate them into a dedicated domain command or parameter object (e.g. `ReceiveLot`, `ConsumeStock`, `TransferStock`).
- Domain commands represent structured operations and are **not** required to map 1:1 with database tables.

### 2.2. Typed filtering with Enums
- When a query or search method allows filtering by different criteria sharing the same primitive type (e.g. `warehouseId` vs `categoryId`, both `Long`), use a type-safe discriminator enum:
  ```java
  enum FilterType {
      WAREHOUSE_ID,
      CATEGORY_ID
  }
  List<Location> execute(Long filterValue, FilterType filterType);
  ```

---

## 3. Utility & Mapper Classes

### 3.1. Final static utility mappers
- Utility classes (such as DTO/Domain mappers) must be final with private constructors:
  ```java
  public final class WarehouseMapper {
      private WarehouseMapper() {}

      public static Warehouse toDomain(WarehouseRequestDto dto) { ... }
      public static WarehouseResponseDto toResponseDto(Warehouse domain) { ... }
  }
  ```
- Use pure static methods only. Do not inject them as stateful beans or rely on runtime reflection/bytecode generation libraries when manual static mapping is standard.
