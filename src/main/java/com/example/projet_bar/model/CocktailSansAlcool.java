package com.example.projet_bar.model;

import java.util.Map;

/**
 * Représente un cocktail sans alcool. Hérite de Cocktail.
 */
public class CocktailSansAlcool extends Cocktail {

    // --- Attributs spécifiques ---
    private boolean estJusFrais;

    // --- Constructeur ---

    public CocktailSansAlcool(String nom, Map<Boisson, Integer> recette, double prixBase, boolean estJusFrais) {
        super(nom, recette, prixBase, false);
        this.estJusFrais = estJusFrais;
        appliquerSurcoutJusFrais();
    }

    // --- Méthodes Métier spécifiques ---

    /**
     * Applique un surcoût si le cocktail utilise des jus frais, modifiant le prix de base.
     */
    private void appliquerSurcoutJusFrais() {
        if (this.estJusFrais) {
            // Exemple de logique: 10% de surcoût pour le travail/qualité des jus frais
            double surcout = getPrixBase() * 0.10;
            setPrixBase(getPrixBase() + surcout);
        }
    }

    // --- Accesseurs (Getters) et Mutateurs (Setters) ---

    public boolean isEstJusFrais() {
        return estJusFrais;
    }

    public void setEstJusFrais(boolean estJusFrais) {
        this.estJusFrais = estJusFrais;
    }
}
