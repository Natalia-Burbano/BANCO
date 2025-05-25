package org.uniquindio;

import org.uniquindio.edu.co.poo.bancouq.model.*;
import org.uniquindio.edu.co.poo.bancouq.controller.TransaccionController;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class TransaccionControllerTest {

    private Administrador administrador;
    private ServicioBancario servicioBancario;

    @BeforeEach
    public void setUp() {
        administrador = new Administrador("admin", "1234");
        servicioBancario = new ServicioBancario(administrador);

        // Agregar clientes de prueba
        Cliente cliente1 = new Cliente(1, "Juan Pérez", new CuentaAhorro(1, 500));
        Cliente cliente2 = new Cliente(2, "María López", new CuentaCorriente(2, 1000, 500));
        administrador.agregarCliente(cliente1);
        administrador.agregarCliente(cliente2);
    }

    @Test
    public void testRealizarDeposito() {
        TransaccionController transaccionController = new TransaccionController(administrador);
        boolean resultado = transaccionController.realizarDeposito(1, 200);
        assertTrue(resultado);
        // Verificar saldo actualizado originalmente: 500 + 200 = 700
        assertEquals(700, servicioBancario.consultarSaldo(1));
    }

    @Test
    public void testRealizarRetiro() {
        TransaccionController transaccionController = new TransaccionController(administrador);
        boolean resultado = transaccionController.realizarRetiro(2, 300);
        assertTrue(resultado);
        // Verificar saldo actualizado originalmente: 1000 - 300 = 700
        assertEquals(700, servicioBancario.consultarSaldo(2));  
    }
    @Test
    public void testRealizarTransferencia() {
        TransaccionController transaccionController = new TransaccionController(administrador);
        boolean resultado = transaccionController.realizarTransferencia(1, 2, 100); 
        
        assertTrue(resultado);

        // Verificar saldos actualizados
        assertEquals(400, servicioBancario.consultarSaldo(1)); // 500 - 100
        assertEquals(1100, servicioBancario.consultarSaldo(2)); // 1000 + 100
        
    }
}
