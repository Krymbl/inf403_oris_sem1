package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@WebServlet("/game")
public class GamePage extends HttpServlet {
    final static Logger logger = LogManager.getLogger(GamePage.class);
    private final GameService gameService = new GameService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug(request.getServletPath());

        String uid = UUID.randomUUID().toString();
        gameService.createNewGame(uid);
        GameState gameState = gameService.getGameState(uid);


        request.setAttribute("status", gameState.getStatus());
        request.setAttribute("table", gameState.getTable());
        request.setAttribute("uid", uid);

        request.getRequestDispatcher("/game.ftlh")
                .forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uid = request.getParameter("uid");
        String action = request.getParameter("action");

        GameState gameState = gameService.getGameState(uid);

        if ("restart".equals(action)) {
            gameService.restart(uid);


            request.setAttribute("status", gameState.getStatus());
            request.setAttribute("table", gameState.getTable());
            request.setAttribute("uid", uid);

            request.getRequestDispatcher("/game.ftlh")
                    .forward(request, response);
            return;
        }

        String row = request.getParameter("row");
        String column = request.getParameter("column");

        logger.debug("uid " + uid +" , row " + row + ", column " + column);

        gameService.makeMove(uid, Integer.parseInt(row), Integer.parseInt(column));

        request.setAttribute("status", gameState.getStatus());
        request.setAttribute("table", gameState.getTable());
        request.setAttribute("uid", uid);

        request.getRequestDispatcher("/game.ftlh")
                .forward(request, response);
    }
}
