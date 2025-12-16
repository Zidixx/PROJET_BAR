package com.example.projet_bar.model;

public class Employe {


    private int id;
    protected String nom;
    private int anciennete;
    private static int compteurId = 100;

    /**
     * CONSTRUCTEUR OBLIGATOIRE POUR QUE super() FONCTIONNE
     */
    public Employe(String nom, int anciennete) {
        this.id = compteurId++;
        this.nom = nom;
        this.anciennete = anciennete;
    }

    /**
     * MÉTHODE DE BASE 'travailler' (Pour que l'override dans Serveur fonctionne)
     */
    public void travailler() {
        System.out.println(this.nom + " travaille...");
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public int getAnciennete() {
        return anciennete;
    }
}