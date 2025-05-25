package org.uniquindio.edu.co.poo.bancouq.controller;

import org.uniquindio.edu.co.poo.bancouq.model.*;
import java.util.ArrayList;

public class CajeroController {

    private Administrador administrador;
    private ServicioBancario servicioBancario;

    public CajeroController(Administrador administrador) {
        this.administrador = administrador;
        this.servicioBancario = new ServicioBancario(administrador);
    }

    // Métodos de gestión de clientes
    public boolean registrarCliente(Cliente cliente) {
        administrador.agregarCliente(cliente);
        return true;
    }

    public Cliente buscarCliente(int id) {
        return administrador.buscarCliente(id);
    }

    public ArrayList<Cliente> obtenerClientes() {
        return administrador.getClientes();
    }

    public boolean existeCliente(int id) {
        return administrador.buscarCliente(id) != null;
    }

    // Métodos de operaciones bancarias
    public boolean realizarDeposito(int idCliente, double monto) {
        return servicioBancario.realizarDeposito(idCliente, monto);
    }

    public boolean realizarRetiro(int idCliente, double monto) {
        return servicioBancario.realizarRetiro(idCliente, monto);
    }

    public boolean realizarTransferencia(int idOrigen, int idDestino, double monto) {
        return servicioBancario.realizarTransferencia(idOrigen, idDestino, monto);
    }

    public double consultarSaldo(int idCliente) {
        return servicioBancario.consultarSaldo(idCliente);
    }

    // Métodos para crear cuentas
    public Cuenta crearCuentaAhorro(int numeroCuenta, double saldoInicial) {
        return new CuentaAhorro(numeroCuenta, saldoInicial);
    }

    public Cuenta crearCuentaCorriente(int numeroCuenta, double saldoInicial, double limiteSobregiro) {
        return new CuentaCorriente(numeroCuenta, saldoInicial, limiteSobregiro);
    }

    public Cuenta crearCuentaEmpresarial(int numeroCuenta, double saldoInicial) {
        return new CuentaEmpresarial(numeroCuenta, saldoInicial);
    }

    // Métodos para obtener información de cuenta
    public String obtenerTipoCuenta(Cliente cliente) {
        Cuenta cuenta = cliente.getCuenta();
        if (cuenta instanceof CuentaAhorro) {
            return "Ahorros";
        } else if (cuenta instanceof CuentaCorriente) {
            return "Corriente";
        } else if (cuenta instanceof CuentaEmpresarial) {
            return "Empresarial";
        }
        return "Desconocido";
    }

    public int obtenerNumeroCuenta(Cliente cliente) {
        return cliente.getCuenta().getNumeroCuenta();
    }

    public double obtenerSaldo(Cliente cliente) {
        return cliente.getCuenta().getSaldo();
    }

    // Métodos de reportes
    public String generarReporteClientes() {
        StringBuilder reporte = new StringBuilder();
        reporte.append("=== REPORTE DE CLIENTES ===\n\n");
        reporte.append("Total de clientes: ").append(administrador.getClientes().size()).append("\n\n");

        for (Cliente cliente : administrador.getClientes()) {
            reporte.append("ID: ").append(cliente.getId()).append("\n");
            reporte.append("Nombre: ").append(cliente.getNombre()).append("\n");
            reporte.append("Tipo de cuenta: ").append(obtenerTipoCuenta(cliente)).append("\n");
            reporte.append("Número de cuenta: ").append(cliente.getCuenta().getNumeroCuenta()).append("\n");
            reporte.append("Saldo: $").append(cliente.getCuenta().getSaldo()).append("\n");
            reporte.append("---\n");
        }

        return reporte.toString();
    }

    public String generarReporteTransacciones() {
        StringBuilder reporte = new StringBuilder();
        reporte.append("=== REPORTE DE TRANSACCIONES ===\n\n");

        ArrayList<Transaccion> transacciones = administrador.getTransacciones();
        reporte.append("Total de transacciones: ").append(transacciones.size()).append("\n\n");

        for (Transaccion transaccion : transacciones) {
            reporte.append(transaccion.getInfoCompleta()).append("\n");
        }

        return reporte.toString();
    }
}