package org.example.client;

import org.example.common.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class GamePanel extends JPanel {
    private GameClient client;
    private GameState gameState;
    private boolean gameOver = false;
    // Для отслеживания времени появления каждый пиццы (для анимации)
    private Map<Integer, Long> pizzaSpawnTimes = new HashMap<>();

    // Компоненты Swing
    private JLabel statusLabel; // Метка для статуса (ожидание, игшра идет и тд_)
    private JPanel controlPanel; // Верхняя панель управления
    private GameCanvas gameCanvas; // Основное игровое поле (кастомный JPanel)

    // Анимация
    private Timer animationTimer;
    private float pizzaAlpha = 0.7f;  // Прозрачность пицц (от 0.0 до 1.0)
    private boolean pizzaAlphaIncreasing = false; //TODO ЗАЧЕМ? Направление изменения прозрачности

    public GamePanel(GameClient client) {
        this.client = client;
        this.gameState = new GameState();

        setLayout(new BorderLayout()); //BorderLayout - менеджер "север-юг-запад-восток-центр"
        initializeComponents();
        setupAnimationTimer();
    }

    private void initializeComponents() {
        // Панель управления сверху
        controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Компоненты слева
        controlPanel.setBackground(new Color(230, 230, 230)); //Серый
        controlPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        statusLabel = new JLabel("Подключение к серверу...");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));

        controlPanel.add(statusLabel);

        add(controlPanel, BorderLayout.NORTH);

        // Игровое поле по центру
        gameCanvas = new GameCanvas();   // Создаем кастомный холст
        //. PreferredSize - "желаемый размер"
        //Domension - хранит ширину, высоту
        gameCanvas.setPreferredSize(new Dimension(800, 600)); // Фиксированный размер
        add(gameCanvas, BorderLayout.CENTER);
    }

    private void setupAnimationTimer() {
        animationTimer = new Timer(50, e -> {
            if (pizzaAlphaIncreasing) {
                pizzaAlpha += 0.03f;
                if (pizzaAlpha >= 0.9f) pizzaAlphaIncreasing = false;
            } else {
                pizzaAlpha -= 0.03f;
                if (pizzaAlpha <= 0.6f) pizzaAlphaIncreasing = true;
            }

            gameCanvas.repaint();
        });
        animationTimer.start();
    }

    public void setGameState(GameState gameState) {
        // Запоминаем время появления для новых пицц
        if (this.gameState != null) {
            for (Pizza newPizza : gameState.getPizzas().values()) {
                if (!this.gameState.getPizzas().containsKey(newPizza.getId())) {
                    pizzaSpawnTimes.put(newPizza.getId(), System.currentTimeMillis());
                }
            }
        }
        this.gameState = gameState;
    }

    public void setStatus(String status) {
        statusLabel.setText(status);
    }

    public void setGameOver(boolean gameOver) {
        // Останавливаем анимацию пиццы при завершении игры
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }
        this.gameOver = gameOver;
    }

    class GameCanvas extends JPanel implements KeyListener {
        private Map<Integer, Player> displayedPlayers;

        public GameCanvas() {
            setBackground(new Color(245, 245, 245));
            setFocusable(true);
            addKeyListener(this);
            displayedPlayers = new HashMap<>();

            Timer focusTimer = new Timer(1000, e -> requestFocusInWindow());
            focusTimer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            // Границы поля
            g2d.setColor(Color.DARK_GRAY);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRect(0, 0, GameState.FIELD_WIDTH - 1, GameState.FIELD_HEIGHT - 1);

            // Базы
            drawBases(g2d);

            // Пиццы с анимацией
            drawPizzas(g2d);

            // Игроки с интерполяцией
            drawPlayers(g2d);

            // HUD
            drawHUD(g2d);

            // Экран окончания игры
            if (gameOver && gameState != null) {
                drawGameOverScreen(g2d);
            }
        }

        private void drawBases(Graphics2D g) {
            int baseSize = 50;

            // База игрока 1 (левый верхний угол) - СИНИЙ
            g.setColor(new Color(70, 130, 255)); // Яркий синий
            g.fillRect(10, 10, baseSize, baseSize);
            g.setColor(Color.BLUE.darker());
            g.setStroke(new BasicStroke(2));
            g.drawRect(10, 10, baseSize, baseSize);

            // Счёт на базе
            if (gameState != null && gameState.getPlayer(1) != null) {
                g.setFont(new Font("Arial", Font.BOLD, 16));
                g.setColor(Color.WHITE);
                String score = String.valueOf(gameState.getPlayer(1).getScore());
                FontMetrics fm = g.getFontMetrics();
                int textX = 10 + (baseSize - fm.stringWidth(score)) / 2;
                int textY = 10 + baseSize / 2 + fm.getAscent() / 2 - 2;
                g.drawString(score, textX, textY);
            }

            // База игрока 2 (правый нижний угол) - КРАСНЫЙ
            g.setColor(new Color(255, 70, 70)); // Яркий красный
            int x2 = GameState.FIELD_WIDTH - baseSize - 10;
            int y2 = GameState.FIELD_HEIGHT - baseSize - 10;
            g.fillRect(x2, y2, baseSize, baseSize);
            g.setColor(Color.RED.darker());
            g.drawRect(x2, y2, baseSize, baseSize);

            // Счёт на базе
            if (gameState != null && gameState.getPlayer(2) != null) {
                g.setFont(new Font("Arial", Font.BOLD, 16));
                g.setColor(Color.WHITE);
                String score = String.valueOf(gameState.getPlayer(2).getScore());
                FontMetrics fm = g.getFontMetrics();
                int textX = x2 + (baseSize - fm.stringWidth(score)) / 2;
                int textY = y2 + baseSize / 2 + fm.getAscent() / 2 - 2;
                g.drawString(score, textX, textY);
            }
        }

        // В drawPizzas():
        private void drawPizzas(Graphics2D g) {
            if (gameState == null) return;

            Composite originalComposite = g.getComposite();

            for (Pizza pizza : gameState.getPizzas().values()) {
                if (!pizza.isActive()) continue;

                int x = pizza.getX();
                int y = pizza.getY();
                int size = Pizza.WIDTH;

                // Базовое мигание
                float blinkAlpha = pizzaAlpha;

                // Анимация появления (если пицца новая)
                float spawnAlpha = 1.0f;
                Long spawnTime = pizzaSpawnTimes.get(pizza.getId());
                if (spawnTime != null) {
                    long age = System.currentTimeMillis() - spawnTime;
                    if (age < 1000) {
                        // Первая секунда: плавное появление
                        spawnAlpha = age / 1000.0f;
                    } else {
                        // Удаляем из map после завершения анимации
                        pizzaSpawnTimes.remove(pizza.getId());
                    }
                }

                // Объединяем
                float finalAlpha = blinkAlpha * spawnAlpha;
//                finalAlpha = Math.max(0.1f, Math.min(0.9f, finalAlpha));

                g.setComposite(AlphaComposite.getInstance(
                        AlphaComposite.SRC_OVER, finalAlpha));

                // Отрисовка
                int[] xPoints = {x, x + size, x + size / 2};
                int[] yPoints = {y + size, y + size, y};

                g.setColor(new Color(255, 204, 0));
                g.fillPolygon(xPoints, yPoints, 3);

                g.setColor(new Color(220, 150, 30));
                g.drawPolygon(xPoints, yPoints, 3);
            }

            g.setComposite(originalComposite);
        }

        private void drawPlayers(Graphics2D g) {
            if (gameState == null) return;

            for (Player player : gameState.getPlayers().values()) {
                // Интерполяция для плавности
                Player displayed = displayedPlayers.get(player.getId());
                if (displayed == null) {
                    displayed = new Player(player.getId(), player.getX(), player.getY());
                    displayedPlayers.put(player.getId(), displayed);
                } else {
                    float smoothing = 0.4f; // Коэффициент плавности
                    int x = (int)(displayed.getX() + (player.getX() - displayed.getX()) * smoothing);
                    int y = (int)(displayed.getY() + (player.getY() - displayed.getY()) * smoothing);
                    displayed.setPosition(x,y);
                }

                // Цвет игрока по его ID (не зависит от того, чей это клиент)
                Color playerColor;
                if (player.getId() == 1) {
                    playerColor = new Color(70, 130, 255); // Синий для игрока 1
                } else {
                    playerColor = new Color(255, 70, 70); // Красный для игрока 2
                }

                // Тело игрока
                g.setColor(playerColor);
                g.fillRect(displayed.getX(), displayed.getY(),
                        Player.WIDTH, Player.HEIGHT);

                // Обводка
                g.setColor(Color.BLACK);
                g.setStroke(new BasicStroke(2));
                g.drawRect(displayed.getX(), displayed.getY(),
                        Player.WIDTH, Player.HEIGHT);

                // ID игрока
                g.setFont(new Font("Arial", Font.BOLD, 12));
                g.setColor(Color.WHITE);
                String idText = "P" + player.getId();
                g.drawString(idText, displayed.getX() + 5, displayed.getY() + 15);

                // Инвентарь (сколько пицц несёт)
                if (player.getPizzaCarried() > 0) {
                    g.setColor(Color.YELLOW);
                    g.setFont(new Font("Arial", Font.BOLD, 14));
                    String carryText = "" + player.getPizzaCarried();
                    FontMetrics fm = g.getFontMetrics();
                    int textX = displayed.getX() + (Player.WIDTH - fm.stringWidth(carryText)) / 2;
                    int textY = displayed.getY() + Player.HEIGHT - 5;
                    g.drawString(carryText, textX, textY);
                }
            }
        }

        private void drawHUD(Graphics2D g) {
            if (gameState == null) return;

            // Время сверху по центру
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 20));

            long seconds = gameState.getGameTime() / 1000;
            String time = String.format("Время: %02d:%02d", seconds / 60, seconds % 60);

            FontMetrics fm = g.getFontMetrics();
            int timeX = (GameState.FIELD_WIDTH - fm.stringWidth(time)) / 2;
            g.drawString(time, timeX, 30);

            // Информация об игроках
            g.setFont(new Font("Arial", Font.PLAIN, 14));

            Player player1 = gameState.getPlayer(1);
            Player player2 = gameState.getPlayer(2);

            if (player1 != null) {
                String p1Info = String.format("Игрок 1: %d очков | Несет: %d/%d",
                        player1.getScore(), player1.getPizzaCarried(), Player.MAX_CARRY);
                g.setColor(new Color(70, 130, 255));
                g.drawString(p1Info, 10, 60);
            }

            if (player2 != null) {
                String p2Info = String.format("Игрок 2: %d очков | Несет: %d/%d",
                        player2.getScore(), player2.getPizzaCarried(), Player.MAX_CARRY);
                g.setColor(new Color(255, 70, 70));
                int textWidth = fm.stringWidth(p2Info);
                g.drawString(p2Info, GameState.FIELD_WIDTH - textWidth - 10, 60);
            }

            // Статус подключения
            g.setColor(client.isConnected() ? Color.GREEN.darker() : Color.RED);
            String status = client.isConnected() ?
                    (client.isGameStarted() ? "Игра идет" : "Ожидание") :
                    "Не подключено";
            g.drawString("Статус: " + status, 10, GameState.FIELD_HEIGHT + 25);
        }

        private void drawGameOverScreen(Graphics2D g) {
            System.out.println("GamePanel: Отрисовка экрана окончания игры. gameOver=" + gameOver);

            if (gameState == null) {
                System.out.println("GamePanel: gameState is null!");
                return;
            }

            g.setColor(new Color(0, 0, 0, 180));
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 36));

            String result;
            Integer winnerId = gameState.getWinnerId();

            System.out.println("GamePanel: winnerId=" + winnerId);

            if (winnerId == null) {
                result = "НИЧЬЯ!";
            } else {
                result = "ПОБЕДИЛ ИГРОК " + winnerId + "!";
            }

            FontMetrics fm = g.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(result)) / 2;
            int y = getHeight() / 2;

            g.drawString(result, x, y);

            // Счёт (только если есть игроки)
            if (gameState.getPlayer(1) != null && gameState.getPlayer(2) != null) {
                g.setFont(new Font("Arial", Font.PLAIN, 24));
                String score = String.format("Счёт: %d - %d",
                        gameState.getPlayer(1).getScore(),
                        gameState.getPlayer(2).getScore());

                fm = g.getFontMetrics();
                x = (getWidth() - fm.stringWidth(score)) / 2;
                g.drawString(score, x, y + 50);
            }

            // Инструкция
            g.setFont(new Font("Arial", Font.PLAIN, 16));
            String instruction = "Закройте и перезапустите программу для новой игры";
            fm = g.getFontMetrics();
            x = (getWidth() - fm.stringWidth(instruction)) / 2;
            g.drawString(instruction, x, y + 100);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            client.handleKeyPressed(e.getKeyCode());
        }

        @Override
        public void keyReleased(KeyEvent e) {
            client.handleKeyReleased(e.getKeyCode());
        }

        @Override
        public void keyTyped(KeyEvent e) {}
    }
}