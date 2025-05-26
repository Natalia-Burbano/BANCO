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
        guardarEmpleados(); // Guardar cuando agregue
    }

    public boolean eliminarEmpleado(int id) {
        for (int i = 0; i < empleados.size(); i++) {
            if (empleados.get(i).getId() == id) {
                empleados.remove(i);
                guardarEmpleados(); // Guardar cuando elimine
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
        guardarClientes(); // Guardar cuando agregue
    }

    public Cliente buscarCliente(int id) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId() == id) {
                return clientes.get(i);
            }
        }
        return null;
    }


    // Gestión de transacciones
    public void registrarTransaccion(Transaccion transaccion) {
        transacciones.add(transaccion);
        guardarTransacciones(); // Guardar cuando registre
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

    // ARREGLAR GUARDAR EMPLEADOS - AHORA SÍ FUNCIONA BIEN
    public void guardarEmpleados() {
        try {
            FileWriter archivo = new FileWriter("empleados.txt");
            for (int i = 0; i < empleados.size(); i++) {
                Empleado emp = empleados.get(i);
                archivo.write(emp.getId() + "," + emp.getNombre() + "," + emp.getPuesto() + "\n");
            }
            archivo.close();
        } catch (IOException e) {
            System.out.println("Error al guardar empleados: " + e.getMessage());
        }
    }

    public void cargarEmpleados() {
        try {
            empleados.clear();
            BufferedReader reader = new BufferedReader(new FileReader("empleados.txt"));
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (!linea.trim().equals("")) { // No procesa líneas vacías
                    String[] partes = linea.split(",");
                    if (partes.length == 3) {
                        int id = Integer.parseInt(partes[0]);
                        String nombre = partes[1];
                        String puesto = partes[2];
                        empleados.add(new Empleado(id, nombre, puesto));
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error al cargar empleados: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error en formato de archivo: " + e.getMessage());
        }
    }

    // MÉTODOS PARA GUARDAR CLIENTES
    public void guardarClientes() {
        try {
            FileWriter archivo = new FileWriter("clientes.txt");
            for (int i = 0; i < clientes.size(); i++) {
                Cliente cliente = clientes.get(i);
                archivo.write(cliente.getId() + "," + cliente.getNombre() + "," + cliente.getCuenta().getSaldo() + "\n");
            }
            archivo.close();
        } catch (IOException e) {
            System.out.println("Error al guardar clientes: " + e.getMessage());
        }
    }

    public void cargarClientes() {
        try {
            clientes.clear();
            BufferedReader reader = new BufferedReader(new FileReader("clientes.txt"));
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (!linea.trim().equals("")) { // No procesar líneas vacías
                    String[] partes = linea.split(",");
                    if (partes.length == 3) {
                        int id = Integer.parseInt(partes[0]);
                        String nombre = partes[1];
                        double saldo = Double.parseDouble(partes[2]);

                        // Crear cuenta de ahorro por defecto
                        CuentaAhorro cuenta = new CuentaAhorro(id + 1000, saldo);
                        clientes.add(new Cliente(id, nombre, cuenta));
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error al cargar clientes: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error en formato de archivo clientes: " + e.getMessage());
        }
    }

    // MÉTODOS PARA TRANSACCIONES
    public void guardarTransacciones() {
        try {
            FileWriter archivo = new FileWriter("transacciones.txt");
            archivo.write("Tipo,Monto,Fecha\n"); // Escribir encabezado
            for (int i = 0; i < transacciones.size(); i++) {
                Transaccion trans = transacciones.get(i);
                archivo.write(trans.getTipo() + "," + trans.getMonto() + "," + trans.getFecha() + "\n");
            }
            archivo.close();
        } catch (IOException e) {
            System.out.println("Error al guardar transacciones: " + e.getMessage());
        }
    }

    public void cargarTransacciones() {
        try {
            transacciones.clear();
            BufferedReader reader = new BufferedReader(new FileReader("transacciones.txt"));
            String linea;
            boolean primeraLinea = true;
            while ((linea = reader.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false; // Saltar la primera línea (encabezado)
                    continue;
                }
                if (!linea.trim().equals("")) {
                    String[] partes = linea.split(",");
                    if (partes.length == 3) {
                        String tipo = partes[0];
                        double monto = Double.parseDouble(partes[1]);
                        transacciones.add(new Transaccion(tipo, monto));
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error al cargar transacciones: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error en formato de archivo transacciones: " + e.getMessage());
        }
    }

    // MÉTODO PARA CARGAR TODO AL INICIO
    public void cargarTodo() {
        cargarEmpleados();
        cargarClientes();
        cargarTransacciones();
    }
}