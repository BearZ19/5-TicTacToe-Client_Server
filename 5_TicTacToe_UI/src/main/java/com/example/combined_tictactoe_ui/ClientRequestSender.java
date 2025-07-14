package com.example.combined_tictactoe_ui;

import com.google.gson.Gson;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientRequestSender {

    public static String sendLoginRequest(String username, String password)
    {
        return sendRequest("login", username, password);
    }

    public static String sendRegisterRequest(String username, String password)
    {
        return sendRequest("register", username, password);
    }

    public static String sendDeleteRequest(String username, String password)
    {
        return sendRequest("delete", username, password);
    }

    private static String sendRequest(String action, String username, String password)
    {
        try (
                Socket socket = new Socket("localhost", 12345);
                PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            // 1. Create the parameters map
            Map<String, String> headers = new HashMap<>();
            headers.put("action", action);
            headers.put("Controller","player");

            Map<String, String> body = new HashMap<>();
            body.put("username", username);
            body.put("password", password);

            // 2. Wrap it into a Request object
            Request request = new Request(headers , body);

            // 3. Convert to JSON
            Gson gson = new Gson();
            String json = gson.toJson(request);

            try (FileWriter fileWriter = new FileWriter("request_log.json"))
            {
                fileWriter.write("{\n");
                fileWriter.write("  \"request\": ");
                fileWriter.write(json);
                fileWriter.write("\n}");
            }

            // 4. Send
            out.println(json);

            // 5. Wait for response
            return in.readLine();

        }
        catch (IOException e)
        {
            e.printStackTrace();
            return "Connection failed";
        }
    }
}
