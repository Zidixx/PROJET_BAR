package com.example.projet_bar.ihm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BarApplication extends Application {

    // --- 1. La méthode start (point d'entrée de la fenêtre) ---
    @Override
    public void start(Stage stage) throws IOException {

        // --- CHARGEMENT DU FICHIER FXML ---

        // 1. Indique à JavaFX quel fichier FXML charger.
        //    (Vous devrez créer 'bar-view.fxml' à l'étape suivante)
        FXMLLoader fxmlLoader = new FXMLLoader(BarApplication.class.getResource("bar-view.fxml"));

        // 2. Crée la scène à partir du FXML
        Scene scene = new Scene(fxmlLoader.load(), 800, 600); // Taille initiale 800x600

        // 3. Configuration de la fenêtre (Stage)
        stage.setTitle("🍹 PROJET BAR - Gestion des Commandes");
        stage.setScene(scene);
        stage.show();
    }

    // --- 2. La méthode main (point de lancement de l'application) ---
    public static void main(String[] args) {
        launch();
    }
}