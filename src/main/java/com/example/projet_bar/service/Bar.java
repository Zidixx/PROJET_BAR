package com.example.projet_bar.service;

import com.example.projet_bar.model.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class Bar {

    private String nom;
    // Le stock: Boisson (Ingrédient) -> Quantité restante en millilitres (mL)
    private Map<Boisson, Integer> stock;
    private List<Employe> employes;
    private List<Cocktail> menu;
    private boolean estHappyHour;

    /**
     * Constructeur du Bar. Initialise les listes de gestion.
     * @param nom Le nom du Bar.
     */
    public Bar(String nom) {
        this.nom = nom;
        this.stock = new HashMap<>();
        this.employes = new ArrayList<>();
        this.menu = new ArrayList<>();
        this.estHappyHour = false;
    }

    // ----------------------------------------------------------------
    // 1. GESTION DU PERSONNEL ET DU MENU
    // ----------------------------------------------------------------

    public void ajouterEmploye(Employe employe) {
        this.employes.add(employe);
    }

    public void ajouterAuMenu(Cocktail cocktail) {
        this.menu.add(cocktail);
    }

    // ----------------------------------------------------------------
    // 2. GESTION DU STOCK et RECETTE (Fonctionnalité Minimale)
    // ----------------------------------------------------------------

    /**
     * Ajoute une quantité d'ingrédient au stock.
     */
    public void ajouterStock(Boisson boisson, int quantiteMl) {
        stock.put(boisson, stock.getOrDefault(boisson, 0) + quantiteMl);
        System.out.println("Stock mis à jour: " + boisson.getNom() + " -> " + stock.get(boisson) + " mL");
    }

    /**
     * Vérifie si tous les ingrédients d'un cocktail sont disponibles en stock.
     */
    public boolean verifierStock(Cocktail cocktail) {
        boolean disponible = true;
        System.out.println("Vérification de la recette du " + cocktail.getNom() + ":");

        for (Map.Entry<Boisson, Integer> ingredient : cocktail.getRecette().entrySet()) {
            Boisson boisson = ingredient.getKey();
            int quantiteRequise = ingredient.getValue();
            int quantiteEnStock = stock.getOrDefault(boisson, 0);

            if (quantiteEnStock < quantiteRequise) {
                System.out.println("  ❌ Manque " + boisson.getNom() + ": Requis " + quantiteRequise + " mL, Stock: " + quantiteEnStock + " mL");
                disponible = false;
            } else {
                System.out.println("  ✅ OK: " + boisson.getNom() + " (Stock: " + quantiteEnStock + " mL)");
            }
        }
        return disponible;
    }

    /**
     * Prépare une commande, met à jour le stock et calcule le prix.
     */
    public Commande preparerCommande(Commande commande) {
        // Logique de vérification du stock pour tous les cocktails de la commande
        boolean stockSuffisant = true;
        for (Cocktail cocktail : commande.getCocktails()) {
            if (!verifierStock(cocktail)) {
                stockSuffisant = false;
                break;
            }
        }

        if (stockSuffisant) {
            // Décrémentation du stock
            for (Cocktail cocktail : commande.getCocktails()) {
                for (Map.Entry<Boisson, Integer> ingredient : cocktail.getRecette().entrySet()) {
                    Boisson boisson = ingredient.getKey();
                    int quantiteUtilisee = ingredient.getValue();
                    stock.put(boisson, stock.get(boisson) - quantiteUtilisee);
                }
            }
            System.out.println("--- Commande #" + commande.getId() + " préparée. Stock décrémenté. ---");
        } else {
            System.out.println("--- ERREUR : La commande #" + commande.getId() + " ne peut pas être préparée (Stock insuffisant). ---");
        }
        return commande;
    }

    // ----------------------------------------------------------------
    // 3. HAPPY HOUR (Fonctionnalité Supplémentaire)
    // ----------------------------------------------------------------

    public void setHappyHour(boolean estHappyHour) {
        this.estHappyHour = estHappyHour;
        System.out.println(estHappyHour ? "🎉 HAPPY HOUR activé !" : "😢 HAPPY HOUR désactivé.");
    }

    /**
     * Calcule le prix final de la commande en appliquant la réduction Happy Hour.
     */
    public double calculerTotalCommande(Commande commande) {
        if (this.estHappyHour) {
            return commande.appliquerReduction(0.15); // 15% de réduction
        } else {
            return commande.calculerPrixTotal();
        }
    }

    // --- Getters essentiels ---
    public Map<Boisson, Integer> getStock() { return stock; }
    public List<Cocktail> getMenu() { return menu; }
    public List<Employe> getEmployes() { return employes; }
    public String getNom() { return nom; }
}
