package viewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class TransaccionController {

    @FXML
    private TextField cuentaDestinoField;
    @FXML
    private TextField cuentaOrigenField;
    @FXML
    private TextField montoField;
    @FXML
    private ComboBox<String> tipoTransaccionCombo;

    // Cuando hace clic en "Realizar Transacción"
    @FXML
    void realizarTransaccion(ActionEvent event) {
        String cuentaOrigen = cuentaOrigenField.getText();
        String cuentaDestino = cuentaDestinoField.getText();
        String montoTexto = montoField.getText();
        String tipoTransaccion = tipoTransaccionCombo.getValue();

        // Verificar que todos los campos estén llenos
        if (cuentaOrigen.isEmpty() || montoTexto.isEmpty() || tipoTransaccion == null) {
            mostrarError("Complete todos los campos obligatorios");
            return;
        }

        // Verificar que el monto sea un número
        double monto;
        try {
            monto = Double.parseDouble(montoTexto);
            if (monto <= 0) {
                mostrarError("El monto debe ser mayor a 0");
                return;
            }
        } catch (NumberFormatException e) {
            mostrarError("El monto debe ser un número válido");
            return;
        }

        // Verificar según el tipo de transacción
        if (tipoTransaccion.equals("Transferencia")) {
            if (cuentaDestino.isEmpty()) {
                mostrarError("Para transferencias debe ingresar cuenta destino");
                return;
            }
            if (cuentaOrigen.equals(cuentaDestino)) {
                mostrarError("La cuenta origen y destino no pueden ser iguales");
                return;
            }
        }

        // Si todo está bien, realizar la transacción
        String mensaje = "Transacción realizada exitosamente:\n";
        mensaje += "Tipo: " + tipoTransaccion + "\n";
        mensaje += "Cuenta Origen: " + cuentaOrigen + "\n";
        if (!cuentaDestino.isEmpty()) {
            mensaje += "Cuenta Destino: " + cuentaDestino + "\n";
        }
        mensaje += "Monto: $" + monto;

        mostrarMensaje(mensaje);
        limpiarCampos();
    }

    // Cuando hace clic en "Volver al Menú Principal"
    @FXML
    void volver(ActionEvent event) {
        mostrarMensaje("Volviendo al menú principal...");
        // Aquí cerrarías esta ventana o abres el menú principal
    }

    // Limpiar todos los campos
    private void limpiarCampos() {
        cuentaOrigenField.clear();
        cuentaDestinoField.clear();
        montoField.clear();
        tipoTransaccionCombo.setValue(null);
    }

    // Mostrar mensaje de éxito
    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Mostrar mensaje de error
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}