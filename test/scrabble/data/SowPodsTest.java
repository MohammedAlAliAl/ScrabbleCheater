package scrabble.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class SowPodsTest {

	static WordList wl;

	@BeforeAll
	public static void createWordList() {
		wl = new SimpleWordList().initFromFile("wordlists/sowpods.txt");
	}

	static Stream<org.junit.jupiter.params.provider.Arguments> data() {
		return Stream.of(
				org.junit.jupiter.params.provider.Arguments.of(
						"gustable",
						new String[] { "gustable" },
						new String[] { "gustable", "blue", "slab", "bug", "tub", "bugle" } // verkürzt für Beispiel
				),
				org.junit.jupiter.params.provider.Arguments.of(
						"siszymo",
						new String[] { "zymosis" },
						new String[] { "zymosis", "miss", "moss", "soy" }
				)
		);
	}

	@ParameterizedTest
	@MethodSource("data")
	public void sizeShouldGiveTotalNumberOfStoredWords(String tileRack, String[] permutations,
													   String[] validSuggestions) {
		assertEquals(267754, wl.size(), tileRack);
	}

	@ParameterizedTest
	@MethodSource("data")
	public void shouldReturnCorrectSuggestions(String tileRack, String[] permutations,
											   String[] validSuggestions) {
		Set<String> actual = wl.allValidWords(tileRack);
		Set<String> expected = new HashSet<>(Arrays.asList(validSuggestions));
		assertEquals(expected, actual, "TileRack: " + tileRack);
	}
}
