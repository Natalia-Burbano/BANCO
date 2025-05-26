module org.uniquindio.edu.co.poo.bancouq {
    requires javafx.controls;
    requires javafx.fxml;

    exports org.uniquindio.edu.co.poo.bancouq; // << NECESARIO para MainApp
    exports org.uniquindio.edu.co.poo.bancouq.viewController;

    opens org.uniquindio.edu.co.poo.bancouq.viewController to javafx.fxml;
}
