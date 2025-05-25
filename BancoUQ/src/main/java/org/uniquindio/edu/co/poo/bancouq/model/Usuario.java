package org.uniquindio.edu.co.poo.bancouq.model;

public abstract class Usuario {
    private int id;
    private String nombre;
    private String username;
    private String password;

    public Usuario(int id, String nombre, String username, String password) {
        this.id = id;
        this.nombre = nombre;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public abstract String getRol();
}