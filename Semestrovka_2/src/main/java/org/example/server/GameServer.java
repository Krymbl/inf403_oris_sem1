package org.example.server;

import org.example.common.GameState;
import org.json.JSONObject;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class GameServer {
    private static final int PORT = 55555;
    private static final String HOST = "localhost";

    private ServerSocket serverSocket;
    private GameEngine gameEngine;
    private ExecutorService threadPool;
    private volatile int connectedPlayers = 0;

    private final ConcurrentHashMap<Integer, ClientHandler> clients;
    private volatile boolean gameActive = false;

    public GameServer() {
        this.clients = new ConcurrentHashMap<>();
        this.threadPool = Executors.newCachedThreadPool();
        this.gameEngine = new GameEngine();

        System.out.println("Сервер Pizza запускается...");
        System.out.println("Ожидание подключения на " + HOST + ":" + PORT);
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Сервер запущен");

            gameEngine.start();
            startGameStateBroadcaster();

            System.out.println("Ожидание подключений...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                handleNewConnection(clientSocket);
            }

        } catch (IOException e) {
            System.err.println("Ошибка сервера: " + e.getMessage());
        } finally {
            stop();
        }
    }

    private void handleNewConnection(Socket clientSocket) {
        if (connectedPlayers >= 2) {
            try {
                JSONObject rejectMsg = new JSONObject();
                rejectMsg.put("type", "SERVER_REJECT");
                rejectMsg.put("message", "Сервер переполнен");
                new PrintWriter(clientSocket.getOutputStream(), true).println(rejectMsg.toString());
                clientSocket.close();
            } catch (IOException e) {}
            return;
        }

        connectedPlayers++;
        int playerId = connectedPlayers;

        System.out.println("Подключился игрок " + playerId);

        ClientHandler clientHandler = new ClientHandler(clientSocket, playerId, this);
        clients.put(playerId, clientHandler);

        threadPool.execute(clientHandler);

        sendConnectResponse(playerId);

        if (connectedPlayers == 2) {
            startGame();
        }
    }

    private void sendConnectResponse(int playerId) {
        JSONObject message = new JSONObject();
        message.put("type", "CONNECT_RESPONSE");
        message.put("playerId", playerId);

        if (connectedPlayers == 1) {
            message.put("message", "Вы Игрок 1. Ожидайте второго игрока...");
        } else {
            message.put("message", "Вы Игрок 2. Игра скоро начнется!");
        }

        ClientHandler handler = clients.get(playerId);
        if (handler != null) {
            handler.sendMessage(message.toString());
        }
    }

    private void startGame() {
        System.out.println("Оба игрока подключены! Начинаем игру!");
        gameActive = true;

        gameEngine.startGame();

        JSONObject startMessage = new JSONObject();
        startMessage.put("type", "GAME_START");
        startMessage.put("message", "Игра началась! Собирайте пиццы!");
        startMessage.put("gameTime", GameState.TOTAL_GAME_TIME);

        broadcastMessage(startMessage.toString());
    }

    private void startGameStateBroadcaster() {
        Thread broadcaster = new Thread(() -> {
            boolean sentGameEnd = false;

            while (true) {
                try {
                    Thread.sleep(30);

                    if (connectedPlayers > 0) {
                        GameState state = gameEngine.getGameState();
                        if (state != null) {

                            if (gameEngine.isGameFinished() && !sentGameEnd) {
                                System.out.println("Broadcaster: Игра завершена, отправляю GAME_END");

                                gameActive = false;
                                sentGameEnd = true;

                                JSONObject endMessage = new JSONObject();
                                endMessage.put("type", "GAME_END");
                                Integer winnerId = state.getWinnerId();
                                if (winnerId != null) {
                                    endMessage.put("winnerId", winnerId);
                                } else {
                                    endMessage.put("winnerId", JSONObject.NULL);
                                }
                                broadcastMessage(endMessage.toString());

                                System.out.println("Broadcaster: GAME_END отправлен");
                                continue;
                            }

                            if (gameActive) {
                                JSONObject message = new JSONObject();
                                message.put("type", "GAME_STATE");
                                message.put("data", state.toJson());
                                broadcastMessage(message.toString());
                            }

                        }
                    }

                } catch (InterruptedException e) {
                    break;
                } catch (Exception e) {
                    System.err.println("Ошибка broadcaster: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });

        broadcaster.setDaemon(true);
        broadcaster.start();
    }


    public void handlePlayerCommand(int playerId, String direction, boolean pressed) {
        if (!gameActive) {
            System.out.println("Команда игнорирована: игра не активна на сервере");
            return;
        }

        System.out.println("Игрок " + playerId + " " + (pressed ? "нажал" : "отпустил") + ": " + direction);
        gameEngine.addPlayerCommand(playerId, direction, pressed);
    }

    public void handleClientDisconnect(int playerId) {
        System.out.println("Игрок " + playerId + " отключился");

        ClientHandler handler = clients.remove(playerId);
        if (handler != null) {
            handler.disconnect();
        }

        connectedPlayers--;
        gameActive = false;

        JSONObject disconnectMessage = new JSONObject();
        disconnectMessage.put("type", "PLAYER_DISCONNECTED");
        disconnectMessage.put("message", "Игрок " + playerId + " отключился");
        broadcastMessage(disconnectMessage.toString());

        gameEngine.resetGame();
    }

    private void broadcastMessage(String message) {
        for (ClientHandler client : clients.values()) {
            if (client.isConnected()) {
                client.sendMessage(message);
            }
        }
    }

    public void stop() {
        System.out.println("Остановка сервера...");

        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {}

        for (ClientHandler client : clients.values()) {
            client.disconnect();
        }

        threadPool.shutdown();
        gameEngine.stop();
    }

    public static void main(String[] args) {
        GameServer server = new GameServer();
        server.start();
    }
}