package scrabble.data;

import scrabble.util.Permutation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Collection;
import java.util.Set;

public class SimpleWordList implements WordList {

	private final Set<String> wordSet = new HashSet<>();

	@Override
	public Set<String> validWordsUsingAllTiles(String tileRackPart) {
		Set<String> result = new HashSet<>();
		if (tileRackPart == null || tileRackPart.trim().isEmpty()) {
			return result;
		}

		Permutation tileRackPerm = new Permutation(tileRackPart);
		for (String word : wordSet) {
			if (word.length() == tileRackPart.length()) {
				Permutation wordPerm = new Permutation(word);
				if (tileRackPerm.equals(wordPerm)) {
					result.add(word);
				}
			}
		}
		return result;
	}

	@Override
	public Set<String> allValidWords(String tileRack) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(String word) {
		return wordSet.add(word);
	}

	@Override
	public boolean addAll(Collection<String> words) {
		return wordSet.addAll(words);
	}

	@Override
	public int size() {
		return wordSet.size();
	}

	@Override
	public WordList initFromFile(String fileName) {
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String word = line.trim().toUpperCase();
				if (!word.isEmpty()) {
					add(word);
				}
			}
		} catch (IOException e) {
			System.err.println("Fehler beim Einlesen der Datei: " + e.getMessage());
		}
		return this;
	}

}
