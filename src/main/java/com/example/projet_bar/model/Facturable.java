package com.example.projet_bar.model;

public interface Facturable {

    double calculerPrixTotal();

    /**
     * Applique une réduction en pourcentage au prix total.
     * @param pourcentage Le pourcentage de réduction (ex: 0.15 pour 15%).
     * @return Le prix final après réduction.
     */
    double appliquerReduction(double pourcentage);
}