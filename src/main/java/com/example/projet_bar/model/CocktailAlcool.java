package com.example.projet_bar.model;

/**
 * Représente un cocktail contenant de l'alcool.
 */
public class CocktailAlcool extends Cocktail {

    private final double degreAlcoolTotal;

    /**
     * Constructeur.
     * @param nom Le nom du cocktail.
     * @param prixBase Le prix de vente de base.
     * @param degreAlcoolTotal Le degré d'alcool du mélange final.
     */
    public CocktailAlcool(String nom, double prixBase, double degreAlcoolTotal) {
        // Appel au constructeur de la classe parente Cocktail
        super(nom, prixBase);
        this.degreAlcoolTotal = degreAlcoolTotal;
    }

    // Implémentation de la méthode abstraite
    @Override
    public boolean isAlcoolise() {
        return true; // Par définition, c'est un cocktail alcoolisé
    }

    // Getter spécifique
    public double getDegreAlcoolTotal() {
        return degreAlcoolTotal;
    }
}
