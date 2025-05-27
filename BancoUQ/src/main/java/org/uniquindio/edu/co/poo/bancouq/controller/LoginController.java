package org.uniquindio.edu.co.poo.bancouq.controller;
import org.uniquindio.edu.co.poo.bancouq.model.Administrador;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.uniquindio.edu.co.poo.bancouq.model.*;

import java.io.IOException;

public class LoginController {

    private Administrador administrador;

    public LoginController(Administrador administrador) {
        this.administrador = administrador;
    }

    // Constructor por defecto que crea el administrador
    public LoginController() {
        this.administrador = new Administrador("admin", "123456");
        inicializarDatosPrueba();
    }

    private void inicializarDatosPrueba() {
        // Crear algunos empleados y clientes de prueba
        administrador.agregarEmpleado(new Empleado(1, "Ana Martinez", "Cajero"));
        administrador.agregarEmpleado(new Empleado(2, "Luis Garcia", "Supervisor"));

        // Crear cuentas y clientes de prueba
        CuentaAhorro cuenta1 = new CuentaAhorro(1001, 5000.0);
        CuentaCorriente cuenta2 = new CuentaCorriente(1002, 3000.0, 1000.0);
        CuentaEmpresarial cuenta3 = new CuentaEmpresarial(1003, 10000.0);

        Cliente cliente1 = new Cliente(101, "Sofia Hernandez", cuenta1);
        Cliente cliente2 = new Cliente(102, "Roberto Silva", cuenta2);
        Cliente cliente3 = new Cliente(103, "Empresa XYZ", cuenta3);

        administrador.agregarCliente(cliente1);
        administrador.agregarCliente(cliente2);
        administrador.agregarCliente(cliente3);
    }

    // Método principal de autenticación con credenciales
    public boolean autenticarUsuario(String username, String password, String rolSeleccionado) {
        // Validar administrador
        if (rolSeleccionado.equals("Administrador")) {
            return username.equals("admin") && password.equals("123456");
        }

        // Validar cajeros
        if (rolSeleccionado.equals("Cajero")) {
            return (username.equals("cajero1") && password.equals("pass123")) ||
                    (username.equals("cajero2") && password.equals("pass456"));
        }

        // Validar clientes
        if (rolSeleccionado.equals("Cliente")) {
            return (username.equals("sofia123") && password.equals("cliente1")) ||
                    (username.equals("roberto456") && password.equals("cliente2")) ||
                    (username.equals("empresa789") && password.equals("cliente3"));
        }

        return false;
    }

