package com.example.projet_bar.model;


public class Serveur extends Employe {

    private int vitesseService;

    /**
     * Constructeur pour un Serveur.
     * @param nom Le nom de l'employé
     * @param anciennete L'ancienneté (années)
     * @param vitesseService Niveau de compétence en vitesse (1 à 10)
     */
    public Serveur(String nom, int anciennete, int vitesseService) {
        super(nom, anciennete);
        this.vitesseService = vitesseService;
    }

    @Override
    public void travailler() {
        System.out.println(this.getNom() + " sert les commandes avec une vitesse de " + this.vitesseService + ".");
    }

    public void prendreCommande(Client client, Commande commande) {
        System.out.println(this.getNom() + " prend la commande #" + commande.getId() + " du client " + client.getNom());
    }

    public int getVitesseService() {
        return vitesseService;
    }
}