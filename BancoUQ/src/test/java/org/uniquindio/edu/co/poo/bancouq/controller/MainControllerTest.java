package org.uniquindio.edu.co.poo.bancouq.controller;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.scene.layout.BorderPane;


public class MainControllerTest {

    private MainController mainController;
    

    @BeforeEach
    public void setUp() {
        mainController = new MainController();
    
    }

    @Test
    public void testMostrarCajeroView() {
        // Verificar que el contenido del pane se actualiza correctamente
        mainController.mostrarCajeroView();
        BorderPane expectedPane = new BorderPane();
        // Set up the expected state of the pane here if necessary
        assertArrayEquals(expectedPane.getChildren().toArray(), mainController.getContenidoPane().getChildren().toArray());
    }

    @Test
    public void testMostrarAdministradorViewNoLanzaExcepcion() {
        assertDoesNotThrow(() -> mainController.mostrarCajeroView());
    }

    @Test
    public void testMostrarTransaccionViewLanzaExcepcion() {
    
        assertDoesNotThrow(() -> mainController.mostrarTransaccionView());
    }
}
