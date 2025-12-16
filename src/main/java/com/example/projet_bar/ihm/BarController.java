package com.example.projet_bar.ihm;

// Importations JavaFX
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

// Importations de votre modèle et service (Logique Métier)
import com.example.projet_bar.model.*;
import com.example.projet_bar.service.Bar;

import java.net.URL;
import java.util.ResourceBundle;

public class BarController implements Initializable {

    // --- 1. LIAISON FXML (Les éléments graphiques) ---
    // Ces variables correspondent aux fx:id que vous avez mis dans bar-view.fxml
    @FXML
    private Button btnAfficherMenu;

    @FXML
    private Button btnTesterStock;

    @FXML
    private Label lblResultat;

    // --- 2. LOGIQUE MÉTIER ---
    private Bar monBar; // Instance de votre classe de gestion

    // --- 3. INITIALISATION (Exécutée après le chargement du FXML) ---
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Crée l'instance du Bar
        monBar = new Bar("Le Cozy Bar");

        // Initialisation des données de test
        initialiserDonneesTest();

        // Lie les boutons aux méthodes Java (gestionnaires d'événements)
        btnAfficherMenu.setOnAction(event -> afficherMenu());
        btnTesterStock.setOnAction(event -> testerStock());

        lblResultat.setText("Le Bar est prêt ! " + monBar.getMenu().size() + " cocktails au menu.");
    }

    // --- 4. MÉTHODES GESTIONNAIRES D'ÉVÉNEMENTS ---

    private void afficherMenu() {
        StringBuilder sb = new StringBuilder("--- Menu du Bar ---\n");
        for (Cocktail c : monBar.getMenu()) {
            String type = c.isAlcoolise() ? "🍹" : "🥤";
            sb.append(String.format("%s %s : %.2f € (Coût Prod: %.2f €)\n",
                    type, c.getNom(), c.getPrix(), c.calculerCoutProduction()));
        }
        lblResultat.setText(sb.toString());
    }

    private void testerStock() {
        Cocktail mojito = monBar.getMenu().stream()
                .filter(c -> c.getNom().equals("Mojito"))
                .findFirst().orElse(null);

        if (mojito != null) {
            boolean stockOK = monBar.verifierStock(mojito);
            if (stockOK) {
                lblResultat.setText("Le stock est suffisant pour préparer un Mojito !");
            } else {
                lblResultat.setText("Stock INSUFFISANT pour Mojito. Veuillez réapprovisionner !");
            }
        } else {
            lblResultat.setText("Erreur : Le Mojito n'est pas dans le menu de test.");
        }
    }

    // --- 5. MÉTHODES DE PRÉPARATION DES DONNÉES (Pour la démo) ---

    private void initialiserDonneesTest() {
        // Ingrédients de base (Boisson: Nom, Degré d'alcool, Coût par mL/cL)
        Boisson rhum = new Boisson("Rhum Blanc", 40.0, 0.05); // 0.05€/mL = 5€/10cL
        Boisson menthe = new Boisson("Menthe Fraîche", 0.0, 0.01);
        Boisson perrier = new Boisson("Eau pétillante", 0.0, 0.005);

        // Stock initial du Bar (mL)
        monBar.ajouterStock(rhum, 500); // 500 mL de Rhum
        monBar.ajouterStock(menthe, 200);
        monBar.ajouterStock(perrier, 1000);

        // Création des Cocktails
        Cocktail mojito = new CocktailAlcool("Mojito", 6.0, 10.0); // Nom, PrixBase, Degre
        mojito.ajouterIngredient(rhum, 50);    // 50 mL
        mojito.ajouterIngredient(menthe, 10);  // 10 mL
        mojito.ajouterIngredient(perrier, 100); // 100 mL

        Cocktail virgin = new CocktailSansAlcool("Virgin Mojito", 5.0, true); // Nom, PrixBase, estJusDeFruit
        virgin.ajouterIngredient(menthe, 10);
        virgin.ajouterIngredient(perrier, 100);

        monBar.ajouterAuMenu(mojito);
        monBar.ajouterAuMenu(virgin);

        // Ajout d'employés
        monBar.ajouterEmploye(new Serveur("Alice", 2, 8));
        monBar.ajouterEmploye(new Barman("Bob", 5, 10));
    }
}