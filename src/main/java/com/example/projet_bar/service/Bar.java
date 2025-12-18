package com.example.projet_bar.service;

import com.example.projet_bar.model.Cocktail;
import com.example.projet_bar.model.Employe;
import com.example.projet_bar.model.Boisson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bar {
    private String nom;
    private List<Cocktail> menu;
    private List<Employe> employes;
    private Map<Boisson, Integer> stock;
    private boolean happyHour;

    public Bar(String nom) {
        this.nom = nom;
        this.menu = new ArrayList<>();
        this.employes = new ArrayList<>();
        this.stock = new HashMap<>();
        this.happyHour = false;
    }

    public void consommerStock(Cocktail cocktail) {

        for (Map.Entry<Boisson, Integer> ingredient : cocktail.getRecette().entrySet()) {
            Boisson b = ingredient.getKey();
            int quantiteNecessaire = ingredient.getValue();

            int quantiteActuelle = this.stock.getOrDefault(b, 0);

            int nouveauStock = Math.max(0, quantiteActuelle - quantiteNecessaire);

            this.stock.put(b, nouveauStock);

            System.out.println("[STOCK] " + b.getNom() + " : " + quantiteActuelle + "ml -> " + nouveauStock + "ml");
        }
    }
    public Map<Boisson, Integer> getStock() {
        return this.stock;
    }

    public void ajouterEmploye(Employe e) {
        this.employes.add(e);
    }

    public List<Employe> getEmployes() {
        return employes;
    }

    public boolean isHappyHour() {
        return happyHour;
    }

    public void setHappyHour(boolean happyHour) {
        this.happyHour = happyHour;
    }

    public void ajouterAuMenu(Cocktail c) {
        this.menu.add(c);
    }

    public List<Cocktail> getMenu() {
        return menu;
    }

    public void ajouterStock(Boisson b, int quantite) {
        this.stock.put(b, this.stock.getOrDefault(b, 0) + quantite);
    }

    public Map<Boisson, Integer> getStockComplet() {
        return stock;
    }

    public String getNom() {
        return nom;
    }
}
