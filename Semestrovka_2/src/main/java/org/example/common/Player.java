package org.example.common;

public class Player {
    private final int id;
    private int x;
    private int y;
    private int score;
    private int pizzaCarried;

    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;
    public static final int MAX_CARRY = 5;

    public Player(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.score = 0;
        this.pizzaCarried = 0;
    }

    public int getId() { return id; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getScore() { return score; }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getPizzaCarried() {
        return pizzaCarried;
    }

    public void setPizzaCarried(int pizzaCarried) {
        this.pizzaCarried = Math.min(pizzaCarried, MAX_CARRY);
    }

    public void takePizza() {
        if (pizzaCarried < MAX_CARRY) {
            pizzaCarried++;
        }
    }

    public void deliverPizza() {
        score += pizzaCarried;
        pizzaCarried = 0;
    }

    public void move(int dx, int dy, int fieldWidth, int fieldHeight) {
        int newX = x + dx;
        int newY = y + dy;

        if (newX >= 0 && newX <= fieldWidth - WIDTH) {
            x = newX;
        }
        if (newY >= 0 && newY <= fieldHeight - HEIGHT) {
            y = newY;
        }
    }

    public boolean collidesWith(Pizza pizza) {
        return x < pizza.getX() + Pizza.WIDTH &&
                x + WIDTH > pizza.getX() &&
                y < pizza.getY() + Pizza.HEIGHT &&
                y + HEIGHT > pizza.getY();
    }

    @Override
    public String toString() {
        return String.format("Player %d [Pos: (%d, %d), Score: %d, Carrying: %d]",
                id, x, y, score, pizzaCarried);
    }
}