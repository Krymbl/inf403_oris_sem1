package org.example;

import java.util.List;

public class GameState {
    private Boolean gameOver = false;
    private String status = "Игра началась";
    private Boolean turn = true;
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

    public Boolean getTurn() {
        return turn;
    }

    public void setTurn(Boolean turn) {
        this.turn = turn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
