package org.uniquindio.edu.co.poo.bancouq.model;

import java.time.LocalDateTime;

public class Transaccion {

    private String tipo;
    private double monto;
    private String fecha;

    public Transaccion(String tipo, double monto) {
        this.tipo = tipo;
        this.monto = monto;
        this.fecha = java.time.LocalDate.now().toString(); // Fecha simple
    }

    // Getters básicos
    public String getTipo() {
        return tipo;
    }

    public double getMonto() {
        return monto;
    }

    public String getFecha() {
        return fecha;
    }

    // Método para mostrar información
    public void mostrarTransaccion() {
        System.out.println("Tipo: " + tipo);
        System.out.println("Monto: " + monto);
        System.out.println("Fecha: " + fecha);
    }

    // Método para mostrar información en JavaFX
    public String getInfoCompleta() {
        return tipo + " | $" + monto + " | " + fecha;
    }
}