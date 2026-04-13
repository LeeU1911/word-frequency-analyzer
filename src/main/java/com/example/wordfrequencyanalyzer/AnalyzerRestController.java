package com.example.wordfrequencyanalyzer;

import com.example.wordfrequencyanalyzer.dto.HighestFrequencyRequest;
import com.example.wordfrequencyanalyzer.dto.HighestFrequencyResponse;
import com.example.wordfrequencyanalyzer.dto.TopWordsRequest;
import com.example.wordfrequencyanalyzer.dto.TopWordsResponse;
import com.example.wordfrequencyanalyzer.dto.WordFrequencyRequest;
import com.example.wordfrequencyanalyzer.dto.WordFrequencyResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnalyzerRestController {
    private static final Logger log = LoggerFactory.getLogger(AnalyzerRestController.class);
    private final WordFrequencyAnalyzer analyzer;

    public AnalyzerRestController(WordFrequencyAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    @PostMapping("/highest")
    public ResponseEntity<HighestFrequencyResponse> highest(
            @Valid @RequestBody HighestFrequencyRequest request) {
        log.debug("Received highest frequency request for text length: {}", 
                request.text().length());
        int result = analyzer.calculateHighestFrequency(request.text());
        return ResponseEntity.ok(new HighestFrequencyResponse(result));
    }

    @PostMapping("/word")
    public ResponseEntity<WordFrequencyResponse> word(
            @Valid @RequestBody WordFrequencyRequest request) {
        log.debug("Received word frequency request for word: {}", request.word());
        int result = analyzer.calculateFrequencyForWord(request.text(), request.word());
        return ResponseEntity.ok(new WordFrequencyResponse(result));
    }

    @PostMapping("/top")
    public ResponseEntity<TopWordsResponse> top(
            @Valid @RequestBody TopWordsRequest request) {
        log.debug("Received top {} words request", request.n());
        var result = analyzer.calculateMostFrequentNWords(request.text(), request.n());
        return ResponseEntity.ok(new TopWordsResponse(result));
    }
}
