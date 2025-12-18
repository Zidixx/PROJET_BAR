package com.example.projet_bar;

import com.example.projet_bar.model.*;
import com.example.projet_bar.service.Bar;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BarController {

    @FXML private VBox paneAccueil, paneStock;
    @FXML private BorderPane panePrincipal;
    @FXML private ListView<String> listMenu;
    @FXML private ListView<String> listPanier;
    @FXML private ComboBox<String> comboServeurs;
    @FXML private Label lblTotal;
    @FXML private ToggleButton btnHappyHour;
    @FXML private TableView<Map.Entry<Boisson, Integer>> tableStock;
    @FXML private TableColumn<Map.Entry<Boisson, Integer>, String> colIngredient;
    @FXML private TableColumn<Map.Entry<Boisson, Integer>, String> colQuantite;

    private Bar monBar;
    private List<Cocktail> panier = new ArrayList<>();
    private double montantTotal = 0.0;
    private Barman leBarman;

    @FXML
    public void initialize() {
        monBar = new Bar("Café Maritime Club");
        initialiserDonnees();
        rafraichirInterface();

        if (colIngredient != null && colQuantite != null) {
            colIngredient.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey().getNom()));
            colQuantite.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue() + " ml"));
        }

        for (Employe e : monBar.getEmployes()) {
            if (e instanceof Serveur) {
                comboServeurs.getItems().add(e.getNomComplet());
            }
        }
    }

    private void rafraichirInterface() {
        listMenu.getItems().clear();
        for (Cocktail c : monBar.getMenu()) {
            // Emojis supprimés ici
            listMenu.getItems().add(c.getNom().toUpperCase() + " - " + String.format("%.2f €", c.getPrix()));
        }
    }

    @FXML
    protected void onAjouterAuPanier() {
        int index = listMenu.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            Cocktail choisi = monBar.getMenu().get(index);
            panier.add(choisi);
            listPanier.getItems().add(choisi.getNom() + " (" + String.format("%.2f €", choisi.getPrix()) + ")");
            montantTotal += choisi.getPrix();
            mettreAJourTotal();
        }
    }

    private void mettreAJourTotal() {
        double totalAffiche = btnHappyHour.isSelected() ? montantTotal * 0.75 : montantTotal;
        lblTotal.setText(String.format("Total : %.2f €", totalAffiche));
    }

    @FXML
    protected void onAppelerServeurClick() {
        if (comboServeurs.getValue() == null || panier.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Veuillez choisir un serveur et des consommations.").show();
            return;
        }

        // --- MENU DES POURBOIRES (TIPS) ---
        double montantTips = 0.0;
        List<String> optionsTips = List.of("Pas de pourboire", "5%", "10%", "20%", "Montant libre");
        ChoiceDialog<String> dialogTips = new ChoiceDialog<>("10%", optionsTips);
        dialogTips.setTitle("Pourboire");
        dialogTips.setHeaderText("Souhaitez-vous laisser un pourboire ?");
        Optional<String> resultTips = dialogTips.showAndWait();

        if (resultTips.isPresent() && !resultTips.get().equals("Pas de pourboire")) {
            if (resultTips.get().equals("Montant libre")) {
                TextInputDialog libre = new TextInputDialog("2.00");
                libre.setTitle("Pourboire Libre");
                libre.setHeaderText("Entrez le montant du pourboire :");
                Optional<String> val = libre.showAndWait();
                montantTips = val.map(Double::parseDouble).orElse(0.0);
            } else {
                double pourcentage = Double.parseDouble(resultTips.get().replace("%", "")) / 100.0;
                montantTips = montantTotal * pourcentage;
            }
        }

        // --- CHOIX DU MODE DE PAIEMENT ---
        List<String> modes = List.of("Carte Bancaire", "Espèces", "Apple Pay");
        ChoiceDialog<String> dialogPay = new ChoiceDialog<>("Carte Bancaire", modes);
        dialogPay.setTitle("Paiement");
        dialogPay.setHeaderText("Sélectionnez le mode de règlement");
        Optional<String> resultPay = dialogPay.showAndWait();

        if (resultPay.isEmpty()) return;

        double reduction = btnHappyHour.isSelected() ? montantTotal * 0.25 : 0.0;
        double totalFinal = (montantTotal - reduction) + montantTips;

        // --- GÉNÉRATION DU TICKET ---
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        StringBuilder ticket = new StringBuilder("      CAFÉ MARITIME CLUB\n");
        ticket.append("--------------------------------\n");
        ticket.append("Date: ").append(dtf.format(LocalDateTime.now())).append("\n");
        ticket.append("Serveur: ").append(comboServeurs.getValue()).append("\n");
        ticket.append("Mode: ").append(resultPay.get()).append("\n");
        ticket.append("--------------------------------\n");

        for (Cocktail c : panier) {
            ticket.append(String.format("%-20s %6.2f€\n", c.getNom(), c.getPrix()));
            monBar.consommerStock(c);
        }

        ticket.append("--------------------------------\n");
        ticket.append(String.format("SOUS-TOTAL: %17.2f€\n", montantTotal));
        if (reduction > 0) ticket.append(String.format("HAPPY HOUR: -%16.2f€\n", reduction));
        ticket.append(String.format("POURBOIRE:  %17.2f€\n", montantTips));
        ticket.append(String.format("TOTAL TTC:  %17.2f€\n", totalFinal));
        ticket.append("--------------------------------\n      MERCI !");

        Alert alertTicket = new Alert(Alert.AlertType.INFORMATION);
        alertTicket.setTitle("Paiement accepté");
        TextArea area = new TextArea(ticket.toString());
        area.setEditable(false);
        alertTicket.getDialogPane().setContent(area);
        alertTicket.showAndWait();

        Alert alertPrep = new Alert(Alert.AlertType.INFORMATION);
        alertPrep.setContentText(leBarman.getNomComplet() + " prépare vos cocktails. Veuillez patienter...");
        alertPrep.showAndWait();

        onResetPanierClick();
    }

    private void initialiserDonnees() {
        leBarman = new Barman("Daddy", "Puff", 3, 15);
        monBar.ajouterEmploye(leBarman);
        monBar.ajouterEmploye(new Serveur("Pina", "Claudia", 1, 10));
        monBar.ajouterEmploye(new Serveur("Campos", "O", 2, 10));

        // Boissons de base
        Boisson rhum = new Boisson("Rhum", 15.0, 0.10);
        Boisson vodka = new Boisson("Vodka", 20.0, 0.5);
        Boisson tequila = new Boisson("Tequila", 25.0, 0.10);
        Boisson gin = new Boisson("Gin", 22.0, 0.10);
        Boisson jusO = new Boisson("Jus d'Orange", 0.0, 0.03);
        Boisson jusA = new Boisson("Jus d'Ananas", 0.0, 0.03);
        Boisson eau = new Boisson("Eau Pétillante", 0.0, 0.01);
        Boisson menthe = new Boisson("Menthe", 0.0, 0.02);
        Boisson cola = new Boisson("Cola", 0.0, 0.02);
        Boisson citron = new Boisson("Citron", 0.0, 0.01);

        monBar.getStock().put(rhum, 5000); monBar.getStock().put(vodka, 3000);
        monBar.getStock().put(tequila, 2000); monBar.getStock().put(gin, 2000);
        monBar.getStock().put(jusO, 6000); monBar.getStock().put(jusA, 4000);
        monBar.getStock().put(eau, 10000); monBar.getStock().put(menthe, 2000);
        monBar.getStock().put(cola, 5000); monBar.getStock().put(citron, 2000);

        // --- LES 15 COCKTAILS (10 nouveaux + 5 anciens) ---
        monBar.ajouterAuMenu(creerCocktail("Mojito", 12.0, true, Map.of(rhum, 50, menthe, 20, eau, 150)));
        monBar.ajouterAuMenu(creerCocktail("Pina Colada", 10.0, true, Map.of(rhum, 40, jusA, 100)));
        monBar.ajouterAuMenu(creerCocktail("Margarita", 15.0, true, Map.of(tequila, 50, citron, 30)));
        monBar.ajouterAuMenu(creerCocktail("Tequila Sunrise", 12.0, true, Map.of(tequila, 50, jusO, 120)));
        monBar.ajouterAuMenu(creerCocktail("Vodka Orange", 14.0, true, Map.of(vodka, 50, jusO, 150)));
        monBar.ajouterAuMenu(creerCocktail("Gin Tonic", 13.0, true, Map.of(gin, 50, eau, 150)));
        monBar.ajouterAuMenu(creerCocktail("Cuba Libre", 11.0, true, Map.of(rhum, 50, cola, 150)));
        monBar.ajouterAuMenu(creerCocktail("Moscow Mule", 14.0, true, Map.of(vodka, 50, citron, 20, eau, 100)));
        monBar.ajouterAuMenu(creerCocktail("Gin Fizz", 12.0, true, Map.of(gin, 40, citron, 30, eau, 100)));
        monBar.ajouterAuMenu(creerCocktail("Tequila Shot", 38.0, true, Map.of(tequila, 30)));
        monBar.ajouterAuMenu(creerCocktail("Virgin Mojito", 0.0, false, Map.of(eau, 200, menthe, 30)));
        monBar.ajouterAuMenu(creerCocktail("Virgin Colada", 0.0, false, Map.of(jusA, 150, jusO, 50)));
        monBar.ajouterAuMenu(creerCocktail("Citronnade", 0.0, false, Map.of(citron, 50, eau, 200)));
        monBar.ajouterAuMenu(creerCocktail("Orange Pressée", 0.0, false, Map.of(jusO, 250)));
        monBar.ajouterAuMenu(creerCocktail("Cola Glacé", 0.0, false, Map.of(cola, 300)));
    }

    private Cocktail creerCocktail(String nom, double degre, boolean alcoolise, Map<Boisson, Integer> recette) {
        Cocktail c = alcoolise ? new CocktailAlcool(nom, 8.0, degre) : new CocktailSansAlcool(nom, 5.0, false);
        recette.forEach(c::ajouterIngredient);
        return c;
    }

    @FXML protected void onHappyHourToggle() { btnHappyHour.setText(btnHappyHour.isSelected() ? "HAPPY HOUR ON (-25%)" : "HAPPY HOUR OFF"); mettreAJourTotal(); }
    @FXML protected void afficherStock() { tableStock.getItems().setAll(monBar.getStock().entrySet()); paneStock.setVisible(true); }
    @FXML protected void fermerStock() { paneStock.setVisible(false); }
    @FXML protected void onOuvrirBarClick() { paneAccueil.setVisible(false); panePrincipal.setVisible(true); }
    @FXML protected void onResetPanierClick() { panier.clear(); listPanier.getItems().clear(); montantTotal = 0; mettreAJourTotal(); panePrincipal.setVisible(false); paneAccueil.setVisible(true); }
}