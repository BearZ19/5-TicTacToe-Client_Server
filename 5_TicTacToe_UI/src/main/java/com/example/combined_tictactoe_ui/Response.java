package com.example.combined_tictactoe_ui;

public class Response
{
    public boolean success;
    public String message;
    public Object data;

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

    public boolean isSuccess()
    {
        return success;
    }

    public String getMessage()
    {
        return message;
    }

    public Object getData()
    {
        return data;
    }
}
