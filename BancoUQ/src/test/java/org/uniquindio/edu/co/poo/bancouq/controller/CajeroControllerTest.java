package org.uniquindio.edu.co.poo.bancouq.controller;

import org.uniquindio.edu.co.poo.bancouq.model.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class CajeroControllerTest {

    private CajeroController cajeroController;
    private ObservableList<Cliente> clientes;

    @BeforeEach
    public void setUp() throws Exception {
        // Inicializar controlador
        cajeroController = new CajeroController(null);

        // Configurar lista de clientes de prueba
        clientes = FXCollections.observableArrayList();
        Cliente cliente1 = new Cliente(1, "Juan Pérez", new CuentaAhorro(1, 500));
        Cliente cliente2 = new Cliente(2, "María López", new CuentaCorriente(2, 1000, 500));
        clientes.add(cliente1);
        clientes.add(cliente2);

        // Inyectar la lista de clientes directamente
        Field clientesField = CajeroController.class.getDeclaredField("clientes");
        clientesField.setAccessible(true);
        clientesField.set(cajeroController, clientes);
    }

    @Test
    public void testTamanoInicialListaClientes() {
        // Verificar que la lista de clientes tiene el tamaño correcto
        assertEquals(2, clientes.size());
    }

    @Test
    public void testPropiedadesCliente() {
        // Verificar propiedades del primer cliente
        Cliente cliente = clientes.get(0);
        assertEquals(1, cliente.getId());
        assertEquals("Juan Pérez", cliente.getNombre());
        assertTrue(cliente.getCuenta() instanceof CuentaAhorro);
        assertEquals(500, cliente.getCuenta().getSaldo());
    }

    @Test
    public void testOperacionesCuenta() {
        // Obtener la cuenta del primer cliente
        Cliente cliente = clientes.get(0);
        Cuenta cuenta = cliente.getCuenta();

        // Verificar saldo inicial
        assertEquals(500, cuenta.getSaldo());

        // Realizar un depósito
        cuenta.depositar(200);
        assertEquals(700, cuenta.getSaldo());

        // Realizar un retiro
        cuenta.retirar(300);
        assertEquals(400, cuenta.getSaldo());
    }

    @Test
    public void testBuscarClientePorId() {
        // Buscar cliente existente
        Cliente encontrado = buscarClientePorId(1);
        assertNotNull(encontrado);
        assertEquals("Juan Pérez", encontrado.getNombre());

        // Buscar cliente inexistente
        Cliente noEncontrado = buscarClientePorId(99);
        assertNull(noEncontrado);
    }

    @Test
    public void testCrearNuevoCliente() {
        // Crear un nuevo cliente manualmente (sin usar el controlador)
        int idNuevo = 3;
        String nombreNuevo = "Carlos Gómez";
        Cuenta cuentaNueva = new CuentaAhorro(idNuevo, 0);
        Cliente nuevoCliente = new Cliente(idNuevo, nombreNuevo, cuentaNueva);

        // Agregar el cliente a la lista
        clientes.add(nuevoCliente);

        // Verificar que el cliente fue agregado correctamente
        assertEquals(3, clientes.size());
        Cliente clienteAgregado = clientes.get(2);
        assertEquals(idNuevo, clienteAgregado.getId());
        assertEquals(nombreNuevo, clienteAgregado.getNombre());
        assertTrue(clienteAgregado.getCuenta() instanceof CuentaAhorro);
    }

    @Test
    public void testTiposDeCuenta() {
        // Verificar comportamiento de CuentaAhorro
        Cuenta cuentaAhorro = new CuentaAhorro(101, 1000);
        cuentaAhorro.depositar(100);
        assertEquals(1100, cuentaAhorro.getSaldo());

        // Verificar comportamiento básico de CuentaCorriente
        CuentaCorriente cuentaCorriente = new CuentaCorriente(102, 500, 200);
        assertEquals(500, cuentaCorriente.getSaldo());

        // Probar operaciones básicas
        cuentaCorriente.depositar(300);
        assertEquals(800, cuentaCorriente.getSaldo());

        cuentaCorriente.retirar(200);
        assertEquals(600, cuentaCorriente.getSaldo());
    }

    private Cliente buscarClientePorId(int id) {
        return clientes.stream()
                .filter(cliente -> cliente.getId() == id)
                .findFirst()
                .orElse(null);
    }
}