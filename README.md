C'est noté, j'ai corrigé le point de lancement et les noms des développeurs. Voici la version finale de ton **README.md** prête à être copiée.

---

# 🍹 Projet JavaFX : Café Maritime Club

## 📝 Présentation

Ce projet est une application de gestion de bar complète développée en **Java** avec **JavaFX**. Elle permet de gérer la prise de commande, le suivi des stocks, la facturation et la coordination du personnel.

L'architecture repose sur une séparation stricte entre la **logique métier** (Model/Service) et l'**interface graphique** (Controller/FXML) pour garantir un code maintenable et évolutif.

---

## 🚀 Fonctionnalités principales

### 1. Gestion des Cocktails & Ingrédients

* **Menu Dynamique** : Affichage automatique de la carte avec distinction visuelle entre boissons avec et sans alcool.
* **Recettes & Production** : Chaque cocktail possède une recette précise déduisant automatiquement les ingrédients du stock lors de la vente.
* **Marge Commerciale** : Le système calcule automatiquement le prix final en appliquant une marge de **25%** sur le coût de revient total.

### 2. Gestion des Stocks

* **Suivi en temps réel** : Visualisation de l'état des stocks (en ml ou doses) via un panneau dédié.
* **Inventaire sécurisé** : Mise à jour immédiate du stock après chaque préparation pour éviter les ruptures.

### 3. Facturation & Vente

* **Happy Hour** : Activation d'une remise globale de **25%** sur la commande via un bouton toggle.
* **Système Facturable** : Utilisation d'une interface de facturation pour gérer les prix totaux et les réductions.
* **Ticket de Caisse** : Génération d'un récapitulatif textuel incluant le nom du serveur, le client et le détail des montants.

---

## 🛠️ Installation & Lancement

1. Cloner le dépôt du projet.
2. Importer le projet dans votre IDE (IntelliJ IDEA recommandé).
3. Lancer l'application via la classe : **`BarApplication`**.

---

## 👥 Équipe de Développement

* **Nathan Loubet**
* **Enzo Faure**

---

Souhaites-tu que j'ajoute une capture d'écran de l'interface ou un guide pour ajouter de nouveaux cocktails dans la base de données ?
