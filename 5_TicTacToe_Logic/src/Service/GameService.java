package Service;

import DM.GameState;
import DM.Move;
import FindingConnectedComponentsinaGraphAlgorithm_Java.DFSConnectedFive;
import FindingConnectedComponentsinaGraphAlgorithm_Java.IAlgo_Shortest_Path_in_a_Graph;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class GameService
{
    private final int SIZE = 10;
    private final Map<String, GameState> games = new ConcurrentHashMap<>();

//////////////////////////////////////////////////////////////////////////////////////////////////////
    private final IAlgo_Shortest_Path_in_a_Graph winChecker = new DFSConnectedFive();
    //private final IAlgo_Shortest_Path_in_a_Graph winChecker = new BFSConnectedFive();

    /*
    Algorithm jar options to use...
     */
//////////////////////////////////////////////////////////////////////////////////////////////////////
    // Start a new game: create a new gameId and playerXId
    public GameState startNewGame()
    {
        String gameId = UUID.randomUUID().toString();
        String playerXId = UUID.randomUUID().toString();
        GameState gameState = new GameState(SIZE, gameId, playerXId);
        games.put(gameId, gameState);
        return gameState;
    }

    // Player joins an existing game waiting for player O
    public GameState joinGame()
    {
        // Find first game without playerOId assigned and not over
        for (GameState game : games.values()) {
            if (game.getPlayerOId() == null && !game.isGameOver()) {
                String playerOId = UUID.randomUUID().toString();
                game.setPlayerOId(playerOId);
                return game;
            }
        }
        // No available games
        return null;
    }

    // Make a move in a specific game by playerId
    public GameState makeMove(String gameId, String playerId, Move move)
    {
        GameState gameState = games.get(gameId);
        if (gameState == null) return null;

        String[][] board = gameState.getBoard();

        // Check if move is valid
        if (board[move.row][move.col] != null || gameState.isGameOver())
        {
            return gameState;
        }

        // Check if playerId matches current player
        if (!isPlayerTurn(gameState, playerId))
        {
            return gameState;
        }

        // Place move
        board[move.row][move.col] = move.player;

        // Check win
        if (checkWin(board, move.row, move.col, move.player))
        {
            gameState.setGameOver(true);
            gameState.setWinner(move.player);
        }
        else
        {
            // Switch turn
            gameState.setCurrentPlayer(move.player.equals("X") ? "O" : "X");
        }

        return gameState;
    }

    // Return game state for a given gameId
    public GameState getGameState(String gameId)
    {
        return games.get(gameId);
    }

    // Check if it is the player's turn
    private boolean isPlayerTurn(GameState gameState, String playerId)
    {
        if (gameState.getCurrentPlayer().equals("X"))
        {
            return playerId.equals(gameState.getPlayerXId());
        }
        else
        {
            return playerId.equals(gameState.getPlayerOId());
        }
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////
    // Win check methods (same as before)
    private boolean checkWin(String[][] board, int row, int col, String player)
    {
        return winChecker.hasConnectedFive(board, row, col, player);//Algorithm jar implementation.
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////

}
