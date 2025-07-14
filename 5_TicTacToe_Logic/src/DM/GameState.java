package DM;

import java.util.Random;

public class GameState {
    public String[][] board;
    private String currentPlayer;
    private boolean gameOver;
    private String winner;

    private String gameId;     // Unique ID for the game
    private String playerXId;  // Player X identifier
    private String playerOId;  // Player O identifier


    public GameState(int size, String gameId, String playerXId) {
        board = new String[size][size];

        Random random = new Random();
        this.currentPlayer = random.nextBoolean() ? "X" : "O";

        //currentPlayer = "X";
        gameOver = false;
        winner = null;
        this.gameId = gameId;
        this.playerXId = playerXId;
        this.playerOId = null;
    }

    // Getters and setters
    public String[][] getBoard() { return board; }

    public String getCurrentPlayer() { return currentPlayer; }
    public void setCurrentPlayer(String player) { currentPlayer = player; }

    public boolean isGameOver() { return gameOver; }
    public void setGameOver(boolean gameOver) { this.gameOver = gameOver; }

    public String getWinner() { return winner; }
    public void setWinner(String winner) { this.winner = winner; }

    public String getGameId() { return gameId; }
    public String getPlayerXId() { return playerXId; }
    public String getPlayerOId() { return playerOId; }
    public void setPlayerOId(String playerOId) { this.playerOId = playerOId; }
}
