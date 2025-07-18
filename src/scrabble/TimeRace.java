
import scrabble.data.SimpleWordList;
import scrabble.data.HashMapWordList;
import scrabble.data.WordList;


import java.util.Scanner;
import java.util.Set;

public class TimeRace {
    public static void main(String[] args) {
        final String filePath = "wordlists/sowpods.txt";
        final int repetitions = 1000;
        Scanner scanner = new Scanner(System.in);

        WordList simpleList = new SimpleWordList().initFromFile(filePath);
        WordList hashList = new HashMapWordList().initFromFile(filePath);

        while (true) {
            System.out.print("Enter a tile rack (or 'exit' to quit): ");
            String rack = scanner.nextLine().trim().toUpperCase();
            if ("EXIT".equals(rack)) {
                System.out.println("Goodbye!");
                break;
            }

            long startSimple = System.nanoTime();
            Set<String> simpleResults = null;
            for (int i = 0; i < repetitions; i++) {
                simpleResults = simpleList.validWordsUsingAllTiles(rack);
            }
            long endSimple = System.nanoTime();

            long startHash = System.nanoTime();
            Set<String> hashResults = null;
            for (int i = 0; i < repetitions; i++) {
                hashResults = hashList.validWordsUsingAllTiles(rack);
            }
            long endHash = System.nanoTime();

            double totalSimpleMs = (endSimple - startSimple) / 1_000_000.0;
            double avgSimpleMs = totalSimpleMs / repetitions;
            double totalHashMs = (endHash - startHash) / 1_000_000.0;
            double avgHashMs = totalHashMs / repetitions;

            System.out.println("Matching words: " + hashResults);
            System.out.printf("SimpleWordList: total = %.2f ms, average = %.4f ms%n", totalSimpleMs, avgSimpleMs);
            System.out.printf("HashMapWordList: total = %.2f ms, average = %.4f ms%n%n", totalHashMs, avgHashMs);
        }
        scanner.close();
    }
}
