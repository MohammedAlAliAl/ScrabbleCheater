package scrabble.util;
import java.util.Arrays;
import java.util.Objects;

public class Permutation {
	private final String word;

	public Permutation(String word) {

		this.word = word.toUpperCase();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getNormalized());

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof Permutation)) return false;
		Permutation other = (Permutation) obj;
		return this.getNormalized().equals(other.getNormalized());
	}

	@Override
	public String toString() {
		return getWord() + " / " + getNormalized();
	}

	public String getNormalized() {
		char[] chars = word.toCharArray();
		Arrays.sort(chars);
		return new String(chars);

	}

	public String getWord() {
		// TBD: implement this method
		return word;
	}

	public int length() {
		return word.length();
	}

}
