## Running the AceToys Gatling tests

The project is designed to be **production-ready** and configurable via system properties.

### Environments

Environment is selected with the `env` system property and resolved via `env-<env>.properties` in `src/test/resources/config`:

- `env-local.properties` → `env=local`
- `env-demo.properties` → `env=demo`

Each file defines at least:

```properties
baseUrl=https://...
requestTimeoutMs=5000

### SLA / assertions overrides

Global assertions are configurable via system properties (see `UserLoadConfig`):

- `ASSERT_P95_MS` (default: `1000`)
- `ASSERT_P99_MS` (default: `2000`)
- `ASSERT_FAILED_PERCENT_MAX` (default: `1.0`)

**Example: stricter SLAs for demo:**

```bash
mvn gatling:test \
  -Denv=local \
  -DTEST_TYPE=CLOSED_MODEL_INJECTION \
  -DASSERT_P95_MS=800 \
  -DASSERT_P99_MS=1500 \
  -DASSERT_FAILED_PERCENT_MAX=0.5
