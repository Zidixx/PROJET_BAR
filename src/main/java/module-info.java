module com.example.projet_bar {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.projet_bar to javafx.fxml;
    exports com.example.projet_bar;
}