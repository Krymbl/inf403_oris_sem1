package org.example.common;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StatisticsManager {
    private static final String STATS_FILE = "Semestrovka_2/data/scores.json";
    private static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public static void saveGameResult(int player1Score, int player2Score,
                                      Integer winnerId, long gameDuration) {
        try {
            JSONObject gameResult = new JSONObject();
            gameResult.put("date", DATE_FORMAT.format(new Date()));
            gameResult.put("player1Score", player1Score);
            gameResult.put("player2Score", player2Score);
            gameResult.put("winner", winnerId != null ? "Player " + winnerId : "Draw");
            gameResult.put("duration", gameDuration);
            gameResult.put("durationFormatted", formatDuration(gameDuration));

            JSONArray allResults = loadAllResults();

            allResults.put(gameResult);
            if (allResults.length() > 20) {
                JSONArray newGameResult = new JSONArray();
                for (int i = allResults.length() - 20; i < allResults.length(); i++) {
                    newGameResult.put(allResults.get(i));
                }
                allResults = newGameResult;
            }

            File file = new File(STATS_FILE);
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(allResults.toString(2)); // 2 - отступы для читаемости
                System.out.println("Статистика сохранена в " + STATS_FILE);
            }

        } catch (Exception e) {
            System.err.println("Ошибка сохранения статистики: " + e.getMessage());
        }
    }

    public static JSONArray loadAllResults() {
        try {
            File file = new File(STATS_FILE);
            if (file.exists()) {
                String content = new String(Files.readAllBytes(file.toPath()));
                if (!content.trim().isEmpty()) {
                    return new JSONArray(content);
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки статистики: " + e.getMessage());
        }
        return new JSONArray();
    }


    private static String formatDuration(long milliseconds) {
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%02d:%02d", minutes, seconds);

    }

    public static void main(String[] args) {
        saveGameResult(10, 7, 1, 120000);
        saveGameResult(5, 5, null, 115000);
        saveGameResult(8, 12, 2, 118000);
    }
}