package com.example.projet_bar.Model;

public class Employe {

    private int id;
    protected String nom;
    private int anciennete;
    private static int compteurId = 100;

    public Employe(String nom, int anciennete) {
        this.id = compteurId++;
        this.nom = nom;
        this.anciennete = anciennete;
    }

    public void travailler() {
        System.out.println(this.nom + " travaille...");
    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public int getAnciennete() { return anciennete; }
}
