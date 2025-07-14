package com.example.combined_tictactoe_ui;

public class GameState
{
    public String[][] board;
    public String currentPlayer;
    public boolean gameOver;
    public String winner;

    // New fields to uniquely identify game and players
    public String gameId;
    public String playerXId;
    public String playerOId;

    // Getters and setters for existing fields
    public String[][] getBoard() { return board; }
    public void setBoard(String[][] board) { this.board = board; }

    public String getCurrentPlayer() { return currentPlayer; }
    public void setCurrentPlayer(String currentPlayer) { this.currentPlayer = currentPlayer; }

    public boolean isGameOver() { return gameOver; }
    public void setGameOver(boolean gameOver) { this.gameOver = gameOver; }

    public String getWinner() { return winner; }
    public void setWinner(String winner) { this.winner = winner; }

    // Getters and setters for new fields
    public String getGameId() { return gameId; }
    public void setGameId(String gameId) { this.gameId = gameId; }

    public String getPlayerXId() { return playerXId; }
    public void setPlayerXId(String playerXId) { this.playerXId = playerXId; }

    public String getPlayerOId() { return playerOId; }
    public void setPlayerOId(String playerOId) { this.playerOId = playerOId; }
}
