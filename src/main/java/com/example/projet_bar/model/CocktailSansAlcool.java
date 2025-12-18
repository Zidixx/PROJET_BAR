package com.example.projet_bar.model;


public class CocktailSansAlcool extends Cocktail {

    private final boolean estJusDeFruit;

    /**
     * Constructeur.
     * @param nom Le nom du cocktail.
     * @param prixBase Le prix de vente de base.
     * @param estJusDeFruit Indique si la base est un jus de fruit.
     */
    public CocktailSansAlcool(String nom, double prixBase, boolean estJusDeFruit) {
        super(nom, prixBase);
        this.estJusDeFruit = estJusDeFruit;
    }

    @Override
    public boolean isAlcoolise() {
        return false; // Par définition, c'est un cocktail sans alcool
    }

    public boolean estJusDeFruit() {
        return estJusDeFruit;
    }
}
