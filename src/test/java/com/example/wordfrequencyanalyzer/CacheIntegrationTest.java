package com.example.wordfrequencyanalyzer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("Caffeine Cache Tests")
class CacheIntegrationTest {

    @Autowired(required = false)
    private CacheManager cacheManager;

    @Test
    void cacheManagerIsPresent() {
        assertThat(cacheManager).isNotNull();
    }

    @Test
    void cacheStoresResults(@Autowired WordFrequencyAnalyzer analyzer) {
        if (cacheManager == null) {
            return;
        }
        
        var cache = cacheManager.getCache("wordFrequency");
        assertThat(cache).isNotNull();
        
        String text = "hello world hello";
        cache.clear();
        
        analyzer.calculateHighestFrequency(text);
        assertThat(cache.get(text)).isNotNull();
        
        analyzer.calculateFrequencyForWord(text, "hello");
        assertThat(cache.get(text)).isNotNull();
        
        analyzer.calculateMostFrequentNWords(text, 2);
        assertThat(cache.get(text)).isNotNull();
    }

    @Test
    void sameTextReturnsCachedResult(@Autowired WordFrequencyAnalyzer analyzer) {
        if (cacheManager == null) {
            return;
        }
        
        var cache = cacheManager.getCache("wordFrequency");
        String text = "test text test";
        analyzer.calculateHighestFrequency(text);
        
        assertThat(cache.get(text)).isNotNull();
        assertThat(cache.get(text).get()).isInstanceOf(java.util.Map.class);
    }
}
