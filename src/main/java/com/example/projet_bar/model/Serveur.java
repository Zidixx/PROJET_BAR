package com.example.projet_bar.model;

public class Serveur extends Employe {

    private int vitesseService;

    public Serveur(String nom, String prenom, int anciennete, int vitesseService) {
        super(nom, prenom, anciennete);
        this.vitesseService = vitesseService;
    }

    @Override
    public void travailler() {
        System.out.println("Le Serveur " + getNomComplet() + " prend les commandes (Vitesse: " + vitesseService + "/10).");
    }

    public int getVitesseService() {
        return vitesseService;
    }
}
