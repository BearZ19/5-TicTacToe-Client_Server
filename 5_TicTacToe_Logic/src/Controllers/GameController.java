package Controllers;

import DM.GameState;
import DM.Move;
import Server.Response;
import Service.GameService;
import java.util.Map;

public class GameController implements IController
{
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public Response handle(Server.Request request)
    {
        return null;
    }

    public Response handleRequest(String action, Map<String, String> body)
    {
        switch (action)
        {
            case "start":
                GameState newState = gameService.startNewGame();
                return new Response(true, "Game started", newState);

            case "join":
                GameState joinedGame = gameService.joinGame();
                if (joinedGame != null)
                {
                    return new Response(true, "Joined game", joinedGame);
                }
                else
                {
                    return new Response(false, "No available games to join");
                }

            case "move":
                String gameId = body.get("gameId");
                String playerId = body.get("playerId");
                int row = Integer.parseInt(body.get("row"));
                int col = Integer.parseInt(body.get("col"));
                String player = body.get("player");
                GameState updatedState = gameService.makeMove(gameId, playerId, new Move(row, col, player));
                if (updatedState != null)
                {
                    return new Response(true, "Move processed", updatedState);
                }
                else
                {
                    return new Response(false, "Invalid game or move");
                }

            case "state":
                String gameIdForState = body.get("gameId");
                GameState gameState = gameService.getGameState(gameIdForState);
                if (gameState != null)
                {
                    return new Response(true, "Game state retrieved", gameState);
                }
                else
                {
                    return new Response(false, "Game not found");
                }

            default:
                return new Response(false, "Unknown action");
        }
    }
}
