package org.uniquindio.edu.co.poo.bancouq.model;


public class Empleado {
    private int id;
    private String nombre;
    private String puesto;

    public Empleado(int id, String nombre, String puesto) {
        this.id = id;
        this.nombre = nombre;
        this.puesto = puesto;
    }

    // Getters básicos para JavaFX
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPuesto() {
        return puesto;
    }

    // Setters básicos para JavaFX
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }
}