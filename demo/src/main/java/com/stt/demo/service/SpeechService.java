package com.stt.demo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stt.demo.model.Transcript;
import com.stt.demo.repository.TranscriptRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;



@Service
public class SpeechService {


    @Value("${DEEPGRAM_API_KEY}")
    private String API_KEY;
    @Autowired
    private TranscriptRepository transcriptRepository;

    public String transcribeAudio(File audioFile) throws Exception {

        byte[] audioBytes = Files.readAllBytes(audioFile.toPath());

        WebClient webClient = WebClient.builder()
                .baseUrl("https://api.deepgram.com")
                .defaultHeader("Authorization", "Token " + API_KEY)
                .build();

        String response = webClient.post()
                .uri("/v1/listen")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .bodyValue(audioBytes)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonNode = mapper.readTree(response);

        String transcriptText = jsonNode
                .get("results")
                .get("channels")
                .get(0)
                .get("alternatives")
                .get(0)
                .get("transcript")
                .asText();

        // SAVE TO DATABASE
        Transcript transcript = new Transcript();

        transcript.setFileName(audioFile.getName());

        transcript.setFilePath("/audio/" + audioFile.getName());

        transcript.setTranscript(transcriptText);

        transcript.setUploadedAt(LocalDateTime.now());

        transcriptRepository.save(transcript);

        return transcriptText;
    }
}