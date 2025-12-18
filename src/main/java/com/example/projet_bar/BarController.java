package com.example.projet_bar;

import com.example.projet_bar.model.*;
import com.example.projet_bar.service.Bar;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BarController {

    @FXML private VBox paneAccueil, panePrincipal, paneStock;
    @FXML private FlowPane gridCocktails;
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
        genererGrilleMenu();

        colIngredient.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey().getNom()));
        colQuantite.setCellValueFactory(cellData -> {
            int ml = cellData.getValue().getValue();
            int unites = ml / 1000;
            return new SimpleStringProperty(unites + " bouteille(s)");
        });

        for (Employe e : monBar.getEmployes()) {
            if (e instanceof Serveur) {
                comboServeurs.getItems().add(e.getNomComplet());
            }
        }
    }

    @FXML
    protected void onHappyHourToggle() {
        if (btnHappyHour.isSelected()) {
            btnHappyHour.setText("HAPPY HOUR ON (-25%)");
            btnHappyHour.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
        } else {
            btnHappyHour.setText("HAPPY HOUR OFF");
            btnHappyHour.setStyle("-fx-background-color: #34495e; -fx-text-fill: white;");
        }
    }

    @FXML
    protected void onOuvrirBarClick() {
        paneAccueil.setVisible(false);
        panePrincipal.setVisible(true);
    }

    @FXML
    protected void onAppelerServeurClick() {
        if (comboServeurs.getValue() == null || panier.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Veuillez choisir un serveur et des consommations.").show();
            return;
        }

        double montantTips = 0.0;
        List<String> optionsTips = List.of("Pas de pourboire", "5%", "10%", "20%", "Montant libre");
        ChoiceDialog<String> dialogTips = new ChoiceDialog<>("10%", optionsTips);
        dialogTips.setTitle("Pourboire");
        dialogTips.setHeaderText("Un petit geste pour l'équipe ?");
        Optional<String> resultTips = dialogTips.showAndWait();

        if (resultTips.isPresent() && !resultTips.get().equals("Pas de pourboire")) {
            if (resultTips.get().equals("Montant libre")) {
                TextInputDialog libre = new TextInputDialog("1.00");
                Optional<String> val = libre.showAndWait();
                montantTips = val.map(Double::parseDouble).orElse(0.0);
            } else {
                double pourcentage = Double.parseDouble(resultTips.get().replace("%", "")) / 100.0;
                montantTips = montantTotal * pourcentage;
            }
        }

        List<String> modes = List.of("Carte Bancaire", "Apple Pay", "Espèces");
        ChoiceDialog<String> dialogPay = new ChoiceDialog<>("Carte Bancaire", modes);
        dialogPay.setTitle("Paiement");
        dialogPay.setHeaderText("Comment souhaitez-vous régler ?");
        Optional<String> modeChoisi = dialogPay.showAndWait();

        if (modeChoisi.isEmpty()) return;

        double reduction = btnHappyHour.isSelected() ? montantTotal * 0.25 : 0.0;
        double totalFinal = (montantTotal - reduction) + montantTips;

        afficherTicket(montantTips, reduction, totalFinal, modeChoisi.get());

        Alert alertPrep = new Alert(Alert.AlertType.INFORMATION);
        alertPrep.setTitle("Préparation");
        alertPrep.setHeaderText(null);
        alertPrep.setContentText(leBarman.getNomComplet() + " prépare vos cocktails, veuillez patienter...");
        alertPrep.showAndWait();

        onResetPanierClick();
        panePrincipal.setVisible(false);
        paneAccueil.setVisible(true);
    }

    private void afficherTicket(double tips, double reduc, double total, String mode) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        StringBuilder ticket = new StringBuilder("      CAFÉ MARITIME CLUB\n--------------------------------\n");
        ticket.append("Date: ").append(dtf.format(LocalDateTime.now())).append("\n");
        ticket.append("Serveur: ").append(comboServeurs.getValue()).append("\n--------------------------------\n");

        for (Cocktail c : panier) {
            ticket.append(String.format("%-20s %6.2f€\n", c.getNom(), c.getPrix()));
            monBar.consommerStock(c);
        }

        ticket.append("--------------------------------\n");
        ticket.append(String.format("SOUS-TOTAL: %17.2f€\n", montantTotal));
        if (reduc > 0) ticket.append(String.format("REDUC HAPPY HOUR: -%11.2f€\n", reduc));
        ticket.append(String.format("POURBOIRE:  %17.2f€\n", tips));
        ticket.append(String.format("TOTAL TTC:  %17.2f€\n", total));
        ticket.append("--------------------------------\nMode: ").append(mode).append("\n    MERCI !");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Paiement accepté");
        TextArea area = new TextArea(ticket.toString());
        area.setEditable(false);
        area.setPrefSize(320, 450);
        alert.getDialogPane().setContent(area);
        alert.showAndWait();
    }

    @FXML
    protected void afficherStock() {
        tableStock.getItems().setAll(monBar.getStock().entrySet());
        paneStock.setVisible(true);
    }

    @FXML
    protected void fermerStock() {
        paneStock.setVisible(false);
    }

    private void genererGrilleMenu() {
        gridCocktails.getChildren().clear();
        for (Cocktail c : monBar.getMenu()) {
            VBox card = new VBox(10);
            card.setAlignment(Pos.CENTER);
            card.setStyle("-fx-background-color: #1a3c5a; -fx-padding: 15; -fx-background-radius: 20; -fx-border-color: #f39c12; -fx-border-width: 2;");
            card.setPrefSize(180, 230);

            Label icon = new Label(c.isAlcoolise() ? "🍸" : "🥤");
            icon.setStyle("-fx-font-size: 40pt;");
            Label name = new Label(c.getNom().toUpperCase());
            name.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
            Label price = new Label(String.format("%.2f €", c.getPrix()));
            price.setStyle("-fx-text-fill: #f1c40f;");

            Button btnAdd = new Button("AJOUTER");
            btnAdd.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-cursor: hand;");
            btnAdd.setOnAction(e -> {
                panier.add(c);
                montantTotal += c.getPrix();
                lblTotal.setText(String.format("Total : %.2f €", montantTotal));
            });

            card.getChildren().addAll(icon, name, price, btnAdd);
            gridCocktails.getChildren().add(card);
        }
    }

    @FXML
    protected void onResetPanierClick() {
        panier.clear();
        montantTotal = 0;
        lblTotal.setText("Total : 0.00 €");
        comboServeurs.getSelectionModel().clearSelection();
    }

    private void initialiserDonnees() {
        leBarman = new Barman("Puff", "Daddy", 3, 15);
        monBar.ajouterEmploye(leBarman);
        monBar.ajouterEmploye(new Serveur("Laouani", "Sarah", 1, 10));
        monBar.ajouterEmploye(new Serveur("Faure", "Melissa", 2, 10));

        Boisson rhum = new Boisson("Rhum", 15.0, 0.10);
        Boisson menthe = new Boisson("Sirop Menthe", 5.0, 0.8);
        Boisson eau = new Boisson("Eau Pétillante", 2.0, 0.0);
        Boisson vodka = new Boisson("Vodka", 20.0, 0.5);
        Boisson tequila = new Boisson("Tequila", 25.0, 0.10);
        Boisson gin = new Boisson("Gin", 22.0, 0.10);
        Boisson jusOrange = new Boisson("Jus d'Orange", 4.0, 0.03);
        Boisson jusAnanas = new Boisson("Jus d'Ananas", 4.5, 0.03);
        Boisson laitCocos = new Boisson("Lait de Coco", 6.0, 0.03);
        Boisson siropGrenadine = new Boisson("Grenadine", 3.5, 0.0);
        Boisson tripleSec = new Boisson("Triple Sec", 18.0, 0.1);
        Boisson jusCitron = new Boisson("Jus de Citron", 3.0, 0.0);
        Boisson cola = new Boisson("Cola", 3.0, 0.0);
        Boisson champagne = new Boisson("Champagne", 45.0, 1.0);

        monBar.getStock().put(rhum, 5000);
        monBar.getStock().put(menthe, 4000);
        monBar.getStock().put(eau, 10000);
        monBar.getStock().put(vodka, 3000);
        monBar.getStock().put(tequila, 2000);
        monBar.getStock().put(gin, 2000);
        monBar.getStock().put(jusOrange, 6000);
        monBar.getStock().put(jusAnanas, 4000);
        monBar.getStock().put(laitCocos, 3000);
        monBar.getStock().put(siropGrenadine, 2000);
        monBar.getStock().put(tripleSec, 1000);
        monBar.getStock().put(jusCitron, 3000);
        monBar.getStock().put(cola, 5000);
        monBar.getStock().put(champagne, 2000);

        CocktailAlcool mojito = new CocktailAlcool("Mojito", 12.0, 12.0);
        mojito.ajouterIngredient(rhum, 50);
        mojito.ajouterIngredient(menthe, 20);
        mojito.ajouterIngredient(eau, 150);
        monBar.ajouterAuMenu(mojito);

        CocktailAlcool pina = new CocktailAlcool("Pina Colada", 14.0, 10.0);
        pina.ajouterIngredient(rhum, 40);
        pina.ajouterIngredient(jusAnanas, 100);
        pina.ajouterIngredient(laitCocos, 50);
        monBar.ajouterAuMenu(pina);

        CocktailAlcool margarita = new CocktailAlcool("Margarita", 13.0, 15.0);
        margarita.ajouterIngredient(tequila, 50);
        margarita.ajouterIngredient(tripleSec, 20);
        margarita.ajouterIngredient(jusCitron, 30);
        monBar.ajouterAuMenu(margarita);

        CocktailAlcool sunrise = new CocktailAlcool("Tequila Sunrise", 11.0, 12.0);
        sunrise.ajouterIngredient(tequila, 50);
        sunrise.ajouterIngredient(jusOrange, 120);
        sunrise.ajouterIngredient(siropGrenadine, 10);
        monBar.ajouterAuMenu(sunrise);

        CocktailSansAlcool virgin = new CocktailSansAlcool("Virgin Mojito", 8.0, false);
        virgin.ajouterIngredient(eau, 200);
        virgin.ajouterIngredient(menthe, 30);
        virgin.ajouterIngredient(jusCitron, 20);
        monBar.ajouterAuMenu(virgin);
    }
}
