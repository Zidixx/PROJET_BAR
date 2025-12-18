package com.example.projet_bar.model;

public abstract class Employe {

    private int id;
    protected String nom;
    protected String prenom;
    private int anciennete;
    private static int compteurId = 100;

    public Employe(String nom, String prenom, int anciennete) {
        this.id = compteurId++;
        this.nom = nom;
        this.prenom = prenom;
        this.anciennete = anciennete;
    }

    public abstract void travailler();

    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }

    public String getNomComplet() {
        return prenom + " " + nom.toUpperCase();
    }

    public int getAnciennete() { return anciennete; }
}
