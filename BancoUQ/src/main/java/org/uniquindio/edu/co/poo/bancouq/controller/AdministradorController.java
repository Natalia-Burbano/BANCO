// AdministradorController.java - Lógica de control para la vista de administrador
package org.uniquindio.edu.co.poo.bancouq.controller;

import org.uniquindio.edu.co.poo.bancouq.model.*;
import java.util.ArrayList;

public class AdministradorController {

    private Administrador administrador;

    public AdministradorController(Administrador administrador) {
        this.administrador = administrador;
    }

    // Métodos de autenticación
    public boolean autenticarAdministrador(String username, String password) {
        return administrador.autenticar(username, password);
    }

    // Métodos de gestión de empleados
    public boolean crearEmpleado(Empleado empleado) {
        administrador.agregarEmpleado(empleado);
        return true;
    }

    public boolean eliminarEmpleado(int id) {
        return administrador.eliminarEmpleado(id);
    }

    public Empleado buscarEmpleado(int id) {
        return administrador.buscarEmpleado(id);
    }

    public ArrayList<Empleado> obtenerEmpleados() {
        return administrador.getEmpleados();
    }

    public boolean modificarEmpleado(int id, String nuevoNombre, String nuevoPuesto) {
        Empleado empleado = administrador.buscarEmpleado(id);
        if (empleado != null) {
            if (nuevoNombre != null && !nuevoNombre.isEmpty()) {
                empleado.setNombre(nuevoNombre);
            }
            if (nuevoPuesto != null && !nuevoPuesto.isEmpty()) {
                empleado.setPuesto(nuevoPuesto);
            }
            return true;
        }
        return false;
    }

    // Métodos de gestión de transacciones
    public boolean registrarTransaccion(Transaccion transaccion) {
        administrador.registrarTransaccion(transaccion);
        return true;
    }

    public ArrayList<Transaccion> obtenerTransacciones() {
        return administrador.getTransacciones();
    }

    public ArrayList<Transaccion> obtenerTransaccionesSospechosas() {
        return administrador.getTransaccionesSospechosas();
    }

    // Métodos de gestión de clientes
    public boolean agregarCliente(Cliente cliente) {
        administrador.agregarCliente(cliente);
        return true;
    }

    public Cliente buscarCliente(int id) {
        return administrador.buscarCliente(id);
    }

    public ArrayList<Cliente> obtenerClientes() {
        return administrador.getClientes();
    }

    // Métodos de persistencia
    public boolean guardarEmpleados() {
        try {
            administrador.guardarEmpleados();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean cargarEmpleados() {
        try {
            administrador.cargarEmpleados();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Métodos de reportes
    public String generarReporteCompleto() {
        StringBuilder reporte = new StringBuilder();
        reporte.append("=== REPORTE COMPLETO DEL SISTEMA ===\n\n");
        reporte.append("Total de empleados: ").append(administrador.getEmpleados().size()).append("\n");
        reporte.append("Total de transacciones: ").append(administrador.getTransacciones().size()).append("\n");
        reporte.append("Total de clientes: ").append(administrador.getClientes().size()).append("\n\n");

        // Calcular total de dinero en el banco
        double totalDinero = 0;
        for (Cliente cliente : administrador.getClientes()) {
            totalDinero += cliente.getCuenta().getSaldo();
        }
        reporte.append("Total dinero en el banco: $").append(totalDinero).append("\n");

        return reporte.toString();
    }
}