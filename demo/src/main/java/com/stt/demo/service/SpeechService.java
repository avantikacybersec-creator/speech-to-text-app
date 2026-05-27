package com.stt.demo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;
import java.nio.file.Files;

@Service
public class SpeechService {

    private final String API_KEY = "50944e8d86011b63504f1ea710b1714580f3bead";

    public String transcribeAudio(File audioFile) throws Exception {

        byte[] audioBytes = Files.readAllBytes(audioFile.toPath());

        WebClient webClient = WebClient.builder()
                .baseUrl("https://api.deepgram.com")
                .defaultHeader("Authorization", "Token " + API_KEY)
                .build();

        String response = webClient.post()
                .uri("/v1/listen")
                .contentType(MediaType.valueOf("audio/wav"))
                .bodyValue(audioBytes)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // Parse JSON response
        ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonNode = mapper.readTree(response);

        String transcript = jsonNode
                .get("results")
                .get("channels")
                .get(0)
                .get("alternatives")
                .get(0)
                .get("transcript")
                .asText();

        return transcript;
    }
}