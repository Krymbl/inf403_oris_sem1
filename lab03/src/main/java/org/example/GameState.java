package org.example;

import java.util.List;

public class GameState {
    private Boolean gameOver = false;
    private List<Row> table =
            List.of(new Row(), new Row(), new Row());

    public Boolean getGameOver() {
        return gameOver;
    }

    public void setGameOver(Boolean gameOver) {
        this.gameOver = gameOver;
    }

    public List<Row> getTable() {
        return table;
    }

    public void setTable(List<Row> table) {
        this.table = table;
    }

}
