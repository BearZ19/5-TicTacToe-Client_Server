package Server;

import java.util.Map;

public class Request
{
    private final Map<String, String> headers;
    private final Map<String, String> body;

    public Request(Map<String, String> headers, Map<String, String> body)
    {
        this.headers = headers;
        this.body = body;
    }

    public Map<String, String> getHeaders()
    {
        return headers;
    }

    public Map<String, String> getBody()
    {
        return body;
    }

    // Convenience method to get a specific header by key
    public String getHeader(String key)
    {
        return headers.get(key);
    }

    // Convenience method to get a specific param from the body
    public String getParam(String key)
    {
        return body.get(key);
    }

    public String getAction(String key)
    {

        return headers.get(key);
    }
}

