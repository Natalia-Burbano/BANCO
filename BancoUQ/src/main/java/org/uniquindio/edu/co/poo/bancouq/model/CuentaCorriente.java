package org.uniquindio.edu.co.poo.bancouq.model;

public class CuentaCorriente extends Cuenta {
    private double limiteSobregiro;

    public CuentaCorriente(int numeroCuenta, double saldo, double limiteSobregiro) {
        super(numeroCuenta, saldo);
        this.limiteSobregiro = limiteSobregiro;
    }
    public double getLimiteSobregiro() {
        return limiteSobregiro;
    }

    @Override
    public boolean retirar(double monto) {
        if (monto <= getSaldo() + limiteSobregiro) {
            setSaldo(getSaldo() - monto);
            return true;
        }
        return false;
    }
}