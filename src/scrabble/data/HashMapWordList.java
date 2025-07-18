package scrabble.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Scanner;

import scrabble.util.Permutation;

public class HashMapWordList implements WordList {

    private final Map<String, Set<String>> wordMap = new HashMap<>();

    @Override
    public boolean add(String word) {

        if (word == null || word.trim().isEmpty()) return false;
        word = word.trim().toUpperCase();
        String normalized = new Permutation(word).getNormalized();
        return wordMap.computeIfAbsent(normalized, k -> new HashSet<>()).add(word);
    }

    @Override
    public boolean addAll(Collection<String> words) {
        boolean changed = false;
        for (String word : words) {
            changed |= add(word);
        }
        return changed;
    }

    @Override
    public int size() {
        return wordMap.values().stream().mapToInt(Set::size).sum();
    }

    @Override
    public WordList initFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
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

    @Override
    public Set<String> validWordsUsingAllTiles(String tileRack) {
        if (tileRack == null || tileRack.trim().isEmpty()) {
            return Collections.emptySet();
        }

        tileRack = tileRack.trim().toUpperCase();
        if (tileRack.length() != 7) {
            return Collections.emptySet();
        }

        String normalized = new Permutation(tileRack).getNormalized();
        return wordMap.getOrDefault(normalized, Collections.emptySet());
    }

    @Override
    public Set<String> allValidWords(String tileRack) {
        throw new UnsupportedOperationException("Not implemented for this assignment.");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        WordList wordList = new HashMapWordList();
        wordList.initFromFile("wordlists/sowpods.txt");

        while (true) {
            System.out.print("Enter your tile rack (or type 'exit' to quit): ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equals("EXIT")) {
                System.out.println("bye!");
                break;
            }

            Set<String> results = wordList.validWordsUsingAllTiles(input);
            System.out.println("Matching words:");
            for (String word : results) {
                System.out.println(word);
            }
            System.out.println();
        }
    }
}