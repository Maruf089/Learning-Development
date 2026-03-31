package com.example.demo.client;

import com.example.demo.config.WebClientConfig;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class GreetingClient {

    private final WebClient greetingWebClient;

    public GreetingClient(WebClient greetingWebClient) {
        this.greetingWebClient = greetingWebClient;
    }

    public String getGreeting(String name) {
        return greetingWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/greet")
                        .queryParam("name", name)
                        .build())
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        response -> response.bodyToMono(String.class)
                                .defaultIfEmpty("Client error from greeting service")
                                .flatMap(message -> Mono.error(
                                        new ResourceNotFoundException(
                                                "Greeting service returned 4xx: " + message
                                        )
                                ))
                )
                .onStatus(
                        HttpStatusCode::is5xxServerError,
                        response -> response.bodyToMono(String.class)
                                .defaultIfEmpty("Server error from greeting service")
                                .flatMap(message -> Mono.error(
                                        new RuntimeException(
                                                "Greeting service returned 5xx: " + message
                                        )
                                ))
                )
                .bodyToMono(String.class)
                .block();
    }
}
