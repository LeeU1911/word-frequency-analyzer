package com.example.wordfrequencyanalyzer.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record TopWordsRequest(
        @NotBlank(message = "text is required")
        String text,
        @Min(value = 1, message = "n must be at least 1")
        int n
) {}
