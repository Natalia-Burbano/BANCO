package org.uniquindio.edu.co.poo.bancouq.viewController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import java.io.IOException;


public class MainViewController {
    @FXML
    private BorderPane contenidoPane;

    public void mostrarCajeroView() {
        cargarVista("/org/uniquindio/edu/co/poo/bancouq/cajero.fxml");
    }

    public void mostrarTransaccionView() {
        cargarVista("/org/uniquindio/edu/co/poo/bancouq/transaccion-view.fxml");
    }

    public void mostrarAdministradorView() {
        cargarVista("/org/uniquindio/edu/co/poo/bancouq/administrador.fxml");
    }

    private void cargarVista(String rutaFXML) {
        try {
            contenidoPane.setCenter(new FXMLLoader(getClass().getResource(rutaFXML)).load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}