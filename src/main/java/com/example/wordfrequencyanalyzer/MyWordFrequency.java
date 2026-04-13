package com.example.wordfrequencyanalyzer;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MyWordFrequency(
        @JsonProperty("word") String word,
        @JsonProperty("frequency") int frequency
) implements WordFrequency {
    
    @Override
    public String getWord() {
        return word();
    }
    
    @Override
    public int getFrequency() {
        return frequency();
    }
    
    public MyWordFrequency {
        if (word == null) {
            throw new IllegalArgumentException("word cannot be null");
        }
    }
}
