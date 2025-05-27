package org.uniquindio.edu.co.poo.bancouq.model;
// Clase abstracta Cuenta que representa una cuenta bancaria.
public abstract class Cuenta implements Transaccionable {
    private int numeroCuenta;
    private double saldo;
// Constructor de la clase Cuenta
    public Cuenta(int numeroCuenta, double saldo) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
    }
//Metodos setter y getter de la clase Cuenta
    public int getNumeroCuenta() {
        return numeroCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    //Metodo que permite realizar una transferencia entre dos cuentas.
    @Override
    public void depositar(double monto) {
        this.saldo += monto;
    }
//Metodo que permite realizar un retiro de una cuenta
    @Override
    public boolean retirar(double monto) {
        if (monto <= saldo) {
            saldo -= monto;
            return true;
        }
        return false;
    }
//Metodo que permite realizar una transferencia entre dos cuentas
    @Override
    public void transferir(double monto, Cuenta destino) {
        if (this.retirar(monto)) {
            destino.depositar(monto);
        }
    }
}