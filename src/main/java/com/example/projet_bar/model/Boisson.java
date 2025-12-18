package com.example.projet_bar.model;

import java.util.Objects;

public class Boisson {

    private String nom;
    private double degreAlcool;
    private double coutUnitaire;

    public Boisson(String nom, double degreAlcool, double coutUnitaire) {
        this.nom = nom;
        this.degreAlcool = degreAlcool;
        this.coutUnitaire = coutUnitaire;
    }

    public double getCoutUnitaire() {
        return coutUnitaire;
    }

    public boolean isEstAlcolisee() {
        return this.degreAlcool > 0.0;
    }

    public String getNom() { return nom; }
    public double getDegreAlcool() { return degreAlcool; }

    @Override public boolean equals(Object o) { /*...*/ return Objects.equals(nom, ((Boisson) o).nom); }
    @Override public int hashCode() { return Objects.hash(nom); }
}