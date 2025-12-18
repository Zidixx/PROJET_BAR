package com.example.projet_bar.model;

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

public abstract class Cocktail {

    protected final String nom;
    protected double prixBase;

    protected final Map<Boisson, Integer> recette;

    public Cocktail(String nom, double prixBase) {
        this.nom = nom;
        this.prixBase = prixBase;
        this.recette = new HashMap<>();
    }


    public void ajouterIngredient(Boisson boisson, int quantiteMl) {
        this.recette.put(boisson, quantiteMl);
    }

    public double calculerCoutProduction() {
        double cout = 0.0;
        for (Map.Entry<Boisson, Integer> entry : recette.entrySet()) {
            Boisson boisson = entry.getKey();
            int quantiteMl = entry.getValue();

            cout += (boisson.getCoutUnitaire() * quantiteMl);
        }
        return cout;
    }

    public double getPrix() {
        return this.prixBase + this.calculerCoutProduction();
    }

    public abstract boolean isAlcoolise();

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
