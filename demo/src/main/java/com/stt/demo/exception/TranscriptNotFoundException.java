package com.stt.demo.exception;

public class TranscriptNotFoundException
        extends RuntimeException {

    public TranscriptNotFoundException(String message) {
        super(message);
    }
}
