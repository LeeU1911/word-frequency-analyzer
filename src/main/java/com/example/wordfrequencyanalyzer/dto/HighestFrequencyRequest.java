package com.example.wordfrequencyanalyzer.dto;

import jakarta.validation.constraints.NotBlank;

public record HighestFrequencyRequest(
        @NotBlank(message = "text is required")
        String text
) {}
