# E-Commerce Pricing Service

Microservice responsible for managing and consulting the final prices applicable to the products of a chain (e.g., Inditex Group / ZARA) on a given date, taking into account the priority of the current rates.

## Technological Stack

* **Java 21**
* **Spring Boot 3** (Web, Data JPA)
* **H2 Database** (In-Memory)
* **MapStruct & Lombok** (Boilerplate reduction and efficient mapping)
* **OpenAPI 3 / Swagger UI** (API-First design)
* **JUnit 5, Mockito, JsonPath** (IT and Unit Test)
* **Cucumber** (Behavior-Driven Development / Acceptance Tests)
* **Docker & Docker Compose** (Multi-stage containerization)

---

## Architecture and Design Decisions

The project has been designed following the principles of **Hexagonal Architecture (Ports and Adapters)** and **Tactical Domain-Driven Design (DDD)**, guaranteeing high cohesion, low coupling, and highly testable code:

1. **Domain Isolation:** The business logic (Use Cases, Domain Entities) has no dependencies on the framework (Spring) or the database.
2. **API-First Design:** The API contract (`openapi.yml`) is the **single source of truth**.
3. **Global Exception Handling (`@RestControllerAdvice`):** Domain exceptions (e.g., `PriceNotFoundException`, `DomainValidationException`) are intercepted at the infrastructure layer to return standardized JSON contracts (404 Not Found, 400 Bad Request) without exposing internal traces to the client.

---

## Possible Improvements and Scalability

In accordance with the premises of keeping the solution simple and avoiding over-engineering, no additional infrastructure tools have been included that would not add value in the current scenario of an in-memory database (H2) with a reduced data volume.

However, in a high-concurrency production environment, the following scalability strategy would be proposed:

### Cache Strategy (e.g. Caffeine / Redis)
Since price rates are data with a high read frequency and low update frequency, it would be optimal to implement a cache layer in the `PricePersistenceAdapter`.

* **Cache key:** Combination of `brandId` and `productId` (e.g., `1-35455`).
* **Value:** List of all rates (`List<Price>`) associated with that product and chain.
* **Justification:** **NO** would be included in the cache key. Doing so would result in infinite cardinality and a zero *Cache Hit Ratio* due to the variability of seconds/milliseconds.
* **Flow:** The system would retrieve the complete list of rates from the cache in microseconds, and the Domain layer would filter the current rate in memory (via the Stream API) and apply the tiebreaker based on priority. This would drastically reduce I/O operations against the database.

---

## How to run the project

The project includes a multi-stage Dockerfile that compiles the code, runs the entire test suite, and launches the application on a lightweight JRE image. You only need to have Docker installed and running.

```bash
# At the project root, run:
docker compose up --build
```

### Generated Reports

After running the previous build, navigable visual reports are automatically generated in the following paths:

* **Code Coverage Report (JaCoCo):** `target/site/jacoco/index.html`
* **RAcceptance Test Report (Cucumber):** `target/cucumber-reports/index.html`

---

## Endpoints and Documentation (Swagger)

The microservice is launched and exposes its services on port **`8080`**.

### Visual Interface (Swagger UI)
Once the container is up and running (or executed locally), you can interact with the API visually by accessing:

**[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

The OpenAPI contract preloads real-world examples into the test fields, so you just need to deploy the endpoint, click **"Try it out"** and **"Execute"**.

### Existing Endpoint and Invocation Example
* **`GET /price`**: Gets the final applicable price by filtering by product, string and date, applying the tie-breaking rule by priority in case of overlapping rates.

You can try **Test 1** from the problem statement directly from your terminal with this `curl` command:

```bash
curl -X 'GET' \
  'http://localhost:8080/price?applicationDate=2020-06-14T10:00:00Z&productId=35455&brandId=1' \
  -H 'accept: application/json'
```

**Expected Response (HTTP 200 OK):**
```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 1,
  "startDate": "2020-06-14T00:00:00Z",
  "endDate": "2020-12-31T23:59:59Z",
  "price": 35.5
}
```