    // Métodos de validación
    public boolean validarCredenciales(String username, String password, String rolSeleccionado) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        if (password == null || password.trim().isEmpty()) {
            return false;
        }
        if (rolSeleccionado == null || rolSeleccionado.trim().isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean existeUsuario(String username) {
        return username.equals("admin") ||
                username.equals("cajero1") || username.equals("cajero2") ||
                username.equals("sofia123") || username.equals("roberto456") || username.equals("empresa789");
    }

    // Método para obtener el nombre del usuario autenticado
    public String obtenerNombreUsuario(String username) {
        switch (username) {
            case "admin":
                return "Carlos Rodriguez";
            case "cajero1":
                return "Ana Martinez";
            case "cajero2":
                return "Luis Garcia";
            case "sofia123":
                return "Sofia Hernandez";
            case "roberto456":
                return "Roberto Silva";
            case "empresa789":
                return "Empresa XYZ";
            default:
                return "Usuario";
        }
    }

    // Método para obtener todos los roles disponibles
    public String[] obtenerRolesDisponibles() {
        return new String[]{"Administrador", "Cajero", "Cliente"};
    }

    // Método para validar contraseña específica
    public boolean validarPassword(String username, String password) {
        switch (username) {
            case "admin":
                return password.equals("123456");
            case "cajero1":
                return password.equals("pass123");
            case "cajero2":
                return password.equals("pass456");
            case "sofia123":
                return password.equals("cliente1");
            case "roberto456":
                return password.equals("cliente2");
            case "empresa789":
                return password.equals("cliente3");
            default:
                return false;
        }
    }

    // Método para obtener el rol correcto del usuario
    public String obtenerRolCorrector(String username) {
        switch (username) {
            case "admin":
                return "Administrador";
            case "cajero1":
            case "cajero2":
                return "Cajero";
            case "sofia123":
            case "roberto456":
            case "empresa789":
                return "Cliente";
            default:
                return null;
        }
    }

    // Método para validar tipo de error en login
    public String obtenerMensajeError(String username, String password, String rolSeleccionado) {
        if (!validarCredenciales(username, password, rolSeleccionado)) {
            return "Complete todos los campos";
        }

        if (!existeUsuario(username)) {
            return "Usuario no encontrado";
        }

        if (!validarPassword(username, password)) {
            return "Contraseña incorrecta";
        }

        String rolCorrecto = obtenerRolCorrector(username);
        if (rolCorrecto != null && !rolCorrecto.equals(rolSeleccionado)) {
            return "El rol seleccionado no coincide con su cuenta";
        }

        return "Error desconocido";
    }

    // Método para obtener la ruta FXML según el rol
    public String obtenerRutaFXML(String rol) {
        switch (rol) {
            case "Administrador":
                return "/org/uniquindio/edu/co/poo/bancouq/administrador.fxml";
            case "Cajero":
                return "/org/uniquindio/edu/co/poo/bancouq/cajero.fxml";
            case "Cliente":
                return "/org/uniquindio/edu/co/poo/bancouq/transaccion-view.fxml";
            default:
                return null;
        }
    }

    // Método para obtener el título de la ventana según el rol
    public String obtenerTituloVentana(String rol) {
        switch (rol) {
            case "Administrador":
                return "BANCO UQ - Panel de Administrador";
            case "Cajero":
                return "BANCO UQ - Panel de Cajero";
            case "Cliente":
                return "BANCO UQ - Panel de Cliente";
            default:
                return "BANCO UQ";
        }
    }

    // Método para obtener información de usuarios de prueba
    public String obtenerUsuariosPrueba() {
        StringBuilder usuarios = new StringBuilder();
        usuarios.append("═══ USUARIOS DE PRUEBA ═══\n\n");
        usuarios.append("👨‍💼 ADMINISTRADOR:\n");
        usuarios.append("Usuario: admin | Contraseña: 123456\n\n");
        usuarios.append("👨‍💻 CAJEROS:\n");
        usuarios.append("Usuario: cajero1 | Contraseña: pass123\n");
        usuarios.append("Usuario: cajero2 | Contraseña: pass456\n\n");
        usuarios.append("👤 CLIENTES:\n");
        usuarios.append("Usuario: sofia123 | Contraseña: cliente1\n");
        usuarios.append("Usuario: roberto456 | Contraseña: cliente2\n");
        usuarios.append("Usuario: empresa789 | Contraseña: cliente3\n\n");
        usuarios.append("💡 Seleccione el rol correspondiente en el ComboBox");

        return usuarios.toString();
    }

    // Métodos para acceder al administrador (si se necesita)
    public Administrador getAdministrador() {
        return administrador;
    }

    // Método para crear un empleado
    public boolean crearEmpleado(Empleado empleado) {
        administrador.agregarEmpleado(empleado);
        return true;
    }

    // Método para crear un cliente
    public boolean crearCliente(Cliente cliente) {
        administrador.agregarCliente(cliente);
        return true;
    }
    /**
     * Método para gestionar la creación de una nueva cuenta
     */
    private void crearCuenta() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/uniquindio/edu/co/poo/bancouq/registro.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("BANCO UQ - Registro de Usuario");
            stage.setScene(new Scene(root));
            stage.setMaximized(true);
            stage.show();

            // Cerrar ventana de login (opcional)
            // Stage loginStage = (Stage) crearCuentaLabel.getScene().getWindow();
            // loginStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la interfaz de registro: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}