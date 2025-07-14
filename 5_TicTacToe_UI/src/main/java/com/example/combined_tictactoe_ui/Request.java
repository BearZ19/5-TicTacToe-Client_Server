package com.example.combined_tictactoe_ui;

import java.util.Map;

public class Request
{
    public Map<String, String> headers;
    public Map<String, String> body;

    // Required no-arg constructor for Gson
    public Request() { }

    public Request(Map<String, String> headers, Map<String, String> body)
    {
        this.headers = headers;
        this.body = body;
    }
}
