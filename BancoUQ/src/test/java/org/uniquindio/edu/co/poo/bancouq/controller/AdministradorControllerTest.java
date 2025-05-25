package org.uniquindio.edu.co.poo.bancouq.controller;

import org.uniquindio.edu.co.poo.bancouq.model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AdministradorControllerTest {

    private Administrador administrador;
    private AdministradorController controller;

    @BeforeEach
    public void setUp() {
        administrador = new Administrador("admin", "1234");
        controller = new AdministradorController(administrador);

        // Agregar empleados de prueba
        Empleado empleado1 = new Empleado(1, "Pedro", "Cajero");
        Empleado empleado2 = new Empleado(2, "Ana", "Asesor");
        administrador.agregarEmpleado(empleado1);
        administrador.agregarEmpleado(empleado2);

        // Agregar clientes de prueba
        Cliente cliente1 = new Cliente(1, "Juan Pérez", new CuentaAhorro(1, 500));
        Cliente cliente2 = new Cliente(2, "María López", new CuentaCorriente(2, 1000, 500));
        administrador.agregarCliente(cliente1);
        administrador.agregarCliente(cliente2);
    }

    @Test
    public void testTamanoInicialListaEmpleados() {
        assertEquals(2, controller.obtenerEmpleados().size());
    }

    @Test
    public void testPropiedadesEmpleado() {
        Empleado empleado = controller.buscarEmpleado(1);
        assertNotNull(empleado);

        assertEquals("Pedro", empleado.getNombre());
        assertEquals("Cajero", empleado.getPuesto());
    }

    @Test
    public void testCrearNuevoEmpleado() {
        Empleado nuevo = new Empleado(3, "Carlos", "Gerente");
        boolean creado = controller.crearEmpleado(nuevo);
        assertTrue(creado);
        assertEquals(3, controller.obtenerEmpleados().size());
        Empleado encontrado = controller.buscarEmpleado(3);
        assertNotNull(encontrado);
        assertEquals("Carlos", encontrado.getNombre());
    }

    @Test
    public void testEliminarEmpleado() {
        boolean eliminado = controller.eliminarEmpleado(1);
        assertTrue(eliminado);
        assertNull(controller.buscarEmpleado(1));
        assertEquals(1, controller.obtenerEmpleados().size());
    }

    @Test
    public void testModificarEmpleado() {
        boolean modificado = controller.modificarEmpleado(2, "Ana María", "Jefe");
        assertTrue(modificado);
        Empleado empleado = controller.buscarEmpleado(2);
        assertEquals("Ana María", empleado.getNombre());
        assertEquals("Jefe", empleado.getPuesto());
    }

    @Test
    public void testAutenticacionAdministrador() {
        assertTrue(controller.autenticarAdministrador("admin", "1234"));
        assertFalse(controller.autenticarAdministrador("admin", "incorrecta"));
    }

    @Test
    public void testAgregarYBuscarCliente() {
        Cliente nuevoCliente = new Cliente(3, "Luis", new CuentaAhorro(3, 200));
        assertTrue(controller.agregarCliente(nuevoCliente));
        Cliente encontrado = controller.buscarCliente(3);
        assertNotNull(encontrado);
        assertEquals("Luis", encontrado.getNombre());
    }

    @Test
    public void testObtenerClientes() {
        ArrayList<Cliente> clientes = controller.obtenerClientes();
        assertEquals(2, clientes.size());
    }

    @Test
    public void testRegistrarYObtenerTransacciones() {
        Transaccion t1 = new Transaccion("Depósito", 100.0); 
        assertTrue(controller.registrarTransaccion(t1));
        ArrayList<Transaccion> transacciones = controller.obtenerTransacciones();
        assertEquals(1, transacciones.size());
        assertEquals("Depósito", transacciones.get(0).getTipo());
    }

    @Test
    public void testGenerarReporteCompleto() {
        String reporte = controller.generarReporteCompleto();
        assertTrue(reporte.contains("Total de empleados: 2"));
        assertTrue(reporte.contains("Total de clientes: 2"));
    }
}