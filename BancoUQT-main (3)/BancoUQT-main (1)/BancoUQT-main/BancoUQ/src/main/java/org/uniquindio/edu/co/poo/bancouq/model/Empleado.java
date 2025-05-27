package org.uniquindio.edu.co.poo.bancouq.model;

import javafx.beans.property.*;
// Clase que representa un empleado del sistema de banco.
public class Empleado {
    private final IntegerProperty id;
    private final StringProperty nombre;
    private final StringProperty puesto;
//Constructor de la clase empleado
    public Empleado(int id, String nombre, String puesto) {
        this.id = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.puesto = new SimpleStringProperty(puesto);
    }
//Metodos getter y setter de la clase empleado
    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getNombre() {
        return nombre.get();
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getPuesto() {
        return puesto.get();
    }

    public StringProperty puestoProperty() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto.set(puesto);
    }
}