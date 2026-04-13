package com.example.wordfrequencyanalyzer;

import org.springframework.http.HttpStatusCode;
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
    public ResponseEntity<Integer> highest(@RequestBody Map<String, String> body) {
        return new ResponseEntity<>(
                this.myWordFrequencyAnalyzer.calculateHighestFrequency(body.get("text")),
                HttpStatusCode.valueOf(200)
        );
    }

    @PostMapping("word")
    public ResponseEntity<Integer> word(@RequestBody Map<String, String> body) {
        return new ResponseEntity<>(
                this.myWordFrequencyAnalyzer.calculateFrequencyForWord(body.get("text"), body.get("word")),
                HttpStatusCode.valueOf(200)
        );
    }

    @PostMapping("top")
    public ResponseEntity<List<WordFrequency>> top(@RequestBody Map<String, Object> body) {
        return new ResponseEntity<>(
                this.myWordFrequencyAnalyzer.calculateMostFrequentNWords((String) body.get("text"), (int) body.get("n")),
                HttpStatusCode.valueOf(200)
        );
    }
}
