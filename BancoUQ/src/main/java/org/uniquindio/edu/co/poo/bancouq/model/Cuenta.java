package org.uniquindio.edu.co.poo.bancouq.model;

public abstract class Cuenta implements Transaccionable {
    private int numeroCuenta;
    private double saldo;

    public Cuenta(int numeroCuenta, double saldo) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
    }

    public int getNumeroCuenta() {
        return numeroCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public void depositar(double monto) {
        this.saldo += monto;
    }

    @Override
    public boolean retirar(double monto) {
        if (monto <= saldo) {
            saldo -= monto;
            return true;
        }
        return false;
    }

    @Override
    public void transferir(double monto, Cuenta destino) {
        if (this.retirar(monto)) {
            destino.depositar(monto);
        }
    }
}