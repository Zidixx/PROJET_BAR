package com.example.projet_bar.model;

import java.util.Objects;

// Assurez-vous que ce code écrase le contenu de Boisson.java
public class Boisson {

    private String nom;
    private double degreAlcool;
    private double coutUnitaire;

    public Boisson(String nom, double degreAlcool, double coutUnitaire) {
        this.nom = nom;
        this.degreAlcool = degreAlcool;
        this.coutUnitaire = coutUnitaire;
    }

    // Fournit la méthode que Cocktail.java attendait (getCoutUnitaire)
    public double getCoutUnitaire() {
        return coutUnitaire;
    }

    // Fournit la méthode que CocktailAlcool/CocktailSansAlcool attendait
    public boolean isEstAlcolisee() {
        return this.degreAlcool > 0.0;
    }

    // Autres Getters/Equals/Hashcode...
    public String getNom() { return nom; }
    public double getDegreAlcool() { return degreAlcool; }

    @Override public boolean equals(Object o) { /*...*/ return Objects.equals(nom, ((Boisson) o).nom); }
    @Override public int hashCode() { return Objects.hash(nom); }
}