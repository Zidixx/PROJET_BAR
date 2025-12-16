package com.example.projet_bar.model;

/**
 * Représente un cocktail sans alcool (mocktail).
 */
public class CocktailSansAlcool extends Cocktail {

    private final boolean estJusDeFruit;

    /**
     * Constructeur.
     * @param nom Le nom du cocktail.
     * @param prixBase Le prix de vente de base.
     * @param estJusDeFruit Indique si la base est un jus de fruit.
     */
    public CocktailSansAlcool(String nom, double prixBase, boolean estJusDeFruit) {
        // Appel au constructeur de la classe parente Cocktail
        super(nom, prixBase);
        this.estJusDeFruit = estJusDeFruit;
    }

    // Implémentation de la méthode abstraite
    @Override
    public boolean isAlcoolise() {
        return false; // Par définition, c'est un cocktail sans alcool
    }

    // Getter spécifique
    public boolean estJusDeFruit() {
        return estJusDeFruit;
    }
}
