# Word Frequency Analyzer

Spring Boot REST API that analyzes text to calculate word frequencies.

## Approach

The application implements the `WordFrequencyAnalyzer` interface with three core methods:
1. **calculateHighestFrequency** - Returns the count of the most frequent word
2. **calculateFrequencyForWord** - Returns the count of a specific word
3. **calculateMostFrequentNWords** - Returns the top N words sorted by frequency (descending), then alphabetically

The implementation uses a `HashMap` to count word occurrences and Java Streams for sorting and filtering results.

## Assumptions

- **Case insensitivity**: "CAT" and "cat" are treated as the same word
- **Word definition**: Only alphabetic characters form words; all other characters are separators
- **Null/blank input**: Returns -1 or null depending on the method

## REST Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/highest` | POST | Get highest word frequency |
| `/word` | POST | Get frequency of a specific word |
| `/top` | POST | Get top N most frequent words |

### Request/Response Examples

**POST /highest**
```json
Request:  {"text": "the cat walks the the staircase"}
Response: {"frequency": 3}
```

**POST /word**
```json
Request:  {"text": "the cat walks the the staircase", "word": "the"}
Response: {"frequency": 3}
```

**POST /top**
```json
Request:  {"text": "the cat walks the the staircase", "n": 3}
Response: {"words": [{"word": "the", "frequency": 3}, {"word": "cat", "frequency": 1}]}
```

**Note**: Missing words return `0` (not null).

## Running the Application

```bash
./mvnw spring-boot:run
```

The server starts on port 8080 (or a random port if 8080 is busy).

## Running Tests

```bash
./mvnw test
```

**Note**: Tests require Java 26.

## API Documentation

An OpenAPI 3.0 specification is available at `openapi.yaml`.
