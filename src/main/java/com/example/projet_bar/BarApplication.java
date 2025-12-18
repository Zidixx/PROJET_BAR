package com.example.projet_bar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class BarApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BarApplication.class.getResource("/com/example/projet_bar/bar-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1100, 750);

        stage.setTitle("CAFÉ MARITIME CLUB - Menu");
        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
