package Controllers;

import Server.Request;
import Server.Response;

import java.util.Map;

public interface IController
{
    Response handle(Request request);
    Response handleRequest(String action, Map<String, String> body);
}

