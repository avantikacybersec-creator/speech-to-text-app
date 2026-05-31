package com.stt.demo.controller;

import com.stt.demo.exception.InvalidFileException;
import com.stt.demo.model.Transcript;
import com.stt.demo.repository.TranscriptRepository;
import java.time.LocalDateTime;
import com.stt.demo.service.SpeechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import java.util.List;


@RestController
@RequestMapping("/api/speech")
@CrossOrigin("*")
public class SpeechController {

    @Autowired
    private SpeechService speechService;

    @Autowired
    private TranscriptRepository transcriptRepository;

    @PostMapping("/upload")
    public String uploadAudio(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new InvalidFileException("File is empty");
        }

        if (!file.getContentType().startsWith("audio")) {
            throw new InvalidFileException("Only audio files are allowed");
        }
        String uploadDir = System.getProperty("user.dir") + "/uploads/";

        File directory = new File(uploadDir);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        String uniqueFileName =
                System.currentTimeMillis() + "_" + file.getOriginalFilename();

        String filePath = uploadDir + uniqueFileName;

        File destFile = new File(filePath);

        file.transferTo(destFile);

        String transcriptText = speechService.transcribeAudio(destFile);

        return transcriptText;
    }
    @GetMapping("/history")
    public List<Transcript> getHistory() {
        return transcriptRepository.findAll();
    }
    @GetMapping("/{id}")
    public Transcript getTranscriptById(
            @PathVariable Long id){

        return speechService.getTranscriptById(id);
    }
    @GetMapping("/search")
    public List<Transcript> searchTranscript(
            @RequestParam String keyword){

        return speechService.searchTranscript(
                keyword
        );
    }
    @DeleteMapping("/{id}")
    public String deleteTranscript(@PathVariable Long id) {

        speechService.deleteTranscript(id);

        return "Transcript deleted successfully";
    }
}