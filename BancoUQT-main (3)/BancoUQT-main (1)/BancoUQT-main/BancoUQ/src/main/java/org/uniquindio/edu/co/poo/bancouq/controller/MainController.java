package org.uniquindio.edu.co.poo.bancouq.controller;

import org.uniquindio.edu.co.poo.bancouq.model.Cliente;
import org.uniquindio.edu.co.poo.bancouq.model.Cuenta;
import org.uniquindio.edu.co.poo.bancouq.model.CuentaAhorro;
import org.uniquindio.edu.co.poo.bancouq.model.CuentaCorriente;
import org.uniquindio.edu.co.poo.bancouq.model.CuentaEmpresarial;
import org.uniquindio.edu.co.poo.bancouq.model.Transaccion;
import org.uniquindio.edu.co.poo.bancouq.util.ArchivoUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    private List<Cliente> clientes;
    private List<Transaccion> transacciones;
    private static final String RUTA_ARCHIVO_CLIENTES = "clientes.txt";
    private static final String RUTA_ARCHIVO_TRANSACCIONES = "transacciones.txt";

    public MainController() {
        this.clientes = new ArrayList<>();
        this.transacciones = new ArrayList<>();
    }

    // Gestión de clientes
    public void registrarCliente(int id, String nombre, String tipoCuenta, int numeroCuenta, double saldoInicial) {
        Cuenta cuenta = crearCuenta(tipoCuenta, numeroCuenta, saldoInicial);
        Cliente cliente = new Cliente(id, nombre, cuenta);
        clientes.add(cliente);
    }

    public void registrarCliente(int id, String nombre, String tipoCuenta, int numeroCuenta, double saldoInicial, double limiteSobregiro) {
        Cuenta cuenta;
        if ("corriente".equalsIgnoreCase(tipoCuenta)) {
            cuenta = new CuentaCorriente(numeroCuenta, saldoInicial, limiteSobregiro);
        } else {
            cuenta = crearCuenta(tipoCuenta, numeroCuenta, saldoInicial);
        }
        Cliente cliente = new Cliente(id, nombre, cuenta);
        clientes.add(cliente);
    }

    private Cuenta crearCuenta(String tipoCuenta, int numeroCuenta, double saldoInicial) {
        switch (tipoCuenta.toLowerCase()) {
            case "ahorro":
                return new CuentaAhorro(numeroCuenta, saldoInicial);
            case "corriente":
                return new CuentaCorriente(numeroCuenta, saldoInicial, 0);
            case "empresarial":
                return new CuentaEmpresarial(numeroCuenta, saldoInicial);
            default:
                return new CuentaAhorro(numeroCuenta, saldoInicial);
        }
    }

    public void eliminarCliente(Cliente cliente) {
        clientes.remove(cliente);
    }

    public Cliente buscarClientePorId(int id) {
        return clientes.stream()
                .filter(cliente -> cliente.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Cliente buscarClientePorNumeroCuenta(int numeroCuenta) {
        return clientes.stream()
                .filter(cliente -> cliente.getCuenta().getNumeroCuenta() == numeroCuenta)
                .findFirst()
                .orElse(null);
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    // Gestión de transacciones
    public boolean realizarDeposito(int numeroCuenta, double monto) {
        Cliente cliente = buscarClientePorNumeroCuenta(numeroCuenta);
        if (cliente != null && monto > 0) {
            cliente.getCuenta().depositar(monto);
            registrarTransaccion("DEPOSITO", monto);
            return true;
        }
        return false;
    }

    public boolean realizarRetiro(int numeroCuenta, double monto) {
        Cliente cliente = buscarClientePorNumeroCuenta(numeroCuenta);
        if (cliente != null && monto > 0) {
            boolean exito = cliente.getCuenta().retirar(monto);
            if (exito) {
                registrarTransaccion("RETIRO", monto);
            }
            return exito;
        }
        return false;
    }

    public boolean realizarTransferencia(int numeroCuentaOrigen, int numeroCuentaDestino, double monto) {
        Cliente clienteOrigen = buscarClientePorNumeroCuenta(numeroCuentaOrigen);
        Cliente clienteDestino = buscarClientePorNumeroCuenta(numeroCuentaDestino);

        if (clienteOrigen != null && clienteDestino != null && monto > 0) {
            if (clienteOrigen.getCuenta().retirar(monto)) {
                clienteDestino.getCuenta().depositar(monto);
                registrarTransaccion("TRANSFERENCIA", monto);
                return true;
            }
        }
        return false;
    }

    public double consultarSaldo(int numeroCuenta) {
        Cliente cliente = buscarClientePorNumeroCuenta(numeroCuenta);
        return cliente != null ? cliente.getCuenta().getSaldo() : -1;
    }

    private void registrarTransaccion(String tipo, double monto) {
        Transaccion transaccion = new Transaccion(tipo, monto);
        transacciones.add(transaccion);
    }

    public List<Transaccion> getTransacciones() {
        return transacciones;
    }

    // Persistencia de datos
    public void guardarClientes() throws IOException {
        List<String> lineas = new ArrayList<>();
        for (Cliente cliente : clientes) {
            Cuenta cuenta = cliente.getCuenta();
            String tipoCuenta = cuenta.getClass().getSimpleName().replace("Cuenta", "").toLowerCase();
            String linea = cliente.getId() + "," + cliente.getNombre() + "," +
                    tipoCuenta + "," + cuenta.getNumeroCuenta() + "," + cuenta.getSaldo();

            if (cuenta instanceof CuentaCorriente) {
                CuentaCorriente cc = (CuentaCorriente) cuenta;
                // Nota: Necesitarías un getter para limiteSobregiro en CuentaCorriente
                // linea += "," + cc.getLimiteSobregiro();
            }

            lineas.add(linea);
        }
        ArchivoUtil.escribirArchivo(RUTA_ARCHIVO_CLIENTES, lineas);
    }

    public void cargarClientes() throws IOException {
        clientes.clear();
        List<String> lineas = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_CLIENTES);
        for (String linea : lineas) {
            String[] partes = linea.split(",");
            int id = Integer.parseInt(partes[0]);
            String nombre = partes[1];
            String tipoCuenta = partes[2];
            int numeroCuenta = Integer.parseInt(partes[3]);
            double saldo = Double.parseDouble(partes[4]);

            if (partes.length > 5 && "corriente".equals(tipoCuenta)) {
                double limiteSobregiro = Double.parseDouble(partes[5]);
                registrarCliente(id, nombre, tipoCuenta, numeroCuenta, saldo, limiteSobregiro);
            } else {
                registrarCliente(id, nombre, tipoCuenta, numeroCuenta, saldo);
            }
        }
    }

    public void guardarTransacciones() throws IOException {
        List<String> lineas = new ArrayList<>();
        for (Transaccion transaccion : transacciones) {
            String linea = transaccion.getTipo() + "," + transaccion.getMonto() + "," +
                    transaccion.getFecha().toString();
            lineas.add(linea);
        }
        ArchivoUtil.escribirArchivo(RUTA_ARCHIVO_TRANSACCIONES, lineas);
    }

    public void cargarTransacciones() throws IOException {
        transacciones.clear();
        List<String> lineas = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_TRANSACCIONES);
        for (String linea : lineas) {
            String[] partes = linea.split(",");
            String tipo = partes[0];
            double monto = Double.parseDouble(partes[1]);
            // Para cargar la fecha necesitarías un constructor adicional en Transaccion
            // que acepte LocalDateTime o un método para establecer la fecha
            Transaccion transaccion = new Transaccion(tipo, monto);
            transacciones.add(transaccion);
        }
    }

    // Validaciones de numero de cuenta y monto
    public boolean validarNumeroCuenta(int numeroCuenta) {
        return numeroCuenta > 0 && String.valueOf(numeroCuenta).length() >= 4;
    }

    public boolean validarMonto(double monto) {
        return monto > 0;
    }

    public boolean existeNumeroCuenta(int numeroCuenta) {
        return buscarClientePorNumeroCuenta(numeroCuenta) != null;
    }
}