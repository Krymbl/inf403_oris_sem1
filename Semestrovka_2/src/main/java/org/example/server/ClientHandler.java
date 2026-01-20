package org.example.server;

import org.json.JSONObject;
import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final int playerId;
    private final GameServer server;

    private BufferedReader in;
    private PrintWriter out;
    private final AtomicBoolean running;

    public ClientHandler(Socket socket, int playerId, GameServer server) {
        this.clientSocket = socket;
        this.playerId = playerId;
        this.server = server;
        this.running = new AtomicBoolean(true);
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            String message;
            while (running.get() && (message = in.readLine()) != null) {
                processMessage(message);
            }

        } catch (SocketException e) {
            System.out.println("Игрок " + playerId + " отключился");
        } catch (IOException e) {
            System.out.println("Ошибка у игрока " + playerId + ": " + e.getMessage());
        } finally {
            disconnect();
        }
    }

    private void processMessage(String jsonMessage) {
        try {
            JSONObject json = new JSONObject(jsonMessage);
            String type = json.getString("type");

            switch (type) {
                case "KEY_STATE":
                    String direction = json.getString("direction");
                    boolean pressed = json.getBoolean("pressed");
                    server.handlePlayerCommand(playerId, direction, pressed);
                    break;

                default:
                    System.out.println("Неизвестный тип от игрока " + playerId + ": " + type);
            }

        } catch (Exception e) {
            System.err.println("Ошибка обработки от игрока " + playerId + ": " + e.getMessage());
        }
    }

    public void sendMessage(String message) {
        if (out != null && running.get()) {
            out.println(message);
        }
    }

    public void disconnect() {
        running.set(false);
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
        } catch (IOException e) {}

        server.handleClientDisconnect(playerId);
    }

    public boolean isConnected() {
        return running.get() &&
                clientSocket != null &&
                !clientSocket.isClosed();
    }
}