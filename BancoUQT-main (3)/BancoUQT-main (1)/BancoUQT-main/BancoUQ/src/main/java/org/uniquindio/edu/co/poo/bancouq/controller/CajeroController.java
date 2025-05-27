package org.uniquindio.edu.co.poo.bancouq.controller;

import org.uniquindio.edu.co.poo.bancouq.model.*;
import org.uniquindio.edu.co.poo.bancouq.util.ArchivoUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Clase que representa un cajero del sistema de banco, que tiene la capacidad de realizar operaciones en las cuentas del cliente
public class CajeroController {

    private List<Cliente> clientes;
    private static final String RUTA_ARCHIVO = "clientes.txt";

    // Constructor de la clase cajero, que inicializa la lista de clientes
    public CajeroController() {
        this.clientes = new ArrayList<>();
    }

    // Metodo que permite registrar un cliente en el sistema
    public void registrarCliente(int id, String nombre, String tipoCuenta)   {
        Cuenta cuenta;
        if (tipoCuenta.equals("Ahorros")) {
            cuenta = new CuentaAhorro(id, 0);
        } else if (tipoCuenta.equals("Corriente")) {
            cuenta = new CuentaCorriente(id, 0, 1000);
        } else {
            cuenta = new CuentaEmpresarial(id, 0);
        }

        Cliente cliente = new Cliente(id, nombre, cuenta);
        clientes.add(cliente);
    }

    // Metodo que permite eliminar un cliente en el sistema
    public void depositar(Cliente cliente, double monto) {
        cliente.getCuenta().depositar(monto);
    }

    // Metodo que permite retirar dinero de una cuenta del cliente
    public boolean retirar(Cliente cliente, double monto) {
        return cliente.getCuenta().retirar(monto);
    }

    // Metodo que permite transferir dinero entre cuentas del cliente
    public boolean transferir(Cliente origen, Cliente destino, double monto) {
        if (origen.getCuenta().retirar(monto)) {
            destino.getCuenta().depositar(monto);
            return true;
        }
        return false;
    }

    // Metodo que permite consultar el saldo de una cuenta del cliente
    public double consultarSaldo(Cliente cliente) {
        return cliente.getCuenta().getSaldo();
    }

    // Metodo que permite obtener un cliente en el sistema, dado su id
    public Cliente buscarCliente(int id) {
        for (Cliente cliente : clientes) {
            if (cliente.getId() == id) {
                return cliente;
            }
        }
        return null;
    }

    // Metodo que permite obtener la lista de clientes del sistema
    public List<Cliente> getClientes() {
        return clientes;
    }

    // Metodo que permite guardar la lista de clientes del sistema en un archivo de texto
    public void guardarClientes() throws IOException {
        List<String> lineas = new ArrayList<>();
        for (Cliente cliente : clientes) {
            String linea = cliente.getId() + "," + cliente.getNombre() + "," + cliente.getCuenta().getSaldo();
            lineas.add(linea);
        }
        ArchivoUtil.escribirArchivo(RUTA_ARCHIVO, lineas);
    }

    /**
     * Carga la lista de clientes desde un archivo de texto.
     *
     * // 1. Limpia la lista actual de clientes
     * // 2. Lee todas las líneas del archivo especificado en RUTA_ARCHIVO
     * // 3. Para cada línea del archivo:
     * //    a. Divide la línea usando comas como separador
     * //    b. Convierte el primer campo a entero (ID)
     * //    c. Toma el segundo campo como nombre
     * //    d. Convierte el tercer campo a double (saldo)
     * //    e. Crea una nueva CuentaAhorro con el ID y saldo
     * //    f. Crea un nuevo Cliente con los datos obtenidos
     * //    g. Agrega el cliente a la lista
     *
     * @throws IOException Si ocurre un error al leer el archivo
     */
    public void cargarClientes() throws IOException {
        clientes.clear();
        List<String> lineas = ArchivoUtil.leerArchivo(RUTA_ARCHIVO);
        for (String linea : lineas) {
            String[] partes = linea.split(",");
            int id = Integer.parseInt(partes[0]);
            String nombre = partes[1];
            double saldo = Double.parseDouble(partes[2]);
            Cuenta cuenta = new CuentaAhorro(id, saldo);
            Cliente cliente = new Cliente(id, nombre, cuenta);
            clientes.add(cliente);
        }
    }
}