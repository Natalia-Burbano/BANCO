package org.uniquindio.edu.co.poo.bancouq.model;
import org.uniquindio.edu.co.poo.bancouq.model.Administrador;



public class ServicioBancario {
    private Administrador administrador;

    public ServicioBancario(Administrador administrador) {
        this.administrador = administrador;
    }

    // Operaciones básicas del cajero
    public boolean realizarDeposito(int idCliente, double monto) {
        try {
            Cliente cliente = administrador.buscarCliente(idCliente);
            if (cliente != null && monto > 0) {
                cliente.getCuenta().depositar(monto);
                administrador.registrarTransaccion(new Transaccion("Deposito", monto));
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean realizarRetiro(int idCliente, double monto) {
        try {
            Cliente cliente = administrador.buscarCliente(idCliente);
            if (cliente != null && monto > 0) {
                boolean exito = cliente.getCuenta().retirar(monto);
                if (exito) {
                    administrador.registrarTransaccion(new Transaccion("Retiro", monto));
                }
                return exito;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean realizarTransferencia(int idOrigen, int idDestino, double monto) {
        try {
            Cliente clienteOrigen = administrador.buscarCliente(idOrigen);
            Cliente clienteDestino = administrador.buscarCliente(idDestino);

            if (clienteOrigen != null && clienteDestino != null && monto > 0) {
                clienteOrigen.getCuenta().transferir(monto, clienteDestino.getCuenta());
                administrador.registrarTransaccion(new Transaccion("Transferencia", monto));
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public double consultarSaldo(int idCliente) {
        Cliente cliente = administrador.buscarCliente(idCliente);
        if (cliente != null) {
            return cliente.getCuenta().getSaldo();
        }
        return -1;
    }
}
