package com.example.projet_bar.model;

import java.util.Map;

/**
 * Représente un cocktail avec alcool. Hérite de Cocktail.
 */
public class CocktailAlcool extends Cocktail {

    // --- Attributs spécifiques ---
    private final double degreAlcool;

    // --- Constructeur ---

    /**
     * Constructeur d'un CocktailAlcool.
     */
    public CocktailAlcool(String nom, Map<Boisson, Integer> recette, double prixBase) {
        super(nom, recette, prixBase, true);
        this.degreAlcool = calculerDegreAlcool();
    }

    // --- Méthodes Métier spécifiques ---

    /**
     * Calcule le degré d'alcool final du cocktail.
     * @return Le degré d'alcool du cocktail (e.g., 0.15 pour 15%).
     */
    private double calculerDegreAlcool() {
        double volumeTotal = 0.0;
        double volumeAlcoolPur = 0.0;

        for (Map.Entry<Boisson, Integer> entry : getRecette().entrySet()) {
            Boisson boisson = entry.getKey();
            int quantiteMl = entry.getValue();

            volumeTotal += quantiteMl;

            if (boisson.isEstAlcoolisee()) {
                // Volume Alcool Pur = Quantité * Degré (si 40%, le degré est 0.4)
                volumeAlcoolPur += (quantiteMl * boisson.getDegreAlcool());
            }
        }

        if (volumeTotal > 0) {
            // Degré d'alcool final = (Volume Alcool Pur total / Volume Total du Cocktail)
            return (volumeAlcoolPur / volumeTotal);
        }
        return 0.0;
    }

    // --- Accesseurs (Getters) ---

    public double getDegreAlcool() {
        return degreAlcool;
    }
}
