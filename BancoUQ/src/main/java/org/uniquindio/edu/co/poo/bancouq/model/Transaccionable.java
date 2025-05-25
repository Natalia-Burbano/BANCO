package org.uniquindio.edu.co.poo.bancouq.model;

public interface Transaccionable {
    void depositar(double monto);
    boolean retirar(double monto);
    void transferir(double monto, Cuenta destino);
}