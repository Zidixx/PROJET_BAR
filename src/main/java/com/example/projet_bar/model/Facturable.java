package com.example.projet_bar.model;

/**
 * Interface pour tout objet pouvant être facturé ou soumis à une réduction.
 * Utilisé par la classe Commande.
 */
public interface Facturable {

    /**
     * Calcule le prix total de l'objet (avant réduction/taxe).
     * @return Le montant total.
     */
    double calculerPrixTotal();

    /**
     * Applique une réduction en pourcentage au prix total.
     * @param pourcentage Le pourcentage de réduction (ex: 0.15 pour 15%).
     * @return Le prix final après réduction.
     */
    double appliquerReduction(double pourcentage);
}