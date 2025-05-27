package org.uniquindio.edu.co.poo.bancouq.model;

/**
 * Clase que representa una cuenta de ahorros.
 * Extiende la funcionalidad básica de una cuenta bancaria.
 */
public class CuentaAhorro extends Cuenta {
    /**
     * Constructor para crear una cuenta de ahorros
     * @param numeroCuenta Número identificador de la cuenta
     * @param saldo Saldo inicial de la cuenta
     */
    public CuentaAhorro(int numeroCuenta, double saldo) {
        super(numeroCuenta, saldo);
    }
}