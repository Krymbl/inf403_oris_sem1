package org.example.server;

import org.example.common.*;
import java.util.*;
import java.util.concurrent.*;

public class GameEngine {
    private final GameState gameState;
    private final Random random;
    private final ScheduledExecutorService scheduler;
    private volatile boolean gameFinished = false;

    private static final int MAX_PIZZAS = 6;
    private static final int PIZZA_SPAWN_INTERVAL = 2000;
    private static final int GAME_TICK_INTERVAL = 20;
    private static final int MOVEMENT_SPEED = 8;

    private int nextPizzaId = 1;

    private volatile boolean gameStarted = false;

    private final Map<Integer, Set<String>> activeDirections;

    public GameEngine() {
        this.gameState = new GameState();
        this.random = new Random();
        this.scheduler = Executors.newScheduledThreadPool(2);
        this.activeDirections = new HashMap<>();

        activeDirections.put(1, new HashSet<>());
        activeDirections.put(2, new HashSet<>());

        System.out.println("GameEngine создан");
    }

    public void start() {
        System.out.println("Запуск игрового движка...");

        scheduler.scheduleAtFixedRate(this::gameTick, 0, GAME_TICK_INTERVAL, TimeUnit.MILLISECONDS);
        scheduler.scheduleAtFixedRate(this::spawnPizzas, 0, PIZZA_SPAWN_INTERVAL, TimeUnit.MILLISECONDS);

        gameState.setGameActive(false);
    }

    public void stop() {
        System.out.println("Остановка игрового движка...");
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(3, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
        }
    }


    public void startGame() {
        if (gameState.getPlayers().size() != 2) {
            System.out.println("Два игрока не подключилось");
            return;
        }
        gameStarted = true;
        gameState.setGameActive(true);

        System.out.println("Игрок 1 позиция: (" + gameState.getPlayer(1).getX() + ", " + gameState.getPlayer(1).getY() + ")");
        System.out.println("Игрок 2 позиция: (" + gameState.getPlayer(2).getX() + ", " + gameState.getPlayer(2).getY() + ")");
    }

    private void gameTick() {
        if (!gameStarted) return;

        updateGameTime();
        processPlayerCommands();
        checkPizzaCollection();
        checkBaseDelivery();
        checkGameEnd();
    }

    private void updateGameTime() {
        long currentTime = gameState.getGameTime();
        if (currentTime > 0) {
            gameState.setGameTime(currentTime - GAME_TICK_INTERVAL);
        }
    }

    private void processPlayerCommands() {
        for (int playerId = 1; playerId <= 2; playerId++) {
            Player player = gameState.getPlayer(playerId);
            if (player == null) continue;

            Set<String> directions = activeDirections.get(playerId);

            int dx = 0, dy = 0;

            for (String dir : directions) {
                switch (dir) {
                    case "UP" -> dy -= MOVEMENT_SPEED;
                    case "DOWN" -> dy += MOVEMENT_SPEED;
                    case "LEFT" -> dx -= MOVEMENT_SPEED;
                    case "RIGHT" -> dx += MOVEMENT_SPEED;
                }
            }

            if (dx != 0 && dy != 0) {
                dx = (int)(dx * 0.7071); // cos(45°) - 0.7071
                dy = (int)(dy * 0.7071); // sin(45°) - 0.7071
            }

            if (dx != 0 || dy != 0) {
                player.move(dx, dy, GameState.FIELD_WIDTH, GameState.FIELD_HEIGHT);

            }
        }
    }

    private void checkPizzaCollection() {
        List<Pizza> pizzasToRemove = new ArrayList<>();

        for (Player player : gameState.getPlayers().values()) {
            for (Pizza pizza : gameState.getActivePizzas()) {
                if (player.collidesWith(pizza) && pizza.isActive()) {
                    if (player.getPizzaCarried() < Player.MAX_CARRY) {
                        player.takePizza();
                        pizzasToRemove.add(pizza);
                        System.out.println("Игрок " + player.getId() + " взял пиццу " + pizza.getId());
                    }
                }
            }
        }

        for (Pizza pizza : pizzasToRemove) {
            gameState.removePizza(pizza.getId());
        }
    }


    private void checkBaseDelivery() {
        for (Player player : gameState.getPlayers().values()) {
            int playerId = player.getId();


            boolean isTouchingBase = isPlayerTouchingBase(player, playerId);

            if (isTouchingBase && player.getPizzaCarried() > 0) {
                int delivered = player.getPizzaCarried();
                player.deliverPizza();
                System.out.println("Игрок " + playerId + " доставил " + delivered +
                        " пицц на базу. Счет: " + player.getScore());
            }
        }
    }


    private boolean isPlayerTouchingBase(Player player, int playerId) {
        int baseSize = 50;
        int playerCenterX = player.getX() + Player.WIDTH / 2;
        int playerCenterY = player.getY() + Player.HEIGHT / 2;

        if (playerId == 1) {
            int baseLeft = 10;
            int baseRight = 10 + baseSize;
            int baseTop = 10;
            int baseBottom = 10 + baseSize;

            return playerCenterX >= baseLeft && playerCenterX <= baseRight &&
                    playerCenterY >= baseTop && playerCenterY <= baseBottom;
        } else {
            int baseLeft = GameState.FIELD_WIDTH - baseSize - 10;
            int baseRight = GameState.FIELD_WIDTH - 10;
            int baseTop = GameState.FIELD_HEIGHT - baseSize - 10;
            int baseBottom = GameState.FIELD_HEIGHT - 10;

            return playerCenterX >= baseLeft && playerCenterX <= baseRight &&
                    playerCenterY >= baseTop && playerCenterY <= baseBottom;
        }
    }

