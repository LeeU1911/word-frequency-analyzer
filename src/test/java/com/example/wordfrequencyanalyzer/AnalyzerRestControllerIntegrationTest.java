package com.example.wordfrequencyanalyzer;

import com.example.wordfrequencyanalyzer.dto.HighestFrequencyRequest;
import com.example.wordfrequencyanalyzer.dto.TopWordsRequest;
import com.example.wordfrequencyanalyzer.dto.WordFrequencyRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.test.web.servlet.client.assertj.RestTestClientResponse;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class AnalyzerRestControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private RestTestClient restTestClient;

    @Test
    void testHighestRestEndpoint() {
        var request = new HighestFrequencyRequest("the cat walks the the staircase");

        RestTestClientResponse response = RestTestClientResponse.from(
                restTestClient.post()
                        .uri("http://localhost:%d/highest".formatted(port))
                        .body(request)
                        .exchange());
        assertThat(response).bodyJson().isEqualTo("{\"frequency\":3}");
    }

    @Test
    void testWordRestEndpoint() {
        var request = new WordFrequencyRequest("the cat walks the the staircase", "cat");

        RestTestClientResponse response = RestTestClientResponse.from(
                restTestClient.post()
                        .uri("http://localhost:%d/word".formatted(port))
                        .body(request)
                        .exchange());
        assertThat(response).bodyJson().isEqualTo("{\"frequency\":1}");
    }

    @Test
    void testTopRestEndpoint() {
        var request = new TopWordsRequest("the cat walks the the staircase", 3);

        RestTestClientResponse response = RestTestClientResponse.from(
                restTestClient.post()
                        .uri("http://localhost:%d/top".formatted(port))
                        .body(request)
                        .exchange());
        assertThat(response).bodyJson().isEqualTo("{\"words\":[{\"word\":\"the\",\"frequency\":3},{\"word\":\"cat\",\"frequency\":1},{\"word\":\"staircase\",\"frequency\":1}]}");
    }

    @Test
    void testBlankTextValidation() {
        var request = new HighestFrequencyRequest("");

        RestTestClientResponse response = RestTestClientResponse.from(
                restTestClient.post()
                        .uri("http://localhost:%d/highest".formatted(port))
                        .body(request)
                        .exchange());
        assertThat(response).bodyJson().asString().contains("Bad Request");
    }

    @Test
    void testEmptyWordValidation() {
        var request = new WordFrequencyRequest("test text", "");

        RestTestClientResponse response = RestTestClientResponse.from(
                restTestClient.post()
                        .uri("http://localhost:%d/word".formatted(port))
                        .body(request)
                        .exchange());
        assertThat(response).bodyJson().asString().contains("Bad Request");
    }

    @Test
    void testInvalidNValidation() {
        var request = new TopWordsRequest("test text", 0);

        RestTestClientResponse response = RestTestClientResponse.from(
                restTestClient.post()
                        .uri("http://localhost:%d/top".formatted(port))
                        .body(request)
                        .exchange());
        assertThat(response).bodyJson().asString().contains("Bad Request");
    }
}
