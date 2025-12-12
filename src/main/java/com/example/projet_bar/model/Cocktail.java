package com.example.projet_bar.model;

import java.util.Collections;
import java.util.Map;

/**
 * Représente un cocktail de base. Gère la recette et le prix de base.
 * C'est la classe parente.
 */
public class Cocktail {

    // --- Attributs ---
    private final String nom;
    private double prixBase;
    private final boolean estAlcoolise;

    // Map<Boisson, Quantité en ml>
    private final Map<Boisson, Integer> recette;

    // --- Constructeur ---

    /**
     * Constructeur d'un Cocktail.
     * @param nom Le nom du cocktail.
     * @param recette La Map contenant les ingrédients (Boisson) et les quantités (Integer).
     * @param prixBase Le prix de vente de base du cocktail.
     * @param estAlcoolise Indique si le cocktail contient de l'alcool.
     */
    public Cocktail(String nom, Map<Boisson, Integer> recette, double prixBase, boolean estAlcoolise) {
        this.nom = nom;
        this.recette = recette;
        this.prixBase = prixBase;
        this.estAlcoolise = estAlcoolise;
    }

    // --- Méthodes Métier ---

    /**
     * Calcule le coût total de production du cocktail en fonction des ingrédients.
     * Nécessite que la classe Boisson ait la méthode getPrixUnitaire().
     * @return Le coût total en double.
     */
    public double calculerCoutProduction() {
        double cout = 0.0;
        for (Map.Entry<Boisson, Integer> entry : recette.entrySet()) {
            Boisson boisson = entry.getKey();
            int quantiteMl = entry.getValue();

            // Le coût est la quantité utilisée * le prix de l'unité de stock
            cout += (boisson.getPrixUnitaire() * quantiteMl);
        }
        return cout;
    }

    // --- Accesseurs (Getters) et Mutateurs (Setters) ---

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

    public boolean isEstAlcoolise() {
        return estAlcoolise;
    }

    /**
     * Retourne la recette. Utilisé Collections.unmodifiableMap pour l'encapsulation.
     * @return Une Map non modifiable de la recette.
     */
    public Map<Boisson, Integer> getRecette() {
        return Collections.unmodifiableMap(recette);
    }
}