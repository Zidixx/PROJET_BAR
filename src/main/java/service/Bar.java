package service;

// Importe toutes les classes du modèle pour pouvoir les utiliser
import com.example.projet_bar.Model.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class Bar {

    private String nom;
    private Map<Boisson, Integer> stock; // La quantité restante en centilitres (cl)
    private List<Employe> employes;
    private List<Cocktail> menu;
    private boolean estHappyHour; // Fonctionnalité supplémentaire

    public Bar(String nom) {
        this.nom = nom;
        this.stock = new HashMap<>();
        this.employes = new ArrayList<>();
        this.menu = new ArrayList<>();
        this.estHappyHour = false;
    }

    // ----------------------------------------------------------------
    // 1. GESTION DU STOCK et RECETTE (Fonctionnalité Minimale)
    // ----------------------------------------------------------------

    public void ajouterStock(Boisson boisson, int quantiteCl) {
        stock.put(boisson, stock.getOrDefault(boisson, 0) + quantiteCl);
    }

    // Vérification du stock avant préparation
    public boolean verifierStock(Cocktail cocktail) {
        for (Map.Entry<Boisson, Integer> ingredient : cocktail.getRecette().entrySet()) {
            if (stock.getOrDefault(ingredient.getKey(), 0) < ingredient.getValue()) {
                return false;
            }
        }
        return true;
    }

    // Préparation de cocktails (avec décrémentation du stock)
    public Commande preparerCommande(Commande commande) {
        if (commande.getCocktails().stream().allMatch(this::verifierStock)) {
            for (Cocktail cocktail : commande.getCocktails()) {
                for (Map.Entry<Boisson, Integer> ingredient : cocktail.getRecette().entrySet()) {
                    stock.put(ingredient.getKey(), stock.get(ingredient.getKey()) - ingredient.getValue());
                }
            }
            System.out.println("Commande #" + commande.getId() + " préparée.");
        } else {
            System.out.println("Erreur: Stock insuffisant pour la commande #" + commande.getId());
        }
        return commande;
    }

    // ----------------------------------------------------------------
    // 2. HAPPY HOUR (Fonctionnalité Supplémentaire)
    // ----------------------------------------------------------------

    public void setHappyHour(boolean estHappyHour) {
        this.estHappyHour = estHappyHour;
    }

    // Calcule le prix final en utilisant l'interface Facturable
    public double calculerTotalCommande(Commande commande) {
        if (this.estHappyHour) {
            return commande.appliquerReduction(0.15); // 15% de réduction
        } else {
            return commande.calculerPrixTotal();
        }
    }

    // --- Getters essentiels ---
    public void ajouterAuMenu(Cocktail c) { this.menu.add(c); }
    public Map<Boisson, Integer> getStock() { return stock; }
    public List<Cocktail> getMenu() { return menu; }
    public String getNom() { return nom; }
}