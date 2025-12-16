module com.example.projet_bar {
    requires javafx.controls;
    requires javafx.fxml;

    // Ouvre le package qui contient BarApplication et BarController pour le FXML loader
    opens com.example.projet_bar.ihm to javafx.fxml;

    // Exporte l'IHM
    exports com.example.projet_bar.ihm;

    // Ouvre les packages model et service pour que le FXML Controller puisse y accéder
    opens com.example.projet_bar.model to javafx.fxml;
    opens com.example.projet_bar.service to javafx.fxml;
}