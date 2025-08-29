package com.example.Test.service;

import com.example.Test.model.GenerateWebhookRequest;
import com.example.Test.model.GenerateWebhookResponse;
import com.example.Test.util.SqlSolutions;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.MediaType;

import java.util.Map;

@Service
public class WebhookService {

    @Autowired
    private WebClient webClient;

    private static final String INIT_URL =
            "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

    @PostConstruct
    public void init() {
        sendInitialRequest();
    }

    private void sendInitialRequest() {
        GenerateWebhookRequest request = new GenerateWebhookRequest(
                "John Doe", "REG12347", "john@example.com");

        GenerateWebhookResponse response = webClient.post()
                .uri(INIT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(GenerateWebhookResponse.class)
                .block();

        if (response != null) {
            sendFinalQuery(response.getWebhook(), response.getAccessToken());
        }
    }

    private void sendFinalQuery(String webhookUrl, String accessToken) {
        webClient.post()
                .uri(webhookUrl)
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of("finalQuery", SqlSolutions.FINAL_QUERY))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
