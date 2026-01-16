package org.example.common;

public class Pizza {
    private final int id;
    private int x;
    private int y;
    private boolean active;

    public static final int WIDTH = 30;
    public static final int HEIGHT = 30;

    public Pizza(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.active = true;
    }

    public int getId() { return id; }
    public int getX() { return x; }
    public int getY() { return y; }
    public boolean isActive() { return active; }


    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return String.format("Pizza %d [Pos: (%d, %d), Active: %b]",
                id, x, y, active);
    }
}