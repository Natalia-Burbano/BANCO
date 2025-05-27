package org.uniquindio.edu.co.poo.bancouq.controller;

import org.uniquindio.edu.co.poo.bancouq.model.Empleado;
import org.uniquindio.edu.co.poo.bancouq.model.Transaccion;
import org.uniquindio.edu.co.poo.bancouq.util.ArchivoUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Clase que representa un administrador del sistema de banco
public class AdministradorController {

    private List<Empleado> empleados;
    private List<Transaccion> transacciones;
    private static final String RUTA_ARCHIVO = "empleados.txt";

// Constructor de la clase administrador
    public AdministradorController() {
        this.empleados = new ArrayList<>();
        this.transacciones = new ArrayList<>();
    }

    // Metodo que permite registrar una transaccion en el sistema
    public boolean autenticar(String usuario, String contrasena) {
        return "admin".equals(usuario) && "1234".equals(contrasena);
    }

    // Metodo que permite registrar un empleado en el sistema
    public void registrarEmpleado(int id, String nombre, String puesto) {
        Empleado empleado = new Empleado(id, nombre, puesto);
        empleados.add(empleado);
    }

    // Metodo que permite modificar un empleado en el sistema
    public void modificarEmpleado(Empleado empleado, String nuevoNombre, String nuevoPuesto) {
        empleado.setNombre(nuevoNombre);
        empleado.setPuesto(nuevoPuesto);
    }

    // Metodo que permite eliminar un empleado en el sistema
    public void eliminarEmpleado(Empleado empleado) {
        empleados.remove(empleado);
    }

// Metodo que permite obtener un empleado en el sistema
    public List<Empleado> getEmpleados() {
        return empleados;
    }

    // Metodo que permite guardar un empleado en el sistema
    public void guardarEmpleados() throws IOException {
        List<String> lineas = new ArrayList<>();
        for (Empleado empleado : empleados) {
            String linea = empleado.getId() + "," + empleado.getNombre() + "," + empleado.getPuesto();
            lineas.add(linea);
        }
        ArchivoUtil.escribirArchivo(RUTA_ARCHIVO, lineas);
    }

    // Metodo que permite cargar un empleado del sistema
    public void cargarEmpleados() throws IOException {
        empleados.clear();
        List<String> lineas = ArchivoUtil.leerArchivo(RUTA_ARCHIVO);
        for (String linea : lineas) {
            String[] partes = linea.split(",");
            int id = Integer.parseInt(partes[0]);
            String nombre = partes[1];
            String puesto = partes[2];
            Empleado empleado = new Empleado(id, nombre, puesto);
            empleados.add(empleado);
        }
    }

    // Metodo que permite registrar una transaccion en el sistema
    public List<Transaccion> getTransacciones() {
        return transacciones;
    }
}