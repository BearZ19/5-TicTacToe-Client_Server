module com.example.combined_tictactoe_ui {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens com.example.combined_tictactoe_ui to javafx.fxml;
    exports com.example.combined_tictactoe_ui;
}