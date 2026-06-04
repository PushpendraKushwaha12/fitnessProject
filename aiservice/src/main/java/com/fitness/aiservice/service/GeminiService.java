package com.fitness.aiservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;

@Service
@Slf4j
public class GeminiService {

    private final WebClient webClient;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    public GeminiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

//    public String getRecommendation(String details) {
//        try {
//            Map<String, Object> requestBody = Map.of(
//                    "contents", new Object[]{
//                            Map.of("parts", new Object[]{
//                                    Map.of("text", details)
//                            })
//                    }
//            );
//            return webClient.post()
//                    .uri(geminiApiUrl + "?key=" + geminiApiKey)
//                    .header("Content-Type", "application/json")
//                    .bodyValue(requestBody)
//                    .retrieve()
//                    .bodyToMono(String.class)
//                    .block();
//        } catch (WebClientResponseException.TooManyRequests e) {
//            log.error("Gemini API limit exceeded. Try after some time.");
//            return "Gemini API limit exceeded. Please try after some time.";
//        } catch (Exception e) {
//            log.error("Gemini API error", e);
//            return "Gemini API error occurred.";
//        }
//    }

    public String getRecommendation(String details) {

        try {

            Map<String, Object> requestBody = Map.of(
                    "contents", new Object[]{
                            Map.of("parts", new Object[]{
                                    Map.of("text", details)
                            })
                    }
            );

            return webClient.post()
                    .uri(geminiApiUrl + "?key=" + geminiApiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

        } catch (WebClientResponseException.TooManyRequests e) {

            log.error("Gemini quota exceeded. Using mock response.");

            return """
        {
          "analysis": {
            "overall": "Good running performance",
            "pace": "Maintain current pace",
            "heartRate": "Heart rate is in healthy range",
            "caloriesBurned": "Good calorie burn"
          },
          "improvements": [
            {
              "area": "Endurance",
              "recommendation": "Increase running duration slowly week by week"
            }
          ],
          "suggestions": [
            {
              "workout": "Interval Running",
              "description": "Run fast for 1 minute and slow jog for 2 minutes"
            }
          ],
          "safety": [
            "Warm up before running",
            "Stay hydrated"
          ]
        }
        """;

        } catch (Exception e) {

            log.error("Gemini Error", e);

            return "Gemini Error";
        }
    }
}

