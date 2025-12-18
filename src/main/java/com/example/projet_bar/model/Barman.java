package com.example.projet_bar.model;

public class Barman extends Employe {

    private int competenceMixologie;

    public Barman(String nom, String prenom, int anciennete, int competenceMixologie) {
        super(nom, prenom, anciennete);
        this.competenceMixologie = competenceMixologie;
    }

    @Override
    public void travailler() {
        System.out.println("Le Barman " + getNomComplet() + " prépare les cocktails (Compétence: " + competenceMixologie + "/10).");
    }

    public int getCompetenceMixologie() {
        return competenceMixologie;
    }
}
