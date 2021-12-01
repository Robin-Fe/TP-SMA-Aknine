module com.example.tp_sma_aknine {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.tp_sma_aknine to javafx.fxml;
    exports com.example.tp_sma_aknine;
}