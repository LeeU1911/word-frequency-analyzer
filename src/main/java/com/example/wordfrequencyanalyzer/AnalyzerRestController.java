package com.example.wordfrequencyanalyzer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class AnalyzerRestController {
    private final MyWordFrequencyAnalyzer myWordFrequencyAnalyzer;

    public AnalyzerRestController(MyWordFrequencyAnalyzer myWordFrequencyAnalyzer) {
        this.myWordFrequencyAnalyzer = myWordFrequencyAnalyzer;
    }

    @PostMapping("highest")
    public ResponseEntity<Map<String, Object>> highest(@RequestBody Map<String, String> body) {
        int result = this.myWordFrequencyAnalyzer.calculateHighestFrequency(body.get("text"));
        return ResponseEntity.ok(Map.of("frequency", result));
    }

    @PostMapping("word")
    public ResponseEntity<Map<String, Object>> word(@RequestBody Map<String, String> body) {
        int result = this.myWordFrequencyAnalyzer.calculateFrequencyForWord(body.get("text"), body.get("word"));
        return ResponseEntity.ok(Map.of("frequency", result));
    }

    @PostMapping("top")
    public ResponseEntity<Map<String, Object>> top(@RequestBody Map<String, Object> body) {
        List<WordFrequency> result = this.myWordFrequencyAnalyzer.calculateMostFrequentNWords(
                (String) body.get("text"), (int) body.get("n"));
        return ResponseEntity.ok(Map.of("words", result));
    }
}
