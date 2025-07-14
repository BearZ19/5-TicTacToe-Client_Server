package com.example.combined_tictactoe_ui;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClientConnector extends Application
{
    @Override
    public void start(Stage stage)
    {
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        TextField passwordField = new TextField();
        passwordField.setPromptText("Password");

        Button registerButton = new Button("Register");
        Button loginButton = new Button("Login");
        Button deleteButton = new Button("Delete");

        // Response label
        Label responseLabel = new Label();

        //  Welcome label
        Label welcomeLabel = new Label("Welcome to 5-in-a-Row TicTacToe Game!");
        welcomeLabel.setStyle(
                "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #2a4d69;" +
                        "-fx-padding: 0 0 10 0;"
        );

        // Layout
        VBox loginLayout = new VBox(10,
                welcomeLabel,
                new Label("Enter your credentials:"),
                usernameField,
                passwordField,
                registerButton,
                loginButton,
                deleteButton,
                responseLabel
        );

        loginLayout.setStyle(
                "-fx-background-color: linear-gradient(to bottom right, #ffffff, #d0e7ff);" +
                        "-fx-padding: 30;" +
                        "-fx-background-radius: 10;"
        );

        // Scene setup
        Scene loginScene = new Scene(loginLayout, 415, 350);

        // Login
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String response = ClientRequestSender.sendLoginRequest(username, password);
            System.out.println(response);
            Gson gson = new Gson();
            Response response1 = gson.fromJson(response, Response.class);
            System.out.println(response1.getMessage());
            System.out.println("Raw response: " + response);

            if (response1.isSuccess())
            {
                try
                {
                    ClientGame clientGame = new ClientGame();
                    //Stage stage = new Stage();
                    clientGame.start(stage);  // Launch the game window
                    ((Stage) loginButton.getScene().getWindow()).close(); // close login window
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText("Invalid Credentials");
                alert.setContentText(response1.getMessage()); // show the message from the response
                alert.showAndWait();
            }
        });

        // Register
        registerButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String response = ClientRequestSender.sendRegisterRequest(username, password);
            System.out.println(response);
            Gson gson = new Gson();
            Response response1 = gson.fromJson(response, Response.class);
            System.out.println(response1.getMessage());
            System.out.println("Raw response: " + response);

            if (response1.isSuccess())
            {
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Register Successful");
                successAlert.setHeaderText(null);
                successAlert.setContentText("You have Registered in successfully!");
                successAlert.showAndWait();
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Register Failed");
                alert.setHeaderText("Invalid Credentials");
                alert.setContentText(response1.getMessage()); // show the message from the response
                alert.showAndWait();
            }
        });

        stage.setScene(loginScene);
        stage.setTitle("❌⭕ Login/Register");
        stage.show();


        //delete
        deleteButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String response = ClientRequestSender.sendDeleteRequest(username, password);
            System.out.println(response);
            Gson gson = new Gson();
            Response response1 = gson.fromJson(response, Response.class);
            System.out.println(response1.getMessage());
            System.out.println("Raw response: " + response);

            if (response1.isSuccess())
            {
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Delete Successful");
                successAlert.setHeaderText(null);
                successAlert.setContentText("You have Deleted successfully!");
                successAlert.showAndWait();
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Delete Failed");
                alert.setHeaderText("Invalid Credentials");
                alert.setContentText(response1.getMessage()); // show the message from the response
                alert.showAndWait();
            }
        });
    }

    public static void main(String[] args)
    {
        launch();
    }
}
