package com.example.projet_bar.model;

import java.util.ArrayList;
import java.util.List;

public class Commande implements Facturable {

    private int id;
    private Client client;
    private List<Cocktail> cocktails;
    private boolean estPayee;

    private static int compteurId = 1000;

    /**
     * Constructeur. Une commande est toujours liée à un Client.
     * @param client Le client ayant passé la commande.
     */
    public Commande(Client client) {
        this.id = compteurId++;
        this.client = client;
        this.cocktails = new ArrayList<>();
        this.estPayee = false;
    }

    /**
     * Ajoute un cocktail à la commande.
     * @param cocktail Le cocktail à ajouter.
     */
    public void ajouterCocktail(Cocktail cocktail) {
        this.cocktails.add(cocktail);
    }

    /**
     * Calcule le prix total de tous les cocktails dans la commande.
     * @return Le prix total non réduit.
     */
    @Override
    public double calculerPrixTotal() {
        double total = 0.0;
        for (Cocktail cocktail : cocktails) {
            total += cocktail.getPrix();
        }
        return total;
    }

    /**
     * Applique une réduction en pourcentage au prix total de la commande.
     * @param pourcentage Le pourcentage de réduction (ex: 0.15 pour 15%).
     * @return Le prix final après réduction.
     */
    @Override
    public double appliquerReduction(double pourcentage) {
        double totalInitial = calculerPrixTotal();
        double reduction = totalInitial * pourcentage;
        return Math.max(0, totalInitial - reduction);
    }

    public int getId() {
        return id;
    }

    public List<Cocktail> getCocktails() {
        return cocktails;
    }

    public Client getClient() {
        return client;
    }

    public void setEstPayee(boolean estPayee) {
        this.estPayee = estPayee;
    }
}
