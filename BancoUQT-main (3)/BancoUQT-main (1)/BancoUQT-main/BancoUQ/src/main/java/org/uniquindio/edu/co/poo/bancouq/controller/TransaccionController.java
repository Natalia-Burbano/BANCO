package org.uniquindio.edu.co.poo.bancouq.controller;

import org.uniquindio.edu.co.poo.bancouq.model.Cuenta;
import java.util.List;


public class TransaccionController {

    private List<Cuenta> cuentas;

    public TransaccionController(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    public boolean realizarTransaccion(int numeroCuentaOrigen, int numeroCuentaDestino,
                                       double monto, String tipoTransaccion) {

        Cuenta cuentaOrigen = buscarCuenta(numeroCuentaOrigen);
        Cuenta cuentaDestino = buscarCuenta(numeroCuentaDestino);

        switch (tipoTransaccion) {
            case "TRANSFERENCIA":
                if (cuentaOrigen != null && cuentaDestino != null) {
                    cuentaOrigen.transferir(monto, cuentaDestino);
                    return true;
                }
                break;
            case "DEPOSITO":
                if (cuentaDestino != null) {
                    cuentaDestino.depositar(monto);
                    return true;
                }
                break;
            case "RETIRO":
                if (cuentaOrigen != null) {
                    return cuentaOrigen.retirar(monto);
                }
                break;
        }

        return false;
    }

    // Metodo que permite obtener una cuenta dado su numero de cuenta
    private Cuenta buscarCuenta(int numeroCuenta) {
        for (Cuenta cuenta : cuentas) {
            if (cuenta.getNumeroCuenta() == numeroCuenta) {
                return cuenta;
            }
        }
        return null;
    }
}