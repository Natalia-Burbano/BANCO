package org.uniquindio.edu.co.poo.bancouq.controller;

import org.junit.jupiter.api.Test;
import org.uniquindio.edu.co.poo.bancouq.model.Administrador;
import org.uniquindio.edu.co.poo.bancouq.model.Cliente;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CajeroViewControllerTest {

    @Test
    public void testObtenerClientes() {
        // Crear administrador vacío (sin clientes)
        Administrador administrador = new Administrador("admin", "1234");

        // Crear controlador con ese administrador
        CajeroController controller = new CajeroController(administrador);

        // Obtener clientes usando el método público
        ArrayList<Cliente> clientes = controller.obtenerClientes();

        // Verificar que la lista no sea null y que esté vacía
        assertNotNull(clientes, "La lista de clientes no debe ser null");
        assertEquals(0, clientes.size(), "La lista de clientes debería estar vacía");

        // Verificar que el cliente ahora está en la lista
        clientes = controller.obtenerClientes();
        assertEquals(1, clientes.size(), "La lista de clientes debe tener 1 cliente");
        assertEquals("Juan Perez", clientes.get(0).getNombre(), "El nombre del cliente debe ser Juan Perez");
    }
}
