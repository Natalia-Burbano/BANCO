package org.uniquindio.edu.co.poo.bancouq.model;

import javafx.beans.property.*;
// Clase que representa un cliente del sistema de banco.
public class Cliente {
    private final IntegerProperty id;
    private final StringProperty nombre;
    private final ObjectProperty<Cuenta> cuenta;

//Constructor de la clase cliente.
    public Cliente(int id, String nombre, Cuenta cuenta) {
        this.id = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.cuenta = new SimpleObjectProperty<>(cuenta);
    }

//Metodos de la clase cliente.
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

    public Cuenta getCuenta() {
        return cuenta.get();
    }

    public ObjectProperty<Cuenta> cuentaProperty() {
        return cuenta;
    }
}