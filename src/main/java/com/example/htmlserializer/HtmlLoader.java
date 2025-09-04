package com.example.htmlserializer;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class HtmlLoader {

    private final HttpClient httpClient;

    // Constructor initializes HttpClient
    public HtmlLoader() {
        this.httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2) // Use HTTP/2
                                                                                     // for better
                                                                                     // performance
                .build();
    }

    /**
     * Asynchronous method to fetch HTML content from a URL.
     *
     * @param url The URL to fetch content from.
     * @return A CompletableFuture containing the HTML content as a string.
     */
    public CompletableFuture<String> load(String url) {
        try {
            // Create an HttpRequest
            HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).GET() // Use GET method
                    .build();

            // Send the request asynchronously
            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body); // Extract the response body as a string

        } catch (Exception e) {
            // Handle invalid URL or other exceptions
            CompletableFuture<String> failedFuture = new CompletableFuture<>();
            failedFuture.completeExceptionally(e);
            return failedFuture;
        }
    }
}
