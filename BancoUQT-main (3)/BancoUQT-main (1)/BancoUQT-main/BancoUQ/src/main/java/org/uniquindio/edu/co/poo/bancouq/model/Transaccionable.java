package org.uniquindio.edu.co.poo.bancouq.model;
/**
 * Interfaz que define las operaciones básicas que pueden realizarse sobre una cuenta bancaria.
 */
public interface Transaccionable {
    /**
     * Realiza un depósito en la cuenta
     * @param monto Cantidad a depositar (debe ser positivo)
     */
    void depositar(double monto);

    /**
     * Intenta realizar un retiro de la cuenta
     * @param monto Cantidad a retirar
     * @return true si el retiro fue exitoso, false si no hay fondos suficientes
     */
    boolean retirar(double monto);

    /**
     * Transfiere fondos a otra cuenta
     * @param monto Cantidad a transferir
     * @param destino Cuenta de destino para la transferencia
     */
    void transferir(double monto, Cuenta destino);
}