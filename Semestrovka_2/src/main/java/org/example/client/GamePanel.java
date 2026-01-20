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
    private Map<Integer, Long> pizzaSpawnTimes = new HashMap<>();

    private JLabel statusLabel;
    private JPanel controlPanel;
    private GameCanvas gameCanvas;

    private Timer animationTimer;
    private float pizzaAlpha = 0.7f;
    private boolean pizzaAlphaIncreasing = false;

    public GamePanel(GameClient client) {
        this.client = client;
        this.gameState = new GameState();

        setLayout(new BorderLayout());
        initializeComponents();
        setupAnimationTimer();
    }

    private void initializeComponents() {
        controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); //компоненты слева направо
        controlPanel.setBackground(new Color(230, 230, 230)); //Серый
        controlPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        statusLabel = new JLabel("Подключение к серверу...");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));

        controlPanel.add(statusLabel);

        add(controlPanel, BorderLayout.NORTH);

        gameCanvas = new GameCanvas();
        gameCanvas.setPreferredSize(new Dimension(800, 600));
        add(gameCanvas, BorderLayout.CENTER);
    }

    private void setupAnimationTimer() {
        animationTimer = new Timer(40, e -> {
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
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }
        this.gameOver = gameOver;
    }

    class GameCanvas extends JPanel implements KeyListener {
        private Map<Integer, Player> displayedPlayers;

        public GameCanvas() {
            setBackground(new Color(245, 245, 245)); // Светло-серый фон
            setFocusable(true);   // Делаем компонент фокусируемым
            addKeyListener(this);
            displayedPlayers = new HashMap<>();

            //периодический запроса фокуса
            Timer focusTimer = new Timer(1000, e -> requestFocusInWindow());
            focusTimer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            //сглаживание
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.setColor(Color.DARK_GRAY);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRect(0, 0, GameState.FIELD_WIDTH , GameState.FIELD_HEIGHT );

            drawBases(g2d);

            drawPizzas(g2d);

            drawPlayers(g2d);

            drawHUD(g2d);

            if (gameOver && gameState != null) {
                drawGameOverScreen(g2d);
            }
        }

        private void drawBases(Graphics2D g) {
            int baseSize = 50;

            g.setColor(new Color(70, 130, 255)); // Яркий синий
            g.fillRect(10, 10, baseSize, baseSize);
            g.setColor(Color.BLUE.darker());  // Темно-синий контур
            g.setStroke(new BasicStroke(2));
            g.drawRect(10, 10, baseSize, baseSize);

            if (gameState != null && gameState.getPlayer(1) != null) {
                g.setFont(new Font("Arial", Font.BOLD, 16));
                g.setColor(Color.WHITE);
                String score = String.valueOf(gameState.getPlayer(1).getScore());
                FontMetrics fm = g.getFontMetrics();
                int textX = 10 + (baseSize - fm.stringWidth(score)) / 2;
                int textY = 10 + baseSize / 2 + fm.getAscent() / 2 - 2;
                g.drawString(score, textX, textY);
            }

            g.setColor(new Color(255, 70, 70)); // Яркий красный
            int x2 = GameState.FIELD_WIDTH - baseSize - 10;
            int y2 = GameState.FIELD_HEIGHT - baseSize - 10;
            g.fillRect(x2, y2, baseSize, baseSize);
            g.setColor(Color.RED.darker());
            g.drawRect(x2, y2, baseSize, baseSize);

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

        private void drawPizzas(Graphics2D g) {
            if (gameState == null) return;

            Composite originalComposite = g.getComposite();

            for (Pizza pizza : gameState.getPizzas().values()) {
                if (!pizza.isActive()) continue;

                int x = pizza.getX();
                int y = pizza.getY();
                int size = Pizza.WIDTH;

                float blinkAlpha = pizzaAlpha;

                float spawnAlpha = 1.0f;
                Long spawnTime = pizzaSpawnTimes.get(pizza.getId());
                if (spawnTime != null) {
                    long age = System.currentTimeMillis() - spawnTime;
                    if (age < 1000) {
                        spawnAlpha = age / 1000.0f;
                    } else {
                        pizzaSpawnTimes.remove(pizza.getId());
                    }
                }

                float finalAlpha = blinkAlpha * spawnAlpha;

                g.setComposite(AlphaComposite.getInstance(
                        AlphaComposite.SRC_OVER, finalAlpha));

                int[] xPoints = {x, x + size, x + size / 2};
                int[] yPoints = {y + size, y + size, y};

                g.setColor(new Color(255, 204, 0)); //желтый
                g.fillPolygon(xPoints, yPoints, 3);

                g.setColor(new Color(220, 150, 30)); //темно-желтый
                g.drawPolygon(xPoints, yPoints, 3);
            }

            g.setComposite(originalComposite);
        }

        private void drawPlayers(Graphics2D g) {
            if (gameState == null) return;

            for (Player player : gameState.getPlayers().values()) {
                Player displayed = displayedPlayers.get(player.getId());
                if (displayed == null) {
                    displayed = new Player(player.getId(), player.getX(), player.getY());
                    displayedPlayers.put(player.getId(), displayed);
                } else {
                    float smoothing = 0.5f; // Коэффициент плавности
                    int x = (int)(displayed.getX() + (player.getX() - displayed.getX()) * smoothing);
                    int y = (int)(displayed.getY() + (player.getY() - displayed.getY()) * smoothing);
                    displayed.setPosition(x,y);
                }

                Color playerColor;
                if (player.getId() == 1) {
                    playerColor = new Color(70, 130, 255); // Синий
                } else {
                    playerColor = new Color(255, 70, 70); // Красный
                }

                g.setColor(playerColor);
                g.fillRect(displayed.getX(), displayed.getY(),
                        Player.WIDTH, Player.HEIGHT);

                g.setColor(Color.BLACK);
                g.setStroke(new BasicStroke(2));
                g.drawRect(displayed.getX(), displayed.getY(),
                        Player.WIDTH, Player.HEIGHT);

                g.setFont(new Font("Arial", Font.BOLD, 12));
                g.setColor(Color.WHITE);
                String idText = "P" + player.getId();
                g.drawString(idText, displayed.getX() + 5, displayed.getY() + 15);

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

            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 20));

            long seconds = gameState.getGameTime() / 1000;
            String time = String.format("Время: %02d:%02d", seconds / 60, seconds % 60);

            FontMetrics fm = g.getFontMetrics();
            int timeX = (GameState.FIELD_WIDTH - fm.stringWidth(time)) / 2;
            g.drawString(time, timeX, 30);

            g.setFont(new Font("Arial", Font.PLAIN, 14));

            Player player1 = gameState.getPlayer(1);
            Player player2 = gameState.getPlayer(2);

            if (player1 != null) {
                String p1Info = String.format("Игрок 1: %d очков | Несет: %d/%d",
                        player1.getScore(), player1.getPizzaCarried(), Player.MAX_CARRY);
                g.setColor(new Color(70, 130, 255)); // Синий цвет
                g.drawString(p1Info, 100, 60);
            }

            if (player2 != null) {
                String p2Info = String.format("Игрок 2: %d очков | Несет: %d/%d",
                        player2.getScore(), player2.getPizzaCarried(), Player.MAX_CARRY);
                g.setColor(new Color(255, 70, 70)); // Красный цвет
                int textWidth = fm.stringWidth(p2Info);
                g.drawString(p2Info, GameState.FIELD_WIDTH - textWidth - 10, 60);
            }

            g.setColor(client.isConnected() ? Color.GREEN.darker() : Color.RED);
            String status = client.isConnected() ?
                    (client.isGameStarted() ? "Игра идет" : "Ожидание") :
                    "Не подключено";
            g.drawString("Статус: " + status, 10, GameState.FIELD_HEIGHT + 20);
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

            if (gameState.getPlayer(1) != null && gameState.getPlayer(2) != null) {
                g.setFont(new Font("Arial", Font.PLAIN, 24));
                String score = String.format("Счёт: %d - %d",
                        gameState.getPlayer(1).getScore(),
                        gameState.getPlayer(2).getScore());

                fm = g.getFontMetrics();
                x = (getWidth() - fm.stringWidth(score)) / 2;
                g.drawString(score, x, y + 50);
            }

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