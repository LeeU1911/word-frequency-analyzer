package com.example.wordfrequencyanalyzer.dto;

import com.example.wordfrequencyanalyzer.WordFrequency;
import java.util.List;

public record TopWordsResponse(List<WordFrequency> words) {}
