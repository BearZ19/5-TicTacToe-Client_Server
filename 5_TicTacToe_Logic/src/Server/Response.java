package Server;

public class Response
{
    private final boolean success;
    private final String message;
    private final Object data;

    public Response(boolean success, String message)
    {
        this(success, message, null);
    }

    public Response(boolean success, String message, Object data)
    {
        this.success = success;
        this.message = message;
        this.data = data;
    }
}