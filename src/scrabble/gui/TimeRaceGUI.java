
package scrabble.gui;

import scrabble.data.SimpleWordList;
import scrabble.data.HashMapWordList;
import scrabble.data.WordList;

import javax.swing.*;
import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Set;

public class TimeRaceGUI {

    private static ImageIcon racingGif;


    private static ImageIcon loadGif(String path) {
        java.net.URL url = TimeRaceGUI.class.getResource(path);
        return (url != null) ? new ImageIcon(url) : null;
    }

    private static void createAndShowGUI() {
        racingGif = loadGif("/_gif_animation__road_runner_running_by_ajc_artworks_ddqvz2z.gif");

        JFrame frame = new JFrame("TimeRace Benchmark");
        frame.getContentPane().setBackground(Color.YELLOW);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(5,5));

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Tile rack:"));
        JTextField rackField = new JTextField(10);
        inputPanel.add(rackField);
        JButton runButton = new JButton("Run Benchmark");
        inputPanel.add(runButton);
        JLabel gifLabel = new JLabel();
        inputPanel.add(gifLabel);
        frame.add(inputPanel, BorderLayout.NORTH);

        JTextArea outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);
        frame.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        JLabel simpleTimeLabel = new JLabel("SimpleWordList: ");
        JLabel hashTimeLabel   = new JLabel("HashMapWordList: ");
        JPanel resultPanel = new JPanel();
        resultPanel.setBackground(Color.YELLOW);
        resultPanel.add(simpleTimeLabel);
        resultPanel.add(hashTimeLabel);
        frame.add(resultPanel, BorderLayout.SOUTH);

        runButton.addActionListener((ActionEvent e) -> {
            String rack = rackField.getText().trim().toUpperCase();
            if (rack.isEmpty()) return;
            gifLabel.setIcon(racingGif);
            final int repetitions = 1000;
            new Thread(() -> {
                WordList simpleList = new SimpleWordList().initFromFile("wordlists/sowpods.txt");
                WordList hashList = new HashMapWordList().initFromFile("wordlists/sowpods.txt");

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

                final Set<String> finalHashResults = hashResults;
                final Set<String> finalSimpleResults = simpleResults;
                final double finalAvgSimpleMs = avgSimpleMs;
                final double finalAvgHashMs = avgHashMs;

                SwingUtilities.invokeLater(() -> {
                    outputArea.setText("Simple matches: " + finalSimpleResults + "\n");
                    outputArea.append("HashMap matches: " + finalHashResults + "\n\n");
                    outputArea.append(String.format("Simple total=%.2fms avg=%.4fms%n",
                            (finalAvgSimpleMs * repetitions), finalAvgSimpleMs));
                    outputArea.append(String.format("HashMap total=%.2fms avg=%.4fms%n",
                            (finalAvgHashMs * repetitions), finalAvgHashMs));
                    simpleTimeLabel.setText(String.format("Simple: avg=%.4fms", finalAvgSimpleMs));
                    hashTimeLabel.setText(String.format("HashMap: avg=%.4fms", finalAvgHashMs));
                    if (finalAvgSimpleMs < finalAvgHashMs) {
                        simpleTimeLabel.setForeground(Color.GREEN);
                        hashTimeLabel.setForeground(Color.RED);
                    } else {
                        simpleTimeLabel.setForeground(Color.RED);
                        hashTimeLabel.setForeground(Color.GREEN);
                    }
                    gifLabel.setIcon(null);
                });
            }).start();
        });

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TimeRaceGUI::createAndShowGUI);
    }
}