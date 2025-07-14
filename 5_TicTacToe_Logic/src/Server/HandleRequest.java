package Server;

import Controllers.ControllerFactory;
import Controllers.GameController;
import Controllers.IController;
import Controllers.PlayerController;
import Service.GameService;
import Service.PlayerService;

import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HandleRequest implements Runnable
{
    private final Socket clientSocket;
    private final ControllerFactory controllerFactory;
    private final Gson gson = new Gson();
    private final Gson gson2 = new Gson();

    public HandleRequest(Socket clientSocket, PlayerService playerService)
    {
        this.clientSocket = clientSocket;

        // Register controllers
        Map<String, IController> controllers = new HashMap<>();
        controllers.put("player", new PlayerController(playerService));
        controllers.put("game", new GameController(new GameService()));
        this.controllerFactory = new ControllerFactory(controllers);
    }

    @Override
    public void run()
    {
        System.out.println("📥 HandleRequest started for: " + clientSocket.getInetAddress());

        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String json = reader.readLine(); // One request per connection
            if (json == null) return;

            Request request = gson.fromJson(json, Request.class);
            String controllerName = request.getAction("Controller");

            IController controller = controllerFactory.getController(controllerName);

            Response response;

            if (controller == null)
            {
                response = new Response(false, "❌ Controller not found: " + controllerName);
            }
            else
            {
                response = controller.handle(request);
            }
            if (response == null)
            {
                response = controller.handleRequest(request.getHeaders().get("action"), request.getBody());
            }

            // Log to file
            try (FileWriter fileWriter = new FileWriter("response_log.json"))
            {
                fileWriter.write("{\n  \"response\": " + gson.toJson(response) + "\n}");
            }
            catch (IOException e) {
                System.err.println("⚠️ Failed to log response: " + e.getMessage());
            }

            // Send to client
            writer.println(gson.toJson(response));

        }
        catch (Exception e)
        {
            System.err.println("❌ Error handling client: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
