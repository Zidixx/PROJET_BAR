package com.example.projet_bar.model;

public class Employe {

    private int id;
    protected String nom;
    private int anciennete;
    private static int compteurId = 100;

    public Employe(String nom, int anciennete) {
        this.id = compteurId++;
        this.nom = nom; // <-- Il doit être là !
        this.anciennete = anciennete; // <-- Il doit être là !
    }

    public String getNom() {
        return nom;
    }

}