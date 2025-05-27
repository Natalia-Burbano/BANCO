package org.uniquindio.edu.co.poo.bancouq.viewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.io.IOException;


public class TransaccionViewController {
    @FXML
    private TextField cuentaOrigenField;
    @FXML
    private TextField cuentaDestinoField;
    @FXML
    private TextField montoField;
    @FXML
    private ComboBox<String> tipoTransaccionCombo;
    @FXML
    private BorderPane contenidoPane;

    @FXML
    public void volver(ActionEvent event) {
        try {
            ((Node) event.getSource()).getScene().setRoot(new FXMLLoader(getClass().getResource("/org/uniquindio/edu/co/poo/bancouq/main-view.fxml")).load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void realizarTransaccion(ActionEvent event) {
        // 1. VALIDAR QUE HAYA SELECCIONADO TIPO DE TRANSACCIÓN
        if (tipoTransaccionCombo.getValue() == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Error");
            alerta.setHeaderText(null);
            alerta.setContentText("Debe seleccionar un tipo de transacción");
            alerta.showAndWait();
            return;
        }

        // 2. VALIDAR QUE HAYA ESCRITO CUENTA ORIGEN
        if (cuentaOrigenField.getText().isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Error");
            alerta.setHeaderText(null);
            alerta.setContentText("Debe escribir el número de cuenta origen");
            alerta.showAndWait();
            return;
        }

        // 3. VALIDAR QUE HAYA ESCRITO EL MONTO
        if (montoField.getText().isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Error");
            alerta.setHeaderText(null);
            alerta.setContentText("Debe escribir el monto");
            alerta.showAndWait();
            return;
        }

        // 4. VALIDAR QUE EL MONTO SEA UN NÚMERO
        double monto;
        try {
            monto = Double.parseDouble(montoField.getText());
        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Error");
            alerta.setHeaderText(null);
            alerta.setContentText("El monto debe ser un número");
            alerta.showAndWait();
            return;
        }

        // 5. VALIDAR QUE EL MONTO SEA MAYOR A CERO
        if (monto <= 0) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Error");
            alerta.setHeaderText(null);
            alerta.setContentText("El monto debe ser mayor a cero");
            alerta.showAndWait();
            return;
        }

        // 6. SI ES TRANSFERENCIA, VALIDAR CUENTA DESTINO
        String tipo = tipoTransaccionCombo.getValue();
        if (tipo.equals("TRANSFERENCIA")) {
            if (cuentaDestinoField.getText().isEmpty()) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Error");
                alerta.setHeaderText(null);
                alerta.setContentText("Para transferencia debe escribir la cuenta destino");
                alerta.showAndWait();
                return;
            }

            // Validar que cuenta origen y destino no sean iguales
            if (cuentaOrigenField.getText().equals(cuentaDestinoField.getText())) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Error");
                alerta.setHeaderText(null);
                alerta.setContentText("La cuenta origen y destino no pueden ser iguales");
                alerta.showAndWait();
                return;
            }
        }

        // 7. SI TODO ESTÁ BIEN, MOSTRAR MENSAJE DE ÉXITO
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Éxito");
        alerta.setHeaderText(null);
        alerta.setContentText("Transacción realizada correctamente");
        alerta.showAndWait();

        // 8. LIMPIAR LOS CAMPOS
        cuentaOrigenField.setText("");
        cuentaDestinoField.setText("");
        montoField.setText("");
        tipoTransaccionCombo.setValue(null);

        System.out.println("Transacción realizada: " + tipo + " de " + monto);
    }
}