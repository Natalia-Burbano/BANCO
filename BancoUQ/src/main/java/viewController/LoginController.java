package viewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    // Campos del FXML
    @FXML
    private TextField usuarioField;

    @FXML
    private TextField contrasenaField;

    @FXML
    private Button iniciarSesionButton;

    @FXML
    private Label crearCuentaLabel;

    // Se ejecuta cuando se abre la ventana
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Configurar prompt texts para los campos
        usuarioField.setPromptText("Ingrese su usuario");
        contrasenaField.setPromptText("Ingrese su contraseña");
    }

    // Método para iniciar sesión (cuando se hace clic en el botón)
    @FXML
    private void iniciarSesion(ActionEvent event) {
        String usuario = usuarioField.getText().trim();
        String contrasena = contrasenaField.getText().trim();

        // Verificar que los campos no estén vacíos
        if (usuario.isEmpty() || contrasena.isEmpty()) {
            mostrarError("Por favor complete todos los campos");
            return;
        }

        // Verificar credenciales según el tipo de usuario
        if (esAdministrador(usuario, contrasena)) {
            mostrarMensaje("Bienvenido Administrador", "Acceso concedido");
            abrirVentanaAdministrador();
        }
        else if (esEmpleado(usuario, contrasena)) {
            mostrarMensaje("Bienvenido Empleado", "Acceso concedido");
            abrirVentanaEmpleado();
        }
        else if (esCliente(usuario, contrasena)) {
            mostrarMensaje("Bienvenido Cliente", "Acceso concedido");
            abrirVentanaCliente();
        }
        else {
            mostrarError("Usuario o contraseña incorrectos");
            limpiarCampos();
        }
    }

    // Método para crear cuenta (cuando se hace clic en el label)
    @FXML
    private void crearCuenta(MouseEvent event) {
        mostrarMensaje("Crear Cuenta", "Función de registro en desarrollo...");
        // Aquí puedes abrir una ventana de registro
        // abrirVentanaRegistro();
    }

    // Verificar si es administrador
    private boolean esAdministrador(String usuario, String contrasena) {
        return usuario.equals("admin") && contrasena.equals("admin123");
    }

    // Verificar si es empleado
    private boolean esEmpleado(String usuario, String contrasena) {
        // Usuarios de empleados de ejemplo
        return (usuario.equals("empleado1") && contrasena.equals("emp123")) ||
                (usuario.equals("empleado2") && contrasena.equals("emp456"));
    }

    // Verificar si es cliente
    private boolean esCliente(String usuario, String contrasena) {
        // Usuarios de clientes de ejemplo
        return (usuario.equals("cliente1") && contrasena.equals("cli123")) ||
                (usuario.equals("cliente2") && contrasena.equals("cli456")) ||
                (usuario.equals("juan") && contrasena.equals("123"));
    }

    // Abrir ventana del administrador
    private void abrirVentanaAdministrador() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bancouq/administrador.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Panel de Administrador - Banco UQ");
            stage.setScene(new Scene(root));
            stage.show();

            // Cerrar ventana actual
            cerrarVentanaActual();

        } catch (IOException e) {
            mostrarError("Error al abrir panel de administrador: " + e.getMessage());
        }
    }

    // Abrir ventana del empleado
    private void abrirVentanaEmpleado() {
        try {
            // Aquí cargarías el FXML del empleado cuando lo tengas
            mostrarMensaje("Empleado", "Panel de empleado en desarrollo...");
            // FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bancouq/empleado.fxml"));
            // Parent root = loader.load();
            // Stage stage = new Stage();
            // stage.setTitle("Panel de Empleado - Banco UQ");
            // stage.setScene(new Scene(root));
            // stage.show();
            // cerrarVentanaActual();

        } catch (Exception e) {
            mostrarError("Error al abrir panel de empleado: " + e.getMessage());
        }
    }

    // Abrir ventana del cliente
    private void abrirVentanaCliente() {
        try {
            // Aquí cargarías el FXML del cliente cuando lo tengas
            mostrarMensaje("Cliente", "Panel de cliente en desarrollo...");
            // FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bancouq/cliente.fxml"));
            // Parent root = loader.load();
            // Stage stage = new Stage();
            // stage.setTitle("Banco UQ - Mi Cuenta");
            // stage.setScene(new Scene(root));
            // stage.show();
            // cerrarVentanaActual();

        } catch (Exception e) {
            mostrarError("Error al abrir panel de cliente: " + e.getMessage());
        }
    }

    // Cerrar la ventana actual
    private void cerrarVentanaActual() {
        Stage stage = (Stage) usuarioField.getScene().getWindow();
        stage.close();
    }

    // Limpiar los campos de texto
    private void limpiarCampos() {
        usuarioField.clear();
        contrasenaField.clear();
    }

    // Mostrar mensaje de información
    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Mostrar mensaje de error
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Login");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}