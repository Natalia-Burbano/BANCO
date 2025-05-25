package org.uniquindio.edu.co.poo.bancouq.model;

import java.util.ArrayList;
import java.io.*;

public class Administrador {
    private String usuario;
    private String contrasena;
    private ArrayList<Empleado> empleados;
    private ArrayList<Transaccion> transacciones;
    private ArrayList<Cliente> clientes;

    public Administrador(String usuario, String contrasena) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.empleados = new ArrayList<Empleado>();
        this.transacciones = new ArrayList<Transaccion>();
        this.clientes = new ArrayList<Cliente>();
    }

    // Getters básicos
    public String getUsuario() {
        return usuario;
    }

    public ArrayList<Empleado> getEmpleados() {
        return empleados;
    }

    public ArrayList<Transaccion> getTransacciones() {
        return transacciones;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    // Gestión de empleados
    public void agregarEmpleado(Empleado empleado) {
        empleados.add(empleado);
    }

    public boolean eliminarEmpleado(int id) {
        for (int i = 0; i < empleados.size(); i++) {
            if (empleados.get(i).getId() == id) {
                empleados.remove(i);
                return true;
            }
        }
        return false;
    }

    public Empleado buscarEmpleado(int id) {
        for (int i = 0; i < empleados.size(); i++) {
            if (empleados.get(i).getId() == id) {
                return empleados.get(i);
            }
        }
        return null;
    }

    // Gestión de clientes
    public void agregarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public Cliente buscarCliente(int id) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId() == id) {
                return clientes.get(i);
            }
        }
        return null;
    }

    public Cliente buscarClientePorUsername(String username) {
        // Método removido ya que Cliente no tiene username
        return null;
    }

    // Gestión de transacciones
    public void registrarTransaccion(Transaccion transaccion) {
        transacciones.add(transaccion);
    }

    public ArrayList<Transaccion> getTransaccionesSospechosas() {
        ArrayList<Transaccion> sospechosas = new ArrayList<Transaccion>();
        for (int i = 0; i < transacciones.size(); i++) {
            if (transacciones.get(i).getMonto() > 10000) {
                sospechosas.add(transacciones.get(i));
            }
        }
        return sospechosas;
    }

    // Autenticación
    public boolean autenticar(String username, String password) {
        return this.usuario.equals(username) && this.contrasena.equals(password);
    }

    // Guardar empleados en archivo (manejo básico de excepciones)
    public void guardarEmpleados() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("empleados.txt"));
            for (int i = 0; i < empleados.size(); i++) {
                Empleado emp = empleados.get(i);
                writer.write(emp.getId() + "," + emp.getNombre() + "," + emp.getPuesto());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error al guardar empleados: " + e.getMessage());
        }
    }

    // Cargar empleados desde archivo (manejo básico de excepciones)
    public void cargarEmpleados() {
        try {
            empleados.clear();
            BufferedReader reader = new BufferedReader(new FileReader("empleados.txt"));
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 3) {
                    int id = Integer.parseInt(partes[0]);
                    String nombre = partes[1];
                    String puesto = partes[2];
                    empleados.add(new Empleado(id, nombre, puesto));
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error al cargar empleados: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error en formato de archivo: " + e.getMessage());
        }
    }
}

