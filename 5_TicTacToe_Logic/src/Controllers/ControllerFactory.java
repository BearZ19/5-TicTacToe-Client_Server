package Controllers;

import java.util.HashMap;
import java.util.Map;

public class ControllerFactory
{
    private static final Map<String, IController> controllerMap = new HashMap<>();

    public ControllerFactory(Map<String, IController> controllers) {
    }

    // Register a controller with a key
    public static void register(String key, IController controller) {
        controllerMap.put(key.toLowerCase(), controller);
    }

    // Retrieve a controller by key
    public static IController getController(String key) {
        if (key == null) return null;
        return controllerMap.get(key.toLowerCase());
    }
}




