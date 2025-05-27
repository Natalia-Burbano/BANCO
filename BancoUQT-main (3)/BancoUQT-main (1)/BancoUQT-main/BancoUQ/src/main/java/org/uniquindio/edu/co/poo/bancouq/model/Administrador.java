package org.uniquindio.edu.co.poo.bancouq.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Clase que representa un administrador del sistema de banco.
public class Administrador {
    private String usuario;
    private String contrasena;
    private List<Empleado> empleados;
    private List<Transaccion> transacciones;

    private static final String RUTA_ARCHIVO_EMPLEADOS = "empleados.txt";
// Constructor de la clase administrador.
    public Administrador(String usuario, String contrasena) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.empleados = new ArrayList<>();
        this.transacciones = new ArrayList<>();
    }
//Metodo que permite guardar un empleado en el sistema.
    public void guardarEmpleados() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA_ARCHIVO_EMPLEADOS))) {
            for (Empleado empleado : empleados) {
                writer.write(empleado.getId() + "," + empleado.getNombre() + "," + empleado.getPuesto());
                writer.newLine();
            }
        }
    }

    //Metodo que permite cargar un empleado del sistema
    public void cargarEmpleados() throws IOException {
        empleados.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(RUTA_ARCHIVO_EMPLEADOS))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                int id = Integer.parseInt(partes[0]);
                String nombre = partes[1];
                String puesto = partes[2];
                empleados.add(new Empleado(id, nombre, puesto));
            }
        }
    }
}