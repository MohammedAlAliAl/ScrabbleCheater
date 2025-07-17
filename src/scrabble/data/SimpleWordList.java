package scrabble.data;

import scrabble.util.Permutation; // Import der Permutation Klasse für Buchstabenvergleiche

import java.io.BufferedReader; // Import für effizientes Lesen von Dateien
import java.io.FileReader; // Import für Dateizugriff
import java.io.IOException; // Import für Fehlerbehandlung bei Dateizugriffen
import java.util.Collection; // Import für Collection Interface
import java.util.HashSet; // Import für HashSet Datenstruktur
import java.util.Set; // Import für Set Interface

public class SimpleWordList implements WordList {

	private Set<String> words; // Speichert alle Wörter in einem HashSet für schnelle Suche

	public SimpleWordList() {
		this.words = new HashSet<>(); // Initialisiert das HashSet für Wörter
	}

	@Override
	public Set<String> validWordsUsingAllTiles(String tileRackPart) {
		Set<String> validWords = new HashSet<>(); // Erstellt LEERES Set für gefundene gültige Wörter

		// Prüft zuerst ob die Eingabe null ist
		if (tileRackPart == null) {
			return validWords; // Gibt LEERES Set zurück (= keine gültigen Wörter)
		}
		// Prüft dann ob die Eingabe leer oder nur Leerzeichen enthält
		if (tileRackPart.trim().length() == 0) {
			return validWords; // Gibt LEERES Set zurück (= keine gültigen Wörter)
		}

		// Erstellt Permutation Objekt für die Eingabe-Buchstaben
		Permutation inputPermutation = new Permutation(tileRackPart);

		// Durchläuft alle gespeicherten Wörter in der Wortliste
		for (String word : words) {
			// Betrachtet nur Wörter mit gleicher Länge wie die Eingabe
			if (word.length() == tileRackPart.length()) {
				Permutation wordPermutation = new Permutation(word); // Erstellt Permutation für aktuelles Wort
				// Prüft ob das Wort eine Permutation der Eingabe-Buchstaben ist
				if (inputPermutation.equals(wordPermutation)) {
					validWords.add(word); // Fügt gültiges Wort zum Ergebnis hinzu
				}
			}
		}

		return validWords; // Gibt Set mit allen gefundenen gültigen Wörtern zurück
	}

	@Override
	public Set<String> allValidWords(String tileRack) {
		// TODO: This would need to be implemented using SubSets utility
		// to generate all subsets of the tile rack and then find valid words
		return null;
	}

	@Override
	public boolean add(String word) {
		if (word == null || word.trim().isEmpty()) { // Prüft auf null oder leere Wörter
			return false; // Gibt false zurück wenn Wort ungültig
		}
		return words.add(word.toLowerCase().trim()); // Fügt Wort in Kleinbuchstaben und ohne Leerzeichen hinzu
	}

	@Override
	public boolean addAll(Collection<String> words) {
		if (words == null) { // Prüft ob Collection null ist
			return false; // Gibt false zurück wenn Collection ungültig
		}
		boolean modified = false; // Flag um zu verfolgen ob Änderungen vorgenommen wurden
		for (String word : words) { // Durchläuft alle Wörter in der Collection
			if (add(word)) { // Versucht jedes Wort hinzuzufügen
				modified = true; // Setzt Flag auf true wenn mindestens ein Wort hinzugefügt wurde
			}
		}
		return modified; // Gibt zurück ob die Wortliste verändert wurde
	}

	@Override
	public int size() {
		return words.size(); // Gibt die Anzahl der gespeicherten Wörter zurück
	}

	@Override
	public WordList initFromFile(String fileName) {
		SimpleWordList wordList = new SimpleWordList(); // Erstellt neue SimpleWordList Instanz

		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) { // Öffnet Datei mit try-with-resources
			String line; // Variable für aktuelle Zeile
			while ((line = reader.readLine()) != null) { // Liest Datei zeilenweise bis zum Ende
				line = line.trim().toLowerCase(); // Entfernt Leerzeichen und konvertiert zu Kleinbuchstaben
				if (!line.isEmpty() && line.length() >= MIN_WORD_LENGTH) { // Prüft ob Zeile nicht leer und lang genug ist
					// Prüft ob Wort nur aus Buchstaben a-z besteht
					if (line.matches("[a-z]+")) {
						wordList.add(line); // Fügt gültiges Wort zur neuen Wortliste hinzu
					}
				}
			}
		} catch (IOException e) { // Fängt Fehler beim Dateizugriff ab
			System.err.println("Error reading file: " + fileName); // Gibt Fehlermeldung aus
			e.printStackTrace(); // Zeigt detaillierte Fehlerinformationen
		}

		return wordList; // Gibt die neue Wortliste mit allen geladenen Wörtern zurück
	}
}