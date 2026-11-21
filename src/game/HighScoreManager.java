package game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

/*
    Saves and loads the top 5 scores to a local text file (highscores.txt).
*/
public class HighScoreManager {

    private static final String SCORE_FILE = "highscores.txt";
    private static final int MAX_SCORES = 5;

    // Loads all saved scores, sorted highest first. Returns an empty list if there is no file yet.
    public ArrayList<Integer> loadScores() {
        ArrayList<Integer> scores = new ArrayList<>();
        File file = new File(SCORE_FILE);
        if (!file.exists()) {
            return scores;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                try {
                    scores.add(Integer.parseInt(line));
                } catch (NumberFormatException e) {
                    // skip a corrupted line instead of losing every score
                }
            }
        } catch (IOException e) {
            System.out.println("Could not load scores: " + e.getMessage());
        }

        scores.sort(Collections.reverseOrder());
        return scores;
    }

    // Adds a new score, keeps only the top 5 and writes them back to the file.
    public void saveScore(int newScore) {
        ArrayList<Integer> scores = loadScores();
        scores.add(newScore);
        scores.sort(Collections.reverseOrder());

        while (scores.size() > MAX_SCORES) {
            scores.remove(scores.size() - 1);
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(SCORE_FILE))) {
            for (int score : scores) {
                writer.println(score);
            }
        } catch (IOException e) {
            System.out.println("Could not save scores: " + e.getMessage());
        }
    }

    // True if the score would make it into the top 5.
    public boolean isHighScore(int score) {
        ArrayList<Integer> scores = loadScores();
        if (scores.size() < MAX_SCORES) {
            return true;
        }
        return score > scores.get(scores.size() - 1);
    }
}
