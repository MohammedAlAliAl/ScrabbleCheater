package scrabble.cli;

import scrabble.data.HashMapWordList;
import scrabble.data.SimpleWordList;
import scrabble.data.WordList;

import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        // Choose implementation
        System.out.print("Use [1] SimpleWordList or [2] HashMapWordList? ");
        int choice = Integer.parseInt(scanner.nextLine());

        WordList wordList;
        if (choice == 1) {
            wordList = new SimpleWordList();
        } else {
            wordList = new HashMapWordList();
        }

        wordList.initFromFile("resources/words.txt"); // adjust path if needed

        System.out.print("Enter a 7-letter tile rack: ");
        String tiles = scanner.nextLine().toLowerCase();

        Set<String> result = wordList.validWordsUsingAllTiles(tiles);

        System.out.println("Matching words:");
        for (String word : result) {
            System.out.println(word);
        }
    }
}
