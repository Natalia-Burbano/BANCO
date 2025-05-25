package org.uniquindio.edu.co.poo.bancouq.model;

public class Cliente {
    private int id;
    private String nombre;
    private Cuenta cuenta;

    public Cliente(int id, String nombre, Cuenta cuenta) {
        this.id = id;
        this.nombre = nombre;
        this.cuenta = cuenta;
    }

    // Getters básicos para JavaFX
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    // Setters básicos para JavaFX
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    // Método para mostrar información en JavaFX
    public String getInfoCompleta() {
        return "ID: " + id + " | Nombre: " + nombre + " | Cuenta: " + cuenta.getNumeroCuenta() +
                " | Saldo: $" + cuenta.getSaldo();
    }

}