    private void checkGameEnd() {
        if (gameState.getGameTime() <= 0 && gameState.isGameActive() && !gameFinished) {
            System.out.println("GameEngine: Время вышло, завершаем игру");

            gameFinished = true;
            gameState.setGameActive(false);
            gameStarted = false;

            activeDirections.get(1).clear();
            activeDirections.get(2).clear();

            determineWinner();

            Player player1 = gameState.getPlayer(1);
            Player player2 = gameState.getPlayer(2);

            if (player1 != null && player2 != null) {
                StatisticsManager.saveGameResult(
                        player1.getScore(),
                        player2.getScore(),
                        gameState.getWinnerId(),
                        GameState.TOTAL_GAME_TIME
                );
            }

            System.out.println("GameEngine: Игра окончена! Победитель: " +
                    (gameState.getWinnerId() != null ? "Игрок " + gameState.getWinnerId() : "Ничья"));
        }
    }



    private void determineWinner() {
        Player player1 = gameState.getPlayer(1);
        Player player2 = gameState.getPlayer(2);

        if (player1 == null || player2 == null) {
            gameState.setWinnerId(null);
            return;
        }

        if (player1.getScore() > player2.getScore()) {
            gameState.setWinnerId(1);
        } else if (player2.getScore() > player1.getScore()) {
            gameState.setWinnerId(2);
        } else {
            gameState.setWinnerId(null);
        }
    }

    private void spawnPizzas() {
        if (!gameStarted) return;

        if (gameState.getActivePizzas().size() >= MAX_PIZZAS) return;

        int pizzasToCreate = MAX_PIZZAS - gameState.getActivePizzas().size();

        for (int i = 0; i < pizzasToCreate; i++) {
            int x, y;
            int attempts = 0;

            do {
                x = random.nextInt(GameState.FIELD_WIDTH - Pizza.WIDTH);
                y = random.nextInt(GameState.FIELD_HEIGHT - Pizza.HEIGHT);
                attempts++;

                if (attempts > 100) {
                    System.out.println("Не удалось найти позицию для пиццы");
                    return;
                }

            } while (isInvalidPizzaPosition(x, y));

            gameState.addPizza(nextPizzaId++, x, y);
        }
    }

    private boolean isInvalidPizzaPosition(int x, int y) {
        if (gameState.isInBaseZone(x, y, 1) || gameState.isInBaseZone(x, y, 2)) {
            return true;
        }

        for (Pizza existingPizza : gameState.getActivePizzas()) {
            int distanceX = Math.abs(existingPizza.getX() - x);
            int distanceY = Math.abs(existingPizza.getY() - y);

            if (distanceX < Pizza.WIDTH * 2 && distanceY < Pizza.HEIGHT * 2) {
                return true;
            }
        }

        for (Player player : gameState.getPlayers().values()) {
            int distanceX = Math.abs(player.getX() - x);
            int distanceY = Math.abs(player.getY() - y);

            if (distanceX < Player.WIDTH * 2 && distanceY < Player.HEIGHT * 2) {
                return true;
            }
        }

        return false;
    }


    public void addPlayerCommand(int playerId, String direction, boolean isKeyPressed) {
        if (!gameStarted) {
            System.out.println("Команда игнорирована: игра не активна");
            return;
        }

        Set<String> directions = activeDirections.get(playerId);
        if (directions != null) {
            if (isKeyPressed) {
                directions.add(direction);
                System.out.println("Игрок " + playerId + " нажал " + direction + ". Активные: " + directions);
            } else {
                directions.remove(direction);
                System.out.println("Игрок " + playerId + " отпустил " + direction + ". Активные: " + directions);
            }
        }
    }

    public GameState getGameState() {
        return gameState;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void resetGame() {
        System.out.println("Сброс игры...");

        gameFinished = false;

        for (Integer pizzaId : new ArrayList<>(gameState.getPizzas().keySet())) {
            gameState.removePizza(pizzaId);
        }

        for (Player player : gameState.getPlayers().values()) {
            player.setScore(0);
            player.setPizzaCarried(0);

            int startY = GameState.FIELD_HEIGHT / 2 - Player.HEIGHT / 2;
            if (player.getId() == 1) {
                player.setPosition(100, startY);
            } else {
                player.setPosition(GameState.FIELD_WIDTH - 100 - Player.WIDTH, startY);
            }
        }

        gameState.setGameTime(GameState.TOTAL_GAME_TIME);
        gameState.setGameActive(false);
        gameState.setWinnerId(null);

        gameStarted = false;
        nextPizzaId = 1;

        activeDirections.get(1).clear();
        activeDirections.get(2).clear();

        System.out.println("Игра сброшена");
    }


    public boolean isGameFinished() {
        return gameFinished;
    }
}