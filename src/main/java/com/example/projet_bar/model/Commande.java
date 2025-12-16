package com.example.projet_bar.model;

import java.util.ArrayList;
import java.util.List;

// Implémente Facturable pour les calculs
public class Commande implements Facturable {

    private int id;
    private Client client;
    private List<Cocktail> cocktails;
    private boolean estPayee;

    private static int compteurId = 1000;

    // Constructeur : DOIT prendre un Client
    public Commande(Client client) {
        this.id = compteurId++;
        this.client = client;
        this.cocktails = new ArrayList<>();
        this.estPayee = false;
    }

    public void ajouterCocktail(Cocktail cocktail) {
        this.cocktails.add(cocktail);
    }

    // --- Implémentation des méthodes de l'interface Facturable ---

    @Override
    public double calculerPrixTotal() {
        double total = 0.0;
        for (Cocktail cocktail : cocktails) {
            total += cocktail.getPrix();
        }
        return total;
    }

    @Override
    public double appliquerReduction(double pourcentage) {
        double totalInitial = calculerPrixTotal();
        double reduction = totalInitial * pourcentage;
        return totalInitial - reduction;
    }

    // Getters essentiels
    public int getId() { return id; }
    public List<Cocktail> getCocktails() { return cocktails; }
    public Client getClient() { return client; }
}