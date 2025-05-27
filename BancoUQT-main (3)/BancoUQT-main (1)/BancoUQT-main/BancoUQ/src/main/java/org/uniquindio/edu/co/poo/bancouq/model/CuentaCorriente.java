package org.uniquindio.edu.co.poo.bancouq.model;

//Clase que representa una cuenta corriente. Extiende la funcionalidad de una cuenta bancaria
public class CuentaCorriente extends Cuenta {
    private double limiteSobregiro;

    //Constructor de la clase CuentaCorriente
    public CuentaCorriente(int numeroCuenta, double saldo, double limiteSobregiro) {
        super(numeroCuenta, saldo);
        this.limiteSobregiro = limiteSobregiro;
    }
//Metodos setter y getter de la clase CuentaCorriente
    @Override
    public boolean retirar(double monto) {
        if (monto <= getSaldo() + limiteSobregiro) {
            setSaldo(getSaldo() - monto);
            return true;
        }
        return false;
    }
}