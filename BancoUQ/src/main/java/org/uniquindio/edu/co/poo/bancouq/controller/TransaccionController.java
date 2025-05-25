package org.uniquindio.edu.co.poo.bancouq.controller;

import org.uniquindio.edu.co.poo.bancouq.model.*;
import java.util.ArrayList;

public class TransaccionController {

    private Administrador administrador;
    private ServicioBancario servicioBancario;

    public TransaccionController(Administrador administrador) {
        this.administrador = administrador;
        this.servicioBancario = new ServicioBancario(administrador);
    }

    // Métodos para realizar transacciones por número de cuenta
    public boolean realizarDeposito(int numeroCuenta, double monto) {
        Cliente cliente = buscarClientePorNumeroCuenta(numeroCuenta);
        if (cliente != null) {
            return servicioBancario.realizarDeposito(cliente.getId(), monto);
        }
        return false;
    }

    public boolean realizarRetiro(int numeroCuenta, double monto) {
        Cliente cliente = buscarClientePorNumeroCuenta(numeroCuenta);
        if (cliente != null) {
            return servicioBancario.realizarRetiro(cliente.getId(), monto);
        }
        return false;
    }

    public boolean realizarTransferencia(int numeroCuentaOrigen, int numeroCuentaDestino, double monto) {
        Cliente clienteOrigen = buscarClientePorNumeroCuenta(numeroCuentaOrigen);
        Cliente clienteDestino = buscarClientePorNumeroCuenta(numeroCuentaDestino);

        if (clienteOrigen != null && clienteDestino != null) {
            return servicioBancario.realizarTransferencia(clienteOrigen.getId(), clienteDestino.getId(), monto);
        }
        return false;
    }

    public double consultarSaldo(int numeroCuenta) {
        if (numeroCuenta <= 0) {
            // Número de cuenta inválido
            return -1;
        }

        Cliente cliente = buscarClientePorNumeroCuenta(numeroCuenta);
        if (cliente == null) {
            // Cliente no encontrado
            return -1;
        }

        double saldo = servicioBancario.consultarSaldo(cliente.getId());
        if (saldo < 0) {
            // Saldo no disponible o error en la consulta
            return -1;
        }

        return saldo;
    }

    // Métodos auxiliares
    public Cliente buscarClientePorNumeroCuenta(int numeroCuenta) {
        if (administrador == null || administrador.getClientes() == null) {
            return null;
        }
        for (Cliente cliente : administrador.getClientes()) {
            if (cliente == null || cliente.getCuenta() == null) {
                continue;
            }
            if (cliente.getCuenta().getNumeroCuenta() == numeroCuenta) {
                return cliente;
            }
        }
        return null;
    }

    public boolean existeCuenta(int numeroCuenta) {
        return buscarClientePorNumeroCuenta(numeroCuenta) != null;
    }

    public String obtenerTipoCuenta(int numeroCuenta) {
        Cliente cliente = buscarClientePorNumeroCuenta(numeroCuenta);
        if (cliente != null) {
            Cuenta cuenta = cliente.getCuenta();
            if (cuenta instanceof CuentaAhorro) {
                return "Cuenta de Ahorro";
            } else if (cuenta instanceof CuentaCorriente) {
                return "Cuenta Corriente";
            } else if (cuenta instanceof CuentaEmpresarial) {
                return "Cuenta Empresarial";
            }
        }
        return "Desconocido";
    }

    public Cliente obtenerPropietarioCuenta(int numeroCuenta) {
        return buscarClientePorNumeroCuenta(numeroCuenta);
    }

    // Métodos de gestión de transacciones
    public boolean registrarTransaccion(String tipo, double monto) {
        if (monto > 0) {
            Transaccion transaccion = new Transaccion(tipo, monto);
            administrador.registrarTransaccion(transaccion);
            return true;
        }
        return false;
    }

    public ArrayList<Transaccion> obtenerTransacciones() {
        return administrador.getTransacciones();
    }

    public ArrayList<Transaccion> obtenerTransaccionesSospechosas() {
        return administrador.getTransaccionesSospechosas();
    }

    // Métodos específicos para cuenta corriente
    public double obtenerLimiteSobregiro(int numeroCuenta) {
        Cliente cliente = buscarClientePorNumeroCuenta(numeroCuenta);
        if (cliente != null && cliente.getCuenta() instanceof CuentaCorriente) {
            CuentaCorriente cuentaCorriente = (CuentaCorriente) cliente.getCuenta();
            return cuentaCorriente.getLimiteSobregiro();
        }
        return 0;
    }

    public boolean tieneSobregiro(int numeroCuenta) {
        Cliente cliente = buscarClientePorNumeroCuenta(numeroCuenta);
        if (cliente != null) {
            return cliente.getCuenta().getSaldo() < 0;
        }
        return false;
    }
}