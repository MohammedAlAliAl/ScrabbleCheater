package scrabble.util;

import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;

public class SubSets {

	public static Set<String> getSubSets(String str) {
		Set<String> result = new HashSet<>();
		generateSubsets("", str, result);
		return result;
	}

	private static void generateSubsets(String prefix, String remaining, Set<String> result) {
		if (prefix.length() >= 2) {
			char[] chars = prefix.toCharArray();
			Arrays.sort(chars);
			result.add(new String(chars));
		}
		for (int i = 0; i < remaining.length(); i++) {
			generateSubsets(prefix + remaining.charAt(i), remaining.substring(i + 1), result);
		}
	}
}
