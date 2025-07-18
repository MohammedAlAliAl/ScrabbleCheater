package scrabble.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class HashMapWordListTest {

    private HashMapWordList wordList;

    @BeforeEach
    public void setUp() {
        wordList = new HashMapWordList();
        wordList.add("abstrac");
        wordList.add("cabtras");
        wordList.add("scrabat");
        wordList.add("banana");
        wordList.add("test");
    }

    @Test
    public void testValidWordsUsingAllTiles() {
        Set<String> result = wordList.validWordsUsingAllTiles("abstrac");

        assertTrue(result.contains("abstrac"));
        assertTrue(result.contains("cabtras"));
        assertFalse(result.contains("banana"));
        assertFalse(result.contains("test"));
        assertEquals(3, result.size());
    }

    @Test
    public void testInvalidLengthInputReturnsEmptySet() {
        Set<String> result = wordList.validWordsUsingAllTiles("abc");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testNullInputReturnsEmptySet() {
        Set<String> result = wordList.validWordsUsingAllTiles(null);
        assertTrue(result.isEmpty());
    }
}
