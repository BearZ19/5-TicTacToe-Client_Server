package com.example.combined_tictactoe_ui;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ClientGame
{
    private final int SIZE = 10;
    private final Button[][] board = new Button[SIZE][SIZE];
    private Label statusLabel = new Label("Press Start or Join to begin");
    private String currentPlayer = "X";
    private String gameId;
    private String playerId;
    private String playerSymbol; // "X" or "O"
    private Gson gson = new Gson();
    private Timer pollTimer;
    private Button startBtn;
    private Button joinBtn;

    public void start(Stage primaryStage)
    {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(2);
        grid.setVgap(2);

        for (int r = 0; r < SIZE; r++)
        {
            for (int c = 0; c < SIZE; c++)
            {
                Button btn = new Button();
                btn.setPrefSize(57, 57);
                btn.setFont(Font.font("Arial", FontWeight.BOLD, 24));
                btn.setTextFill(Color.RED);

                String defaultStyle =
                        "-fx-background-color: #e0e0e0;" +
                                "-fx-border-color: #8ab4f8;" +
                                "-fx-border-width: 2px;" +
                                "-fx-border-radius: 4;" +
                                "-fx-background-radius: 4;";

                String hoverStyle =
                        "-fx-background-color: #d0e8ff;" +  // lighter blue on hover
                                "-fx-border-color: #66aaff;" +
                                "-fx-border-width: 2px;" +
                                "-fx-border-radius: 4;" +
                                "-fx-background-radius: 4;";

                btn.setStyle(defaultStyle);

                // hover effect
                btn.setOnMouseEntered(e -> btn.setStyle(hoverStyle));
                btn.setOnMouseExited(e -> btn.setStyle(defaultStyle));

                int row = r, col = c;
                btn.setOnAction(e -> handleCellClick(row, col));
                board[r][c] = btn;
                grid.add(btn, c, r);
            }
        }

        startBtn = new Button("Start New Game");
        joinBtn = new Button("Join Game");

        startBtn.setOnAction(e -> startGame());
        joinBtn.setOnAction(e -> joinGame());

        HBox buttons = new HBox(10, startBtn, joinBtn);
        buttons.setAlignment(Pos.CENTER);

        VBox root = new VBox(10, grid, buttons, statusLabel);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #ffffff, #e6f2ff);"); // bright gradient background

        Scene scene = new Scene(root, 650, 600);
        primaryStage.setTitle("❌⭕ 5 in a Row TicTacToe Client");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startGame()
    {
        Response resp = sendRequest("start", null);
        if (resp.isSuccess())
        {
            GameState gs = gson.fromJson(gson.toJson(resp.getData()), GameState.class);
            gameId = gs.getGameId();
            playerId = gs.getPlayerXId();
            playerSymbol = "X";
            currentPlayer = gs.getCurrentPlayer();

            clearBoard();
            updateBoard(gs);
            statusLabel.setText("Game started. You are Player X. Your turn.");

            // Disable start/join buttons to prevent new game/join until this game ends
            startBtn.setDisable(true);
            joinBtn.setDisable(true);

            startPolling();
        }
        else
        {
            statusLabel.setText("Failed to start game: " + resp.getMessage());
        }
    }

    private void joinGame()
    {
        Response resp = sendRequest("join", null);

        if (resp.isSuccess())
        {
            GameState gs = gson.fromJson(gson.toJson(resp.getData()), GameState.class);
            gameId = gs.getGameId();
            playerId = gs.getPlayerOId();
            playerSymbol = "O";
            currentPlayer = gs.getCurrentPlayer();

            clearBoard();
            updateBoard(gs);
            statusLabel.setText("Joined game. You are Player O. Wait for your turn.");

            // Disable start/join buttons to prevent new game/join until this game ends
            startBtn.setDisable(true);
            joinBtn.setDisable(true);

            startPolling();
        }
        else
        {
            statusLabel.setText("Failed to join game: " + resp.getMessage());
        }
    }

    private void handleCellClick(int row, int col)
    {
        if (gameId == null || playerId == null)
        {
            statusLabel.setText("Start or join a game first.");
            return;
        }

        // Only allow move if it is this player's turn
        if (!currentPlayer.equals(playerSymbol)) {
            statusLabel.setText("Not your turn.");
            return;
        }

        if (!board[row][col].getText().isEmpty()) {
            statusLabel.setText("Cell already occupied.");
            return;
        }

        Map<String, String> body = new HashMap<>();
        body.put("gameId", gameId);
        body.put("playerId", playerId);
        body.put("row", String.valueOf(row));
        body.put("col", String.valueOf(col));
        body.put("player", playerSymbol);

        Response resp = sendRequest("move", body);

        if (resp.isSuccess())
        {
            GameState gs = gson.fromJson(gson.toJson(resp.getData()), GameState.class);
            updateBoard(gs);
            currentPlayer = gs.getCurrentPlayer();

            if (gs.isGameOver())
            {
                if (gs.getWinner() != null)
                {
                    if (gs.getWinner().equals(playerSymbol))
                    {
                        statusLabel.setText("You won!");
                    }
                    else
                    {
                        statusLabel.setText("You lost! Winner: " + gs.getWinner());
                    }
                }
                else
                {
                    statusLabel.setText("Game over. It's a draw.");
                }

                stopPolling();

                // Enable start/join buttons again to allow new games
                startBtn.setDisable(false);
                joinBtn.setDisable(false);

                // Reset game state variables if you want to allow clean start
                gameId = null;
                playerId = null;
                playerSymbol = null;
            }
            else
            {
                statusLabel.setText("Player " + currentPlayer + "'s turn");
            }
        }
        else
        {
            statusLabel.setText("Invalid move: " + resp.getMessage());
        }
    }

    private void startPolling()
    {
        stopPolling(); // Just in case

        pollTimer = new Timer(true);
        pollTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (gameId == null) return;
                Map<String, String> body = new HashMap<>();
                body.put("gameId", gameId);
                Response resp = sendRequest("state", body);
                if (resp.isSuccess())
                {
                    GameState gs = gson.fromJson(gson.toJson(resp.getData()), GameState.class);
                    Platform.runLater(() -> {
                        updateBoard(gs);
                        currentPlayer = gs.getCurrentPlayer();

                        if (gs.isGameOver())
                        {
                            if (gs.getWinner() != null)
                            {
                                if (gs.getWinner().equals(playerSymbol))
                                {
                                    statusLabel.setText("You won!");
                                }
                                else
                                {
                                    statusLabel.setText("You lost! Winner: " + gs.getWinner());
                                }
                            }
                            else
                            {
                                statusLabel.setText("Game over. It's a draw.");
                            }

                            stopPolling();

                            // Enable start/join buttons again to allow new games
                            startBtn.setDisable(false);
                            joinBtn.setDisable(false);

                            // Reset game state variables
                            gameId = null;
                            playerId = null;
                            playerSymbol = null;
                        }
                        else
                        {
                            statusLabel.setText("Player " + currentPlayer + "'s turn");
                        }
                    });
                }
            }
        }, 0, 1000); // Poll every second
    }

    private void stopPolling()
    {
        if (pollTimer != null)
        {
            pollTimer.cancel();
            pollTimer = null;
        }
    }

    private Response sendRequest(String action, Map<String, String> body)
    {
        try (Socket socket = new Socket("localhost", 12345);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            Map<String, String> headers = new HashMap<>();
            headers.put("Controller", "game");
            headers.put("action", action);

            if (body == null)
            {
                body = new HashMap<>();
            }

            Request request = new Request(headers, body);
            String json = gson.toJson(request);
            out.println(json);

            String responseJson = in.readLine();
            Response response = gson.fromJson(responseJson, Response.class);

            if (response.getData() != null)
            {
                String gameStateJson = gson.toJson(response.getData());
                GameState gs = gson.fromJson(gameStateJson, GameState.class);
                return new Response(response.isSuccess(), response.getMessage(), gs);
            }
            return response;

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new Response(false, "Connection failed");
        }
    }

    private void updateBoard(GameState gs)
    {
        String[][] boardData = gs.getBoard();
        for (int r = 0; r < SIZE; r++)
        {
            for (int c = 0; c < SIZE; c++)
            {
                board[r][c].setText(boardData[r][c] == null ? "" : boardData[r][c]);
            }
        }
    }

    private void clearBoard()
    {
        for (Button[] row : board)
        {
            for (Button cell : row)
            {
                cell.setText("");
            }
        }
    }
}
