package com.example.wordfrequencyanalyzer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("MyWordFrequencyAnalyzer Tests")
class MyWordFrequencyAnalyzerTest {
    private final MyWordFrequencyAnalyzer analyzer = new MyWordFrequencyAnalyzer(new WordFrequencyCacheService());

    @Nested
    @DisplayName("calculateHighestFrequency")
    class CalculateHighestFrequencyTests {

        @Test
        void returnsCorrectHighestFrequency() {
            assertThat(analyzer.calculateHighestFrequency("the cat is the cat")).isEqualTo(2);
        }

        @Test
        void caseInsensitive() {
            assertThat(analyzer.calculateHighestFrequency("The THE the")).isEqualTo(3);
        }

        @Test
        void singleWord() {
            assertThat(analyzer.calculateHighestFrequency("hello")).isEqualTo(1);
        }

        @Test
        void allSameWords() {
            assertThat(analyzer.calculateHighestFrequency("word word word word")).isEqualTo(4);
        }

        @Test
        void withPunctuation() {
            assertThat(analyzer.calculateHighestFrequency("hello! hello? hello.")).isEqualTo(3);
        }

        @Test
        void emptyString() {
            assertThat(analyzer.calculateHighestFrequency("")).isZero();
        }

        @Test
        void blankString() {
            assertThat(analyzer.calculateHighestFrequency("   ")).isZero();
        }

        @Test
        void nullText() {
            assertThat(analyzer.calculateHighestFrequency(null)).isZero();
        }

        @Test
        void noValidWords() {
            assertThat(analyzer.calculateHighestFrequency("!@#$%^&*()")).isZero();
        }

        @Test
        void numbersMixedWithText() {
            assertThat(analyzer.calculateHighestFrequency("test123 test test")).isEqualTo(3);
        }

        @Test
        void withNewlinesAndTabs() {
            assertThat(analyzer.calculateHighestFrequency("hello\nhello\tworld")).isEqualTo(2);
        }
    }

    @Nested
    @DisplayName("calculateFrequencyForWord")
    class CalculateFrequencyForWordTests {

        @Test
        void returnsCorrectFrequency() {
            assertThat(analyzer.calculateFrequencyForWord("the cat is the cat", "the")).isEqualTo(2);
        }

        @Test
        void caseInsensitive() {
            assertThat(analyzer.calculateFrequencyForWord("the cat is the cat", "CAT")).isEqualTo(2);
        }

        @Test
        void wordNotFound() {
            assertThat(analyzer.calculateFrequencyForWord("hello world", "missing")).isZero();
        }

        @Test
        void emptyText() {
            assertThat(analyzer.calculateFrequencyForWord("", "hello")).isZero();
        }

        @Test
        void nullText() {
            assertThat(analyzer.calculateFrequencyForWord(null, "hello")).isZero();
        }

        @Test
        void nullWord() {
            assertThat(analyzer.calculateFrequencyForWord("hello world", null)).isZero();
        }

        @Test
        void singleCharacter() {
            assertThat(analyzer.calculateFrequencyForWord("a b c a d a", "a")).isEqualTo(3);
        }

        @Test
        void withPunctuation() {
            assertThat(analyzer.calculateFrequencyForWord("hello! hello? hello.", "hello")).isEqualTo(3);
        }

        @ParameterizedTest
        @MethodSource("provideFrequencyTestCases")
        void parameterized(String text, String word, int expected) {
            assertThat(analyzer.calculateFrequencyForWord(text, word)).isEqualTo(expected);
        }

        private static Stream<Arguments> provideFrequencyTestCases() {
            return Stream.of(
                    Arguments.of("hello world", "hello", 1),
                    Arguments.of("hello world hello", "HELLO", 2),
                    Arguments.of("a a a a a", "a", 5),
                    Arguments.of("test 123 test", "test", 2),
                    Arguments.of("hello-world hello", "hello", 2)
            );
        }
    }

    @Nested
    @DisplayName("calculateMostFrequentNWords")
    class CalculateMostFrequentNWordsTests {

        @Test
        void returnsTopNWordsSortedByFrequency() {
            var result = analyzer.calculateMostFrequentNWords("the cat walks over The staircase", 3);
            assertThat(result).containsExactly(
                    new MyWordFrequency("the", 2),
                    new MyWordFrequency("cat", 1),
                    new MyWordFrequency("over", 1)
            );
        }

        @Test
        void sortedAlphabeticallyForTies() {
            var result = analyzer.calculateMostFrequentNWords("cat bat rat cat", 3);
            assertThat(result).containsExactly(
                    new MyWordFrequency("cat", 2),
                    new MyWordFrequency("bat", 1),
                    new MyWordFrequency("rat", 1)
            );
        }

        @Test
        void nGreaterThanAvailable() {
            var result = analyzer.calculateMostFrequentNWords("hello world", 10);
            assertThat(result).hasSize(2);
        }

        @Test
        void nIsZero() {
            assertThat(analyzer.calculateMostFrequentNWords("hello world", 0)).isEmpty();
        }

        @Test
        void nIsNegative() {
            assertThat(analyzer.calculateMostFrequentNWords("hello world", -1)).isEmpty();
        }

        @Test
        void emptyText() {
            assertThat(analyzer.calculateMostFrequentNWords("", 5)).isEmpty();
        }

        @Test
        void nullText() {
            assertThat(analyzer.calculateMostFrequentNWords(null, 5)).isEmpty();
        }

        @Test
        void noValidWords() {
            assertThat(analyzer.calculateMostFrequentNWords("!@#$%^&*()", 5)).isEmpty();
        }

        @Test
        void singleWord() {
            var result = analyzer.calculateMostFrequentNWords("hello", 3);
            assertThat(result).containsExactly(new MyWordFrequency("hello", 1));
        }

        @Test
        void withPunctuation() {
            var result = analyzer.calculateMostFrequentNWords("apple! banana, apple. cherry: apple", 2);
            assertThat(result).containsExactly(
                    new MyWordFrequency("apple", 3),
                    new MyWordFrequency("banana", 1)
            );
        }

        @Test
        void withNewlines() {
            var result = analyzer.calculateMostFrequentNWords("one\none\ntwo", 3);
            assertThat(result).containsExactly(
                    new MyWordFrequency("one", 2),
                    new MyWordFrequency("two", 1)
            );
        }
    }

    @Nested
    @DisplayName("MyWordFrequency Record")
    class MyWordFrequencyRecordTests {

        @Test
        void gettersWork() {
            var wf = new MyWordFrequency("test", 5);
            assertThat(wf.word()).isEqualTo("test");
            assertThat(wf.frequency()).isEqualTo(5);
            assertThat(wf.getWord()).isEqualTo("test");
            assertThat(wf.getFrequency()).isEqualTo(5);
        }

        @Test
        void equality() {
            var wf1 = new MyWordFrequency("test", 5);
            var wf2 = new MyWordFrequency("test", 5);
            var wf3 = new MyWordFrequency("test", 6);
            assertThat(wf1).isEqualTo(wf2);
            assertThat(wf1).isNotEqualTo(wf3);
        }

        @Test
        void nullWordThrows() {
            assertThatThrownBy(() -> new MyWordFrequency(null, 1))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("word cannot be null");
        }
    }
}
