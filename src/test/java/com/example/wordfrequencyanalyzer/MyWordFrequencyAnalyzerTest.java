package com.example.wordfrequencyanalyzer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MyWordFrequencyAnalyzerTest {
    @Test
    public void testCalculateHighestFrequency(){
        MyWordFrequencyAnalyzer analyzer = new MyWordFrequencyAnalyzer();
        assertThat(analyzer.calculateHighestFrequency("the cat is the cat")).isEqualTo(2);
    }

    @Test
    public void testCalculateFrequencyForWord(){
        MyWordFrequencyAnalyzer analyzer = new MyWordFrequencyAnalyzer();
        assertThat(analyzer.calculateFrequencyForWord("the cat is the cat", "is")).isEqualTo(1);
        assertThat(analyzer.calculateFrequencyForWord("the cat is the cat", "CAT")).isEqualTo(2);
    }

    @Test
    public void testCalculateMostFrequentNWords(){
        MyWordFrequencyAnalyzer analyzer = new MyWordFrequencyAnalyzer();
        Assertions.assertThat(analyzer.calculateMostFrequentNWords("the cat walks over The staircase", 3))
                .containsExactly(new MyWordFrequency("the", 2), new MyWordFrequency("cat", 1), new MyWordFrequency("over", 1));
    }
}
