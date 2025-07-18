package scrabble.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SimpleWordListTest {

    private SimpleWordList wordList;

    @BeforeEach
    void setUp() {
        wordList = new SimpleWordList();
        wordList.add("abstrac");
        wordList.add("cabtras");
        wordList.add("carbats");
        wordList.add("banana");
        wordList.add("testing");
    }

    @Test
    void testValidWordsUsingAllTiles() {
        Set<String> result = wordList.validWordsUsingAllTiles("abstrac");

        assertTrue(result.contains("abstrac"));
        assertTrue(result.contains("cabtras"));
        assertTrue(result.contains("carbats"));
        assertFalse(result.contains("banana"));
        assertEquals(3, result.size());
    }

    @Test
    void testEmptyOrInvalidInputReturnsEmptySet() {
        assertTrue(wordList.validWordsUsingAllTiles(null).isEmpty());
        assertTrue(wordList.validWordsUsingAllTiles("abc").isEmpty());
        assertTrue(wordList.validWordsUsingAllTiles("").isEmpty());
    }

    @Test
    void testAddAndSize() {
        SimpleWordList wl = new SimpleWordList();
        wl.add("one");
        wl.add("two");
        wl.add("three");
        assertEquals(3, wl.size());
    }
    @Test
    void testNullInputReturnsEmptySet() {
        assertTrue(wordList.validWordsUsingAllTiles(null).isEmpty());
    }

    @Test
    void testInvalidLengthReturnsEmptySet() {
        assertTrue(wordList.validWordsUsingAllTiles("abc").isEmpty());
    }
}
