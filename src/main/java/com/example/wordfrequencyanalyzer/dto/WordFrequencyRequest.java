package com.example.wordfrequencyanalyzer.dto;

import jakarta.validation.constraints.NotBlank;

public record WordFrequencyRequest(
        @NotBlank(message = "text is required")
        String text,
        @NotBlank(message = "word is required")
        String word
) {}
