package com.stt.demo.repository;

import com.stt.demo.model.Transcript;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface TranscriptRepository
        extends JpaRepository<Transcript, Long> {

    Optional<Transcript> findById(Long id);
    List<Transcript> findByTranscriptContainingIgnoreCase(
            String keyword
    );
}