package com.example.wordfrequencyanalyzer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.test.web.servlet.client.assertj.RestTestClientResponse;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
public class AnalyzerRestControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private RestTestClient restTestClient;

    @Test
    public void testHighestRestEndpoint() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("text", "the cat walks the the staircase");

        restTestClient.post()
                .uri("http://localhost:%d/highest".formatted(port))
                .body(requestBody)
                .exchange()
                .expectBody(Integer.class)
                .isEqualTo(3);
    }


    @Test
    public void testWordRestEndpoint() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("text", "the cat walks the the staircase");
        requestBody.put("word", "cat");

        restTestClient.post()
                .uri("http://localhost:%d/word".formatted(port))
                .body(requestBody)
                .exchange()
                .expectBody(Integer.class)
                .isEqualTo(1);
    }


    @Test
    public void testTopRestEndpoint() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("text", "the cat walks the the staircase");
        requestBody.put("n", 3);

        RestTestClientResponse response = RestTestClientResponse.from(
                restTestClient.post()
                        .uri("http://localhost:%d/top".formatted(port))
                        .body(requestBody)
                        .exchange());
        assertThat(response).bodyJson().isEqualTo("[{\"word\":\"the\",\"frequency\":3},{\"word\":\"cat\",\"frequency\":1},{\"word\":\"staircase\",\"frequency\":1}]\n");
    }
}
