package org.uniquindio.edu.co.poo.bancouq.model;

//Clase abstracta Usuario
public abstract class Usuario {
    private int id;
    private String nombre;
    private String username;
    private String password;

    //Constructor de la clase Usuario

    public Usuario(int id, String nombre, String username, String password) {
        this.id = id;
        this.nombre = nombre;
        this.username = username;
        this.password = password;
    }

    //Metodos setter y getter de la clase Usuario

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