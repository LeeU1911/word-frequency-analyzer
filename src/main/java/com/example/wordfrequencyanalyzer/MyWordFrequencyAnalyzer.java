package com.example.wordfrequencyanalyzer;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MyWordFrequencyAnalyzer implements WordFrequencyAnalyzer{
    private Map<String, Integer> countWordFrequency(String text) {
        Map<String, Integer> counter = new HashMap<>();
        if (text == null || text.isBlank()) {
            return null;
        }
        String separator = "[^a-zA-Z]+"; // assuming separator character is anything but alphabets
        String[] wordArray = text.toLowerCase().split(separator);
        for (String word : wordArray) {
            counter.put(word, counter.getOrDefault(word, 0) + 1);
        }
        return counter;
    }

    @Override
    public int calculateHighestFrequency(String text) {
        Map<String, Integer> counter = countWordFrequency(text);
        if (counter == null) {
            return -1;
        }
        return counter.entrySet().stream()
                .max(Comparator.comparingInt(Map.Entry::getValue))
                .orElse(Map.entry("", 0))
                .getValue();
    }

    @Override
    public int calculateFrequencyForWord(String text, String word) {
        Map<String, Integer> counter = countWordFrequency(text);
        if (counter == null || word == null) {
            return 0;
        }
        return counter.getOrDefault(word.toLowerCase(), 0);
    }

    @Override
    public List<WordFrequency> calculateMostFrequentNWords(String text, int n) {
        Map<String, Integer> counter = countWordFrequency(text);
        if (counter == null) {
            return null;
        }
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
