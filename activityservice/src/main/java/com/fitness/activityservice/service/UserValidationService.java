package com.fitness.activityservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserValidationService {

    private final WebClient userServiceWebClient;

    public boolean validateUser(String userId) {
        log.info("Calling User Service for {}", userId);
        try {
            return Boolean.TRUE.equals(userServiceWebClient.get().uri("/api/users/{userId}/validation", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block());
        } catch (WebClientResponseException e) {
            System.out.println("User validation failed: " + e.getStatusCode());
            System.out.println(e.getResponseBodyAsString());
        }
        return false;
    }
}
