package com.example.projet_bar.model;


public class Barman extends Employe {

    private int competenceMixologie;

    /**
     * Constructeur pour un Barman.
     * @param nom Le nom de l'employé
     * @param anciennete L'ancienneté (années)
     * @param competenceMixologie Niveau de compétence en mixologie (1 à 10)
     */
    public Barman(String nom, int anciennete, int competenceMixologie) {
        super(nom, anciennete);
        this.competenceMixologie = competenceMixologie;
    }

    @Override
    public void travailler() {
        System.out.println(this.getNom() + " prépare les boissons avec une compétence de " + this.competenceMixologie + ".");
    }

    public int getCompetenceMixologie() {
        return competenceMixologie;
    }
}
