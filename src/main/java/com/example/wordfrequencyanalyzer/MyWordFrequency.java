package com.example.wordfrequencyanalyzer;

import java.util.Objects;

public class MyWordFrequency implements WordFrequency {
    private String word;
    private int frequency;

    public MyWordFrequency(){
        this.word = null;
        this.frequency = 0;
    }

    public MyWordFrequency(String word, int frequency) {
        this.word = word;
        this.frequency = frequency;
    }

    @Override
    public String getWord() {
        return this.word;
    }

    @Override
    public int getFrequency() {
        return this.frequency;
    }

    @Override
    public String toString() {
        return "MyWordFrequency{" +
                "word='" + word + '\'' +
                ", frequency=" + frequency +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyWordFrequency that = (MyWordFrequency) o;
        return frequency == that.frequency && Objects.equals(word, that.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, frequency);
    }
}
