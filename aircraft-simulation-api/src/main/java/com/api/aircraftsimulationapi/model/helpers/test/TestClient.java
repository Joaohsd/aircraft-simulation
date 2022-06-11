package com.api.aircraftsimulationapi.model.helpers.test;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

public class TestClient {

    private final URI uri;
    private HttpRequest httpRequest;
    private HttpClient client;
    public static HttpResponse<String> response;

    public TestClient(String uri) throws URISyntaxException {
        this.uri = new URI(uri);
        this.client = HttpClient.newHttpClient();
    }

    public void saveTestDataOnDB(@NotNull String jsonSampleString) {
        this.httpRequest = HttpRequest.newBuilder()
                        .uri(this.uri)
                        .timeout(Duration.of(10, SECONDS))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(jsonSampleString))
                        .build();
        try {
            response = this.client.send(this.httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
