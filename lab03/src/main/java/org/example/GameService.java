package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameService {
    private final Map<String, GameState> gamers = new HashMap<>();


    public boolean makeMove(String uid, int row, int column) {
        GameState gameState = gamers.get(uid);
        if (gameState.getGameOver()) {
            gameState.setStatus("Игра окончена, можете перезапустить игру");
            return false;
        }
        List<Row> table = gameState.getTable();
        Row rowTable = table.get(row - 1);
        String value = getCellValue(rowTable, column);
        if (!value.equals("empty.png")) {
            gameState.setStatus("Ячейка [" + row + ", " + column + "] занята, сделайте ход снова");
            return false;
        }
        String symbol = gameState.getTurn() ? "tac.svg" : "tic.svg";
        setCellValue(rowTable, column, symbol);

        if (checkWin(gameState, symbol)) {
            gameState.setGameOver(true);
            gameState.setStatus("Игра окончена! \nПобедитель: " + uid);
            return false;
        } else if (isBoardFull(gameState)) {
            gameState.setGameOver(true);
            gameState.setStatus("Поле заполнено. Игра окончена! \nНичья!" );
            return false;
        }
        gameState.setStatus("Ход игрока: " + uid + " сделан");
        gameState.setTurn(!gameState.getTurn());
        return true;

    }

    public boolean restart(String uid) {
        GameState gameState = gamers.get(uid);
        if (gameState != null) {
            gameState.setGameOver(false);
            gameState.setStatus("Игра перезапущена");
            gameState.setTurn(true);
            gameState.setTable(List.of(new Row(), new Row(), new Row()));
            return true;
        }
        return false;
    }



    public boolean isBoardFull(GameState gameState) {
        List<Row> table = gameState.getTable();
        return table.stream()
                .allMatch(row ->
                        !row.getF().equals("empty.png") &&
                        !row.getS().equals("empty.png") &&
                        !row.getT().equals("empty.png")
                );
    }

    public boolean checkWin(GameState gameState, String symbol) {

        List<Row> table = gameState.getTable();

        for (Row row : table) {
            if (row.getF().equals(symbol) &&  row.getS().equals(symbol) && row.getT().equals(symbol)) {
                return true;
            }
        }

        for (int column = 1; column <= 3; column++) {
            if (symbol.equals(getCellValue(table.get(0), column)) &&
                    symbol.equals(getCellValue(table.get(1), column)) &&
                    symbol.equals(getCellValue(table.get(2), column))) {
                return true;
            }
        }

        if (symbol.equals(getCellValue(table.get(0), 1)) &&
        symbol.equals(getCellValue(table.get(1), 2)) &&
        symbol.equals(getCellValue(table.get(2), 3))) {
            return true;
        }

        if (symbol.equals(getCellValue(table.get(0), 3)) &&
                symbol.equals(getCellValue(table.get(1), 2)) &&
                symbol.equals(getCellValue(table.get(2), 1))) {
            return true;
        }

        return false;
    }

    public String getCellValue(Row row, int column) {
        return switch (column) {
            case 1 -> row.getF();
            case 2 -> row.getS();
            case 3 -> row.getT();
            default -> throw new RuntimeException();
        };
    }

    public void setCellValue(Row row, int column, String symbol) {
        switch (column) {
            case 1 -> row.setF(symbol);
            case 2 -> row.setS(symbol);
            case 3 -> row.setT(symbol);
            default -> throw new RuntimeException();
        };
    }

    public void createNewGame(String uid) {
        gamers.put(uid, new GameState());
    }



    public GameState getGameState(String uid) {
        return gamers.get(uid);
    }


    public Map<String, GameState> getGamers() {
        return gamers;
    }
}

