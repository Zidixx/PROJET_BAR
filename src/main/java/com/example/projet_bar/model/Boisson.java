package com.example.projet_bar.Model;

import java.util.Objects;

public class Boisson {

    private String nom;
    private double degreAlcool;
    private double coutUnitaire;

    /**
     * Constructeur de la classe Boisson (l'ingrédient).
     * @param nom Le nom de la boisson (ex: "Rhum Blanc").
     * @param degreAlcool Le degré d'alcool de la boisson.
     * @param coutUnitaire Le coût d'achat par centilitre (cL).
     */
    public Boisson(String nom, double degreAlcool, double coutUnitaire) {
        this.nom = nom;
        this.degreAlcool = degreAlcool;
        this.coutUnitaire = coutUnitaire;
    }

    public String getNom() {
        return nom;
    }

    public double getDegreAlcool() {
        return degreAlcool;
    }

    public double getCoutUnitaire() {
        return coutUnitaire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Boisson boisson = (Boisson) o;
        return Objects.equals(nom, boisson.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom);
    }
}