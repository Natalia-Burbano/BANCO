package org.uniquindio.edu.co.poo.bancouq.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainController {

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

    public void cargarVista(String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Node vista = loader.load();
            contenidoPane.setCenter(vista); // Aquí se usa contenidoPane
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    
}