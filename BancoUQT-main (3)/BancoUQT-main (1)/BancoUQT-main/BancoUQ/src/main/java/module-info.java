module com.example.bancouq {
    requires javafx.controls;
    requires javafx.fxml;

    // Exporta el paquete de tu MainApp para que JavaFX pueda acceder a él
    exports org.uniquindio.edu.co.poo.bancouq to javafx.graphics;

    // Mantén las exportaciones existentes para el controlador
    opens org.uniquindio.edu.co.poo.bancouq.viewController to javafx.fxml;
    opens org.uniquindio.edu.co.poo.bancouq.model to javafx.base;

    exports org.uniquindio.edu.co.poo.bancouq.viewController;
    exports org.uniquindio.edu.co.poo.bancouq.model;
}