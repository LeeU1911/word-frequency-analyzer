package com.example.wordfrequencyanalyzer;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WordFrequencyCacheService {

    @Cacheable(value = "wordFrequency", key = "#text")
    public Map<String, Integer> countWordFrequency(String text) {
        Map<String, Integer> counter = new HashMap<>();
        if (text == null || text.isBlank()) {
            return counter;
        }
        String[] wordArray = text.toLowerCase().split("[^a-zA-Z]+");
        for (String word : wordArray) {
            if (!word.isEmpty()) {
                counter.merge(word, 1, Integer::sum);
            }
        }
        return counter;
    }
}
