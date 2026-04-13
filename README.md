# Word Frequency Analyzer

Spring Boot REST API that analyzes text to calculate word frequencies.

## Approach

The application implements the `WordFrequencyAnalyzer` interface with three core methods:
1. **calculateHighestFrequency** - Returns the count of the most frequent word
2. **calculateFrequencyForWord** - Returns the count of a specific word
3. **calculateMostFrequentNWords** - Returns the top N words sorted by frequency (descending), then alphabetically

Word frequency counting is cached using Caffeine to avoid redundant computations.

## Assumptions

- **Case insensitivity**: "CAT" and "cat" are treated as the same word
- **Word definition**: Only alphabetic characters form words; all other characters are separators
- **Null/blank input**: Returns `0` or empty list (not null or -1)

## REST Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/v1/highest` | POST | Get highest word frequency |
| `/v1/word` | POST | Get frequency of a specific word |
| `/v1/top` | POST | Get top N most frequent words |

### Request/Response Examples

**POST /v1/highest**
```json
Request:  {"text": "the cat walks the the staircase"}
Response: {"frequency": 3}
```

**POST /v1/word**
```json
Request:  {"text": "the cat walks the the staircase", "word": "the"}
Response: {"frequency": 3}
```

**POST /v1/top**
```json
Request:  {"text": "the cat walks the the staircase", "n": 3}
Response: {"words": [{"word": "the", "frequency": 3}, {"word": "cat", "frequency": 1}, {"word": "staircase", "frequency": 1}]}
```

### Validation

Requests are validated:
- `text` is required and must not be blank
- `word` is required and must not be blank
- `n` must be at least 1

Invalid requests return a `400 Bad Request` with error details.

## Running the Application

```bash
./mvnw spring-boot:run
```

The server starts on port 8080.

## Running Tests

```bash
./mvnw test
```

**Requirements**: Java 26

## API Documentation

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI spec: http://localhost:8080/api-docs
