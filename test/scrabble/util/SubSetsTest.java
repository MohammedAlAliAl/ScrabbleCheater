package scrabble.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubSetsTest {

	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ "a", new String[] {} },
				{ "ab", new String[] { "ab" } },
				{
						"java",
						new String[] { "aj", "av", "jv", "ajv", "aajv", "aaj", "aav", "aa" }
				},
				{
						"abcd",
						new String[] { "ab", "ac", "ad", "bc", "bd", "cd", "abc", "abd", "acd", "bcd", "abcd" }
				}
		});
	}

	@ParameterizedTest
	@MethodSource("data")
	public void testComputeSubSets(String input, String[] expectedArray) {
		Set<String> expected = new HashSet<>(Arrays.asList(expectedArray));
		Set<String> actual = SubSets.getSubSets(input);assertEquals(expected, actual);
	}
}
