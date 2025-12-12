package com.example.projet_bar.Model;

public class Client {

    private int id;
    private String nom;
    private boolean estFidele;

    private static int compteurId = 1;

    /**
     * Constructeur de la classe Client.
     * @param nom Le nom du client.
     */
    public Client(String nom) {
        this.id = compteurId++;
        this.nom = nom;
        this.estFidele = false; // Par défaut, non fidèle
    }

    /**
     * Permet au client de créer une nouvelle commande.
     * @return L'objet Commande créé pour ce client.
     */
    public Commande passerCommande() {
        Commande nouvelleCommande = new Commande(this);
        System.out.println(this.nom + " a passé la commande #" + nouvelleCommande.getId());
        return nouvelleCommande;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public boolean estFidele() {
        return estFidele;
    }
}
