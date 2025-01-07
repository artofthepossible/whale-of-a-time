package com.example.whale_of_a_time;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WhaleOfATimeApplicationTests {

    @LocalServerPort
    private int port;

    @Container
    public static GenericContainer<?> app = new GenericContainer<>("demonstrationorg/whale-of-a-time-alpaquita-liberica-openjdk-alpine:v1.0")
            .waitingFor(Wait.forHttp("/"))
            .withExposedPorts(8080)
            .withCommand("java", "-jar", "/app/app.jar");

    private static String baseUrl;

    @BeforeAll
    public static void setUp() {
        baseUrl = "http://" + app.getHost() + ":" + app.getMappedPort(8080);
    }

    @Test
    void contextLoads() {
    }

    @Test
    void testHomePage() throws IOException {
        String baseUrl = WhaleOfATimeApplicationTests.baseUrl;
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(baseUrl, String.class);

        // Parse the HTML response using Jsoup
        org.jsoup.nodes.Document doc = Jsoup.parse(response);

        // Assert that the title contains
        assertThat(doc.body().text()).contains("Welcome to My Spring Boot Application");
        assertThat(doc.body().text()).contains("Get it on GitHub");
    }
}
