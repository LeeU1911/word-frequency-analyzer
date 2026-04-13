package com.example.wordfrequencyanalyzer;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MyWordFrequencyAnalyzer implements WordFrequencyAnalyzer {
    private final WordFrequencyCacheService cacheService;

    public MyWordFrequencyAnalyzer(WordFrequencyCacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    public int calculateHighestFrequency(String text) {
        Map<String, Integer> counter = cacheService.countWordFrequency(text);
        return counter.values().stream()
                .max(Comparator.naturalOrder())
                .orElse(0);
    }

    @Override
    public int calculateFrequencyForWord(String text, String word) {
        if (word == null) {
            return 0;
        }
        Map<String, Integer> counter = cacheService.countWordFrequency(text);
        return counter.getOrDefault(word.toLowerCase(), 0);
    }

    @Override
    public List<WordFrequency> calculateMostFrequentNWords(String text, int n) {
        if (n <= 0) {
            return List.of();
        }
        Map<String, Integer> counter = cacheService.countWordFrequency(text);
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
