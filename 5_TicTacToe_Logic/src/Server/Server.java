package Server;

import Controllers.ControllerFactory;
import Controllers.GameController;
import Controllers.IController;
import Controllers.PlayerController;
import Service.GameService;
import Service.PlayerService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    private final int port;
    private final PlayerService playerService;

    public Server(int port, PlayerService playerService)
    {
        this.port = port;
        this.playerService = playerService;
    }

    public void run()
    {
        GameService gameService = new GameService();
        IController gameController = new GameController(gameService);
        ControllerFactory.register("game", gameController);

        IController playerController = new PlayerController(playerService);
        ControllerFactory.register("player", playerController);

        try (ServerSocket serverSocket = new ServerSocket(port))
        {
            System.out.println("Server is running on port " + port);

            while (true)
            {
                try
                {
                    System.out.println("Waiting for a client...");
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connected: " + clientSocket.getInetAddress());

                    // Pass PlayerService if needed (or null if not used by HandleRequest)
                    new Thread(new HandleRequest(clientSocket, playerService)).start();

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    System.out.println("Failed to handle client connection.");
                }
            }

        }
        catch (IOException e)
        {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
