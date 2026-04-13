package com.example.wordfrequencyanalyzer;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MyWordFrequencyAnalyzer implements WordFrequencyAnalyzer {
    private static final String SEPARATOR = "[^a-zA-Z]+";

    private Map<String, Integer> countWordFrequency(String text) {
        Map<String, Integer> counter = new HashMap<>();
        if (text == null || text.isBlank()) {
            return counter;
        }
        String[] wordArray = text.toLowerCase().split(SEPARATOR);
        for (String word : wordArray) {
            if (!word.isEmpty()) {
                counter.merge(word, 1, Integer::sum);
            }
        }
        return counter;
    }

    @Override
    public int calculateHighestFrequency(String text) {
        Map<String, Integer> counter = countWordFrequency(text);
        return counter.values().stream()
                .max(Comparator.naturalOrder())
                .orElse(0);
    }

    @Override
    public int calculateFrequencyForWord(String text, String word) {
        if (word == null) {
            return 0;
        }
        Map<String, Integer> counter = countWordFrequency(text);
        return counter.getOrDefault(word.toLowerCase(), 0);
    }

    @Override
    public List<WordFrequency> calculateMostFrequentNWords(String text, int n) {
        if (n <= 0) {
            return List.of();
        }
        Map<String, Integer> counter = countWordFrequency(text);
        return counter.entrySet().stream()
                .sorted(
                        Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())
                                .thenComparing(Map.Entry.comparingByKey())
                )
                .limit(n)
                .map(entry -> new MyWordFrequency(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
