package org.uniquindio.edu.co.poo.bancouq.viewController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import org.uniquindio.edu.co.poo.bancouq.model.*;
import java.io.IOException;

import static org.uniquindio.edu.co.poo.bancouq.model.TipoUsuario.validarCredenciales;


public class LoginViewController {

    @FXML
    private TextField usuarioField;
    @FXML
    private TextField contrasenaField;
    @FXML
    private ComboBox<String> comboRol;
    @FXML
    private Label crearCuentaLabel;

    private static Administrador administrador;

    /**
     * Método estático para acceder al administrador desde otras vistas
     */
    public static Administrador getAdministrador() {
        if (administrador == null) {
            administrador = new Administrador("admin", "admin123");
            administrador.cargarTodo();

            // Crear datos de ejemplo si no hay
            if (administrador.getEmpleados().size() == 0) {
                administrador.agregarEmpleado(new Empleado(1, "Ana Martinez", "Cajero"));
                administrador.agregarEmpleado(new Empleado(2, "Luis Garcia", "Supervisor"));
            }

            if (administrador.getClientes().size() == 0) {
                CuentaAhorro cuenta1 = new CuentaAhorro(1001, 5000.0);
                CuentaCorriente cuenta2 = new CuentaCorriente(1002, 3000.0, 1000.0);
                administrador.agregarCliente(new Cliente(101, "Sofia Hernandez", cuenta1));
                administrador.agregarCliente(new Cliente(102, "Roberto Silva", cuenta2));
            }
        }
        return administrador;
    }

    @FXML
    void iniciarSesion(ActionEvent event) {
        // Validar que todos los campos estén llenos
        if (usuarioField.getText().trim().isEmpty()) {
            mostrarMensajeError("Por favor ingrese su usuario");
            return;
        }

        if (contrasenaField.getText().trim().isEmpty()) {
            mostrarMensajeError("Por favor ingrese su contraseña");
            return;
        }

        if (comboRol.getValue() == null) {
            mostrarMensajeError("Por favor seleccione su rol");
            return;
        }

        String usuario = usuarioField.getText().trim();
        String contrasena = contrasenaField.getText().trim();
        String rol = comboRol.getValue();

        // Validar credenciales según el rol seleccionado
        if (validarCredenciales(usuario, contrasena, rol)) {
            mostrarMensajeExito("Inicio de sesión exitoso como " + rol);
            navegarSegunRol(event, rol);
        } else {
            mostrarMensajeError("Credenciales incorrectas para el rol " + rol);
        }
    }
    private boolean validarCredenciales(String usuario, String contrasena, String rol) {
        switch (rol) {
            case "Administrador":
                // Credenciales de administrador
                return (usuario.equals("admin") && contrasena.equals("admin123")) ||
                        (usuario.equals("admin1") && contrasena.equals("admin456"));

            case "Cajero":
                // Credenciales de cajero
                return (usuario.equals("cajero1") && contrasena.equals("pass123")) ||
                        (usuario.equals("cajero") && contrasena.equals("cajero123"));

            case "Cliente":
                // Credenciales de cliente
                return (usuario.equals("sofia123") && contrasena.equals("cliente1")) ||
                        (usuario.equals("cliente") && contrasena.equals("cliente123"));

            default:
                return false;
        }
    }

    private void mostrarOpcionCrearCuenta(String rol) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Usuario no encontrado");
        alert.setHeaderText("Credenciales incorrectas");

        String mensaje = "No se encontró un " + rol.toLowerCase() + " con esas credenciales.\n\n";

        if (rol.equals("Cliente")) {
            mensaje += "Para crear una cuenta de cliente, debe dirigirse a una sucursal del banco.";
            alert.setContentText(mensaje);
            alert.showAndWait();
        } else {
            mensaje += "¿Desea registrar un nuevo empleado?";
            alert.setContentText(mensaje);

            if (alert.showAndWait().get().getText().equals("Aceptar")) {
                irARegistro(null);
            }
        }
    }



    /**
     * Método para manejar el clic en el label "Crear Cuenta"
     * ESTE ES EL MÉTODO QUE HACE QUE EL LABEL FUNCIONE COMO BOTÓN
     */
    @FXML
    private void irARegistro(MouseEvent event) {
        try {
            // Cargar la interfaz de registro
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/uniquindio/edu/co/poo/bancouq/registro.fxml"));
            Parent root = loader.load();

            // Obtener la ventana actual desde el label
            Stage stageActual = (Stage) crearCuentaLabel.getScene().getWindow();

            // Configurar y mostrar la escena
            Scene scene = new Scene(root);
            stageActual.setScene(scene);
            stageActual.setTitle("BANCO UQ - Registro de Empleados");
            stageActual.show();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarMensajeError("Error al cargar la vista de registro: " + e.getMessage());
        }
    }

    private void navegarSegunRol(ActionEvent event, String rol) {
        try {
            String vistaDestino;
            String tituloVentana;

            // Determinar la vista según el rol
            switch (rol) {
                case "Administrador":
                    vistaDestino = "/org/uniquindio/edu/co/poo/bancouq/administrador.fxml";
                    tituloVentana = "BANCO UQ - Panel de Administrador";
                    break;
                case "Cajero":
                    vistaDestino = "/org/uniquindio/edu/co/poo/bancouq/cajero.fxml";
                    tituloVentana = "BANCO UQ - Panel de Cajero";
                    break;
                case "Cliente":
                    vistaDestino = "/org/uniquindio/edu/co/poo/bancouq/main-view.fxml";
                    tituloVentana = "BANCO UQ - Panel de Cliente";
                    break;
                default:
                    mostrarMensajeError("Rol no válido");
                    return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(vistaDestino));
            Parent root = loader.load();

            Stage stageActual = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stageActual.setScene(scene);
            stageActual.setTitle(tituloVentana);
            stageActual.show();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarMensajeError("Error al cargar la vista: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensajeError("Error inesperado: " + e.getMessage());
        }
    }

    @FXML
    private void mostrarEjemplos(ActionEvent event) {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Credenciales de Ejemplo");
        info.setHeaderText("Usuarios de prueba");
        info.setContentText("Admin: admin / admin123\n" +
                "Cajero: cajero1 / pass123\n" +
                "Cliente: sofia123 / cliente1");
        info.showAndWait();
    }

    private void mostrarMensajeExito(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Inicio de Sesión");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarMensajeError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}