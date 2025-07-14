package Controllers;

import DM.Player;
import Server.Response;
import Service.PlayerService;

import java.util.Map;

public class PlayerController implements IController
{
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public Response handle(Server.Request request)
    {
        String action =request.getAction("action");

        switch (action.toLowerCase())
        {
            case "login":
                return handleLogin(request);
            case "register":
                return handleRegister(request);
            case "delete":
                return handleDelete(request);
            default:
                return new Response(false, "Unknown action: " + action);
        }
    }

    @Override
    public Response handleRequest(String action, Map<String, String> body) {
        return null;
    }

    private Response handleLogin(Server.Request request)
    {
        String username = request.getParam("username");
        String password = request.getParam("password");

        try
        {
            Player player = playerService.login(username, password);
            return new Response(true, "Login successful", player);
        }
        catch (Exception e)
        {
            return new Response(false, "Login failed: " + e.getMessage());
        }
    }

    private Response handleRegister(Server.Request request)
    {
        String username = request.getParam("username");
        String password = request.getParam("password");

        try
        {
            playerService.registerPlayer(username, password);
            return new Response(true, "Registration successful");
        }
        catch (Exception e)
        {
            return new Response(false, "Registration failed: " + e.getMessage());
        }
    }

    private Response handleDelete(Server.Request request)
    {
        String username = request.getParam("username");
        String password = request.getParam("password");

        try
        {
            Player player = playerService.login(username, password);
            playerService.removePlayer(player);
            return new Response(true, "Player deleted");
        }
        catch (Exception e)
        {
            return new Response(false, "Delete failed: " + e.getMessage());
        }
    }
}
