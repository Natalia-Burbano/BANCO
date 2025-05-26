package org.uniquindio.edu.co.poo.bancouq.viewController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import org.uniquindio.edu.co.poo.bancouq.model.*;

import java.io.IOException;

public class RegistroViewController {

    @FXML
    private TextField txtNombres;
    @FXML
    private TextField txtApellidos;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtIdentificacion;
    @FXML
    private ComboBox<String> comboTipoUsuario;
    @FXML
    private Button buttonVolver;

    private Administrador administrador;

    @FXML
    public void initialize() {
        // Obtener el administrador principal
        administrador = obtenerAdministradorPrincipal();

        // Inicializar el ComboBox con los tipos de usuario
        inicializarComboBox();

        // Configurar validaciones en tiempo real
        configurarValidaciones();
    }

    // Método para obtener el administrador
    private Administrador obtenerAdministradorPrincipal() {
        try {
            return org.uniquindio.edu.co.poo.bancouq.viewController.LoginViewController.getAdministrador();
        } catch (Exception e) {
            Administrador admin = new Administrador("admin", "admin123");
            admin.cargarTodo();
            return admin;
        }
    }

    private void inicializarComboBox() {
        // Limpiar items existentes
        comboTipoUsuario.getItems().clear();

        // Agregar los valores para empleados
        comboTipoUsuario.getItems().addAll("Cajero", "Supervisor", "Gerente", "Administrador");

        // Configurar prompt text
        comboTipoUsuario.setPromptText("Seleccione tipo de empleado");
        comboTipoUsuario.setEditable(false);
    }

    private void configurarValidaciones() {
        // Validación para números telefónicos (solo números)
        txtTelefono.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtTelefono.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Validación para identificación (solo números)
        txtIdentificacion.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtIdentificacion.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Validación para nombres (solo letras y espacios)
        txtNombres.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*")) {
                txtNombres.setText(oldValue);
            }
        });

        // Validación para apellidos (solo letras y espacios)
        txtApellidos.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*")) {
                txtApellidos.setText(oldValue);
            }
        });
    }

    @FXML
    private void registrarCuenta(ActionEvent event) {
        if (validarCampos()) {
            try {
                // Obtener datos del formulario
                String nombres = txtNombres.getText().trim();
                String apellidos = txtApellidos.getText().trim();
                String telefono = txtTelefono.getText().trim();
                String identificacion = txtIdentificacion.getText().trim();
                String tipoUsuario = comboTipoUsuario.getValue();

                // Crear nombre completo
                String nombreCompleto = nombres + " " + apellidos;

                // Convertir identificación a ID
                int id = Integer.parseInt(identificacion);

                // Verificar que no exista empleado con ese ID
                if (administrador.buscarEmpleado(id) != null) {
                    mostrarMensajeError("Ya existe un empleado con esa identificación");
                    return;
                }

                // Crear nuevo empleado
                Empleado nuevoEmpleado = new Empleado(id, nombreCompleto, tipoUsuario);

                // Agregar al administrador (se guarda automáticamente)
                administrador.agregarEmpleado(nuevoEmpleado);

                // Generar credenciales
                String[] partes = nombreCompleto.split(" ");
                String username = partes[0].toLowerCase() + id;
                String password = tipoUsuario.equals("Administrador") ? "admin" + id : "cajero" + id;

                // Mostrar mensaje de éxito con credenciales
                String mensaje = "Empleado registrado exitosamente:\n\n" +
                        "ID: " + id + "\n" +
                        "Nombre: " + nombreCompleto + "\n" +
                        "Puesto: " + tipoUsuario + "\n" +
                        "Teléfono: " + telefono + "\n\n" +
                        "CREDENCIALES DE ACCESO:\n" +
                        "Usuario: " + username + "\n" +
                        "Contraseña: " + password + "\n" +
                        "Rol: " + (tipoUsuario.equals("Administrador") ? "Administrador" : "Cajero");

                mostrarMensajeExito(mensaje);
                limpiarFormulario();

            } catch (NumberFormatException e) {
                mostrarMensajeError("La identificación debe ser solo números");
            } catch (Exception e) {
                mostrarMensajeError("Error al registrar empleado: " + e.getMessage());
            }
        }
    }

    private boolean validarCampos() {
        StringBuilder errores = new StringBuilder();

        if (txtNombres.getText().trim().isEmpty()) {
            errores.append("• El campo Nombres es obligatorio\n");
        }

        if (txtApellidos.getText().trim().isEmpty()) {
            errores.append("• El campo Apellidos es obligatorio\n");
        }

        if (txtTelefono.getText().trim().isEmpty()) {
            errores.append("• El campo Número Telefónico es obligatorio\n");
        } else if (txtTelefono.getText().trim().length() < 7) {
            errores.append("• El número telefónico debe tener al menos 7 dígitos\n");
        }

        if (txtIdentificacion.getText().trim().isEmpty()) {
            errores.append("• El campo Identificación es obligatorio\n");
        } else if (txtIdentificacion.getText().trim().length() < 6) {
            errores.append("• La identificación debe tener al menos 6 dígitos\n");
        }

        if (comboTipoUsuario.getValue() == null) {
            errores.append("• Debe seleccionar un tipo de empleado\n");
        }

        if (errores.length() > 0) {
            mostrarMensajeError("Por favor corrija los siguientes errores:\n\n" + errores.toString());
            return false;
        }

        return true;
    }

    private void limpiarFormulario() {
        txtNombres.clear();
        txtApellidos.clear();
        txtTelefono.clear();
        txtIdentificacion.clear();
        comboTipoUsuario.setValue(null);
        txtNombres.requestFocus();
    }

    @FXML
    void volver(ActionEvent event) {
        try {
            // Cargar la interfaz de login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/uniquindio/edu/co/poo/bancouq/login.fxml"));
            Parent root = loader.load();

            // Obtener la ventana actual desde el evento
            Stage stageActual = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // Configurar y mostrar la escena
            Scene scene = new Scene(root);
            stageActual.setScene(scene);
            stageActual.setTitle("BANCO UQ - Inicio de Sesión");
            stageActual.show();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarMensajeError("Error al volver al login: " + e.getMessage());
        }
    }

    private void mostrarMensajeExito(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registro Exitoso");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarMensajeError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Registro");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}