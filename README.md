# Gatling API Tutorial 🚀

A simple, production-ready Gatling example project with both **API** and **Web** simulations. It shows how to structure Gatling tests, configure environments, and run load tests using the Maven wrapper (`./mvnw`).

---

## Quick Start ✅

Prerequisites:
- Java 17 installed
- No need to install Maven globally — use the included wrapper (`./mvnw`)

Run the API simulations (example):

```bash
./mvnw clean gatling:test -Denv=demo
```

Run the Web simulations (example):

```bash
./mvnw clean gatling:test -Denv=local -DWEB_TEST_TYPE=CLOSED_MODEL_INJECTION
```

Reports are generated in `target/gatling` after the run.

---

## Project Overview 🔧

- `src/test/java/io/performance/demo/api` — API simulations, requests, and scenarios
- `src/test/java/io/performance/demo/web` — Web simulations, page objects, and journeys
- `src/test/java/io/performance/demo/common` — shared config and helpers (env, headers, protocols)
- `src/test/resources/config` — environment property files (`env-local.properties`, `env-demo.properties`, ...)
- `src/test/resources/dataFiles` — example data and JSON payloads used by tests

This layout keeps examples small and easy to extend.

---

## Configuration & SLAs 🛠️

Select the environment with the `env` system property (matches `env-<env>.properties`):

```bash
-Denv=local
```

Override SLA/assertion values with system properties:
- `SLA_P95_MS` (default: `1000`)
- `SLA_P99_MS` (default: `2000`)
- `SLA_FAILED_PERCENT_MAX` (default: `1.0`)

Example:

```bash
./mvnw clean gatling:test -Denv=local -DSLA_P95_MS=800 -DSLA_P99_MS=1500 -DSLA_FAILED_PERCENT_MAX=0.5
```
