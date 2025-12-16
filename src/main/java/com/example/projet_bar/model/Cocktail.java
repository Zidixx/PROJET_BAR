package com.example.projet_bar.model;

import java.util.Collections;
import java.util.Map;
import java.util.HashMap; // Ajouté pour initialiser la recette

/**
 * Représente un cocktail de base. Gère la recette et le prix de base.
 * Rendu ABSTRAIT pour respecter l'héritage.
 */
public abstract class Cocktail { // <-- La classe doit être abstraite

    // --- Attributs ---
    protected final String nom; // Changé en protected pour l'héritage
    protected double prixBase; // Changé en protected pour l'héritage

    // Map<Boisson, Quantité en ml>
    protected final Map<Boisson, Integer> recette; // Changé en protected pour l'héritage

    // --- Constructeur ---
    // Simplifié pour être appelé par super(nom, prixBase) dans les classes enfants
    public Cocktail(String nom, double prixBase) {
        this.nom = nom;
        this.prixBase = prixBase;
        this.recette = new HashMap<>();
    }

    // Ajout d'ingrédients après l'instanciation (utilisé par le Bar)
    public void ajouterIngredient(Boisson boisson, int quantiteMl) {
        this.recette.put(boisson, quantiteMl);
    }

    // --- Méthodes Métier ---

    /**
     * Calcule le coût total de production du cocktail en fonction des ingrédients.
     * @return Le coût total en double.
     */
    public double calculerCoutProduction() {
        double cout = 0.0;
        for (Map.Entry<Boisson, Integer> entry : recette.entrySet()) {
            Boisson boisson = entry.getKey();
            int quantiteMl = entry.getValue();

            // CORRECTION: Utiliser getCoutUnitaire() (méthode existante dans Boisson)
            cout += (boisson.getCoutUnitaire() * quantiteMl);
        }
        return cout;
    }

    /**
     * CALCULE LE PRIX DE VENTE FINAL
     * Ceci est la méthode que Commande.calculerPrixTotal() attend.
     * @return Le prix de vente final.
     */
    public double getPrix() {
        // Prix de vente = Prix de base + Coût de production
        // Vous pouvez ajuster la marge ici si nécessaire.
        return this.prixBase + this.calculerCoutProduction();
    }

    /**
     * Définit si le cocktail est alcoolisé. Doit être implémenté par les enfants.
     */
    public abstract boolean isAlcoolise();


    // --- Accesseurs (Getters) ---
    public String getNom() {
        return nom;
    }

    public double getPrixBase() {
        return prixBase;
    }

    public void setPrixBase(double prixBase) {
        if (prixBase > 0) {
            this.prixBase = prixBase;
        }
    }

    public Map<Boisson, Integer> getRecette() {
        return Collections.unmodifiableMap(recette);
    }
}
