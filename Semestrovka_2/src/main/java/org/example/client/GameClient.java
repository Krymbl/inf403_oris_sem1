package org.example.client;

import org.example.common.GameState;
import org.json.JSONObject;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.*;
import java.util.HashSet;
import java.util.Set;

public class GameClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 55555;

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private GamePanel gamePanel;
    private JFrame frame;

    private volatile GameState currentGameState;
    private volatile int playerId = 0;
    private volatile boolean connected = false;
    private volatile boolean gameStarted = false;

    private final Set<Integer> pressedKeys = new HashSet<>();

    public GameClient() {
        initializeGUI();
        connectToServer();
        startKeyPolling();
    }

    private void initializeGUI() {
        frame = new JFrame("Pizza Rush");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850, 700);
        frame.setResizable(false);

        gamePanel = new GamePanel(this);
        frame.add(gamePanel, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        gamePanel.requestFocusInWindow();
    }

    private void connectToServer() {
        new Thread(() -> {
            try {
                socket = new Socket(SERVER_HOST, SERVER_PORT);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                connected = true;
                System.out.println("Подключено к серверу");
                Thread receiver = new Thread(this::receiveMessages);
                receiver.setDaemon(true);
                receiver.start();

            } catch (IOException e) {
                SwingUtilities.invokeLater(() -> {
                    gamePanel.setStatus("Ошибка подключения");
                });
                connected = false;
            }
        }).start();
    }

    private void startKeyPolling() {
        Timer keyTimer = new Timer(30, e -> sendKeyState());
        keyTimer.start();
    }

    private void sendKeyState() {
        if (!connected || !gameStarted) {
            if (!pressedKeys.isEmpty()) {
                pressedKeys.clear();
            }
            return;
        }

        boolean up = pressedKeys.contains(KeyEvent.VK_W);
        boolean down = pressedKeys.contains(KeyEvent.VK_S);
        boolean left = pressedKeys.contains(KeyEvent.VK_A);
        boolean right = pressedKeys.contains(KeyEvent.VK_D);

        if (up) sendKeyCommand("UP", true);
        if (down) sendKeyCommand("DOWN", true);
        if (left) sendKeyCommand("LEFT", true);
        if (right) sendKeyCommand("RIGHT", true);
    }

    private void sendKeyCommand(String direction, boolean isPressed) {
        JSONObject message = new JSONObject();
        message.put("type", "KEY_STATE");
        message.put("direction", direction);
        message.put("pressed", isPressed);

        out.println(message.toString());
    }

    private void receiveMessages() {
        try {
            String message;
            while (connected && (message = in.readLine()) != null) {
                processServerMessage(message);
            }
        } catch (IOException e) {
            System.out.println("Соединение с сервером разорвано");
        } finally {
            disconnect();
        }
    }

    private void processServerMessage(String jsonMessage) {
        try {
            JSONObject json = new JSONObject(jsonMessage);
            String type = json.getString("type");

            switch (type) {
                case "CONNECT_RESPONSE":
                    playerId = json.getInt("playerId");
                    SwingUtilities.invokeLater(() -> {
                        frame.setTitle("Pizza Rush - Игрок " + playerId);
                        gamePanel.setStatus(json.getString("message"));
                    });
                    break;

                case "GAME_START":
                    gameStarted = true;
                    SwingUtilities.invokeLater(() -> {
                        gamePanel.setStatus("Игра началась! Собирайте пиццы!");
                    });
                    break;

                case "GAME_STATE":
                    JSONObject data = json.getJSONObject("data");
                    currentGameState = GameState.fromJson(data);

                    SwingUtilities.invokeLater(() -> {
                        gamePanel.setGameState(currentGameState);
                        gamePanel.repaint();
                    });
                    break;

                case "GAME_END":
                    gameStarted = false;

                    Integer winnerId;
                    if (json.has("winnerId") && !json.isNull("winnerId")) {
                        winnerId = json.getInt("winnerId");
                    } else {
                        winnerId = null;
                    }

                    String winnerText = (winnerId != null) ? "Победил Игрок " + winnerId : "Ничья!";

                    System.out.println("GameClient: Получено сообщение GAME_END. Победитель: " + winnerText);

                    SwingUtilities.invokeLater(() -> {
                        gamePanel.setStatus("Игра окончена! " + winnerText);
                        gamePanel.setGameOver(true);
                        gamePanel.repaint();

                        if (currentGameState != null) {
                            currentGameState.setGameActive(false);
                            currentGameState.setWinnerId(winnerId);
                        }
                    });
                    break;

                case "PLAYER_DISCONNECTED":
                    gameStarted = false;
                    SwingUtilities.invokeLater(() -> {
                        gamePanel.setStatus("Другой игрок отключился. Игра завершена.");
                        gamePanel.setGameOver(true);
                    });
                    break;
            }

        } catch (Exception e) {
            System.err.println("Ошибка обработки сообщения: " + e.getMessage());
        }
    }

    public void handleKeyPressed(int keyCode) {
        pressedKeys.add(keyCode);
    }

    public void handleKeyReleased(int keyCode) {
        pressedKeys.remove(keyCode);

        if (connected && gameStarted) {
            String direction = getDirectionFromKeyCode(keyCode);
            if (direction != null) {
                sendKeyCommand(direction, false);
            }
        }
    }

    private String getDirectionFromKeyCode(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_W:
                return "UP";
            case KeyEvent.VK_S:
                return "DOWN";
            case KeyEvent.VK_A:
                return "LEFT";
            case KeyEvent.VK_D:
                return "RIGHT";
            default:
                return null;
        }
    }

    public void disconnect() {
        connected = false;
        gameStarted = false;

        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
        }
    }

    public int getPlayerId() { return playerId; }
    public boolean isConnected() { return connected; }
    public boolean isGameStarted() { return gameStarted; }
    public GameState getGameState() { return currentGameState; }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GameClient();
        });
    }
}