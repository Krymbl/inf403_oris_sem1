package org.example.common;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.*;

public class GameState {
    private final Map<Integer, Player> players;
    private final Map<Integer, Pizza> pizzas;

    private long gameTime;
    private boolean gameActive;
    private Integer winnerId;

    public static final int FIELD_WIDTH = 800;
    public static final int FIELD_HEIGHT = 600;
    public static final long TOTAL_GAME_TIME = 120000;

    public GameState() {
        players = new HashMap<>();
        pizzas = new HashMap<>();

        gameTime = TOTAL_GAME_TIME;
        gameActive = false;
        winnerId = null;

        int startY = FIELD_HEIGHT / 2 - Player.HEIGHT / 2;
        players.put(1, new Player(1, 100, startY));
        players.put(2, new Player(2, FIELD_WIDTH - 100 - Player.WIDTH, startY));
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        JSONArray playersArray = new JSONArray();
        for (Player player : players.values()) {
            JSONObject playerJson = new JSONObject();
            playerJson.put("id", player.getId());
            playerJson.put("x", player.getX());
            playerJson.put("y", player.getY());
            playerJson.put("score", player.getScore());
            playerJson.put("pizzaCarried", player.getPizzaCarried());
            playersArray.put(playerJson);
        }
        json.put("players", playersArray);

        JSONArray pizzasArray = new JSONArray();
        for (Pizza pizza : pizzas.values()) {
            JSONObject pizzaJson = new JSONObject();
            pizzaJson.put("id", pizza.getId());
            pizzaJson.put("x", pizza.getX());
            pizzaJson.put("y", pizza.getY());
            pizzaJson.put("active", pizza.isActive());
            pizzasArray.put(pizzaJson);
        }
        json.put("pizzas", pizzasArray);

        json.put("gameTime", gameTime);
        json.put("gameActive", gameActive);
        if (winnerId != null) {
            json.put("winnerId", winnerId);
        }

        return json;
    }

    public static GameState fromJson(JSONObject json) {
        GameState state = new GameState();

        state.players.clear();
        state.pizzas.clear();

        JSONArray playersArray = json.getJSONArray("players");
        for (int i = 0; i < playersArray.length(); i++) {
            JSONObject playerJson = playersArray.getJSONObject(i);
            int id = playerJson.getInt("id");
            int x = playerJson.getInt("x");
            int y = playerJson.getInt("y");
            int score = playerJson.getInt("score");
            int pizzaCarried = playerJson.getInt("pizzaCarried");

            Player player = new Player(id, x, y);
            player.setScore(score);
            player.setPizzaCarried(pizzaCarried);
            state.players.put(id, player);
        }

        JSONArray pizzasArray = json.getJSONArray("pizzas");
        for (int i = 0; i < pizzasArray.length(); i++) {
            JSONObject pizzaJson = pizzasArray.getJSONObject(i);
            int id = pizzaJson.getInt("id");
            int x = pizzaJson.getInt("x");
            int y = pizzaJson.getInt("y");
            boolean active = pizzaJson.getBoolean("active");

            Pizza pizza = new Pizza(id, x, y);
            pizza.setActive(active);
            state.pizzas.put(id, pizza);
        }

        state.gameTime = json.getLong("gameTime");
        state.gameActive = json.getBoolean("gameActive");
        if (json.has("winnerId") && !json.isNull("winnerId")) {
            state.winnerId = json.getInt("winnerId");
        }

        return state;
    }

    public Map<Integer, Player> getPlayers() {
        return Collections.unmodifiableMap(players);
    }

    public Map<Integer, Pizza> getPizzas() {
        return Collections.unmodifiableMap(pizzas);
    }

    public Player getPlayer(int playerId) {
        return players.get(playerId);
    }

    public Pizza getPizza(int pizzaId) {
        return pizzas.get(pizzaId);
    }

    public long getGameTime() {
        return gameTime;
    }

    public boolean isGameActive() {
        return gameActive;
    }

    public Integer getWinnerId() {
        return winnerId;
    }

    public void addPizza(int id, int x, int y) {
        pizzas.put(id, new Pizza(id, x, y));
    }

    public void removePizza(int id) {
        pizzas.remove(id);
    }

    public void setGameTime(long gameTime) {
        this.gameTime = gameTime;
    }

    public void setGameActive(boolean gameActive) {
        this.gameActive = gameActive;
    }

    public void setWinnerId(Integer winnerId) {
        this.winnerId = winnerId;
    }

    public List<Pizza> getActivePizzas() {
        List<Pizza> active = new ArrayList<>();
        for (Pizza pizza : pizzas.values()) {
            if (pizza.isActive()) {
                active.add(pizza);
            }
        }
        return active;
    }


    public boolean isInBaseZone(int x, int y, int playerId) {
        int baseSize = 50;
        int margin = 20;

        if (playerId == 1) {
            return x >= 10 - margin && x <= 10 + baseSize + margin &&
                    y >= 10 - margin && y <= 10 + baseSize + margin;
        } else {
            int baseX = FIELD_WIDTH - baseSize - 10;
            return x >= baseX - margin && x <= baseX + baseSize + margin &&
                    y >= FIELD_HEIGHT - baseSize - 10 - margin &&
                    y <= FIELD_HEIGHT - 10 + margin;
        }
    }
}