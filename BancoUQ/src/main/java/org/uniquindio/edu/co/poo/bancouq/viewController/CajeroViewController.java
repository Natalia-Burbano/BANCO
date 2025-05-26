package org.uniquindio.edu.co.poo.bancouq.viewController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.uniquindio.edu.co.poo.bancouq.model.Administrador;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CajeroViewController implements Initializable {

    private Administrador administrador;

    // Controles de la interfaz (vinculados con el FXML)
    @FXML
    private TableView<Cliente> clientesTable;
    @FXML
    private TextField destinoField;
    @FXML
    private TableColumn<Cliente, String> idColumn;
    @FXML
    private TextField idField;
    @FXML
    private TextField montoField;
    @FXML
    private TableColumn<Cliente, String> nombreColumn;
    @FXML
    private TextField nombreField;
    @FXML
    private TableColumn<Cliente, String> tipoCuentaColumn;
    @FXML
    private ComboBox<String> tipoCuentaCombo;

    // Lista para almacenar los clientes
    private ObservableList<Cliente> listaClientes;
    private List<String> historialTransacciones;

    // Este método se ejecuta automáticamente al cargar la ventana
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Inicializar la lista de clientes
        listaClientes = FXCollections.observableArrayList();
        historialTransacciones = new ArrayList<>();

        // Configurar las opciones del ComboBox
        tipoCuentaCombo.setItems(FXCollections.observableArrayList(
                "Ahorros", "Corriente", "Nómina"
        ));

        // Configurar las columnas de la tabla
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tipoCuentaColumn.setCellValueFactory(new PropertyValueFactory<>("tipoCuenta"));

        // Conectar la lista con la tabla
        clientesTable.setItems(listaClientes);
    }

    // Método para registrar un nuevo cliente
    @FXML
    void registrarCliente(ActionEvent event) {
        try {
            // Obtener los datos de los campos
            String id = idField.getText().trim();
            String nombre = nombreField.getText().trim();
            String tipoCuenta = tipoCuentaCombo.getValue();

            // Validar que los campos no estén vacíos
            if (id.isEmpty() || nombre.isEmpty() || tipoCuenta == null) {
                mostrarAlerta("Error", "Todos los campos son obligatorios");
                return;
            }

            // Verificar que el ID no esté repetido
            for (Cliente cliente : listaClientes) {
                if (cliente.getId().equals(id)) {
                    mostrarAlerta("Error", "Ya existe un cliente con ese ID");
                    return;
                }
            }

            // Crear nuevo cliente y agregarlo a la lista
            Cliente nuevoCliente = new Cliente(id, nombre, tipoCuenta, 0.0);
            listaClientes.add(nuevoCliente);

            // Limpiar los campos
            limpiarCampos();

            mostrarAlerta("Éxito", "Cliente registrado correctamente");

        } catch (Exception e) {
            mostrarAlerta("Error", "Error al registrar cliente: " + e.getMessage());
        }
    }

    // Método para depositar dinero
    @FXML
    void depositar(ActionEvent event) {
        try {
            Cliente clienteSeleccionado = clientesTable.getSelectionModel().getSelectedItem();
            if (clienteSeleccionado == null) {
                mostrarAlerta("Error", "Seleccione un cliente de la tabla");
                return;
            }

            String montoTexto = montoField.getText().trim();
            if (montoTexto.isEmpty()) {
                mostrarAlerta("Error", "Ingrese un monto");
                return;
            }

            double monto = Double.parseDouble(montoTexto);
            if (monto <= 0) {
                mostrarAlerta("Error", "El monto debe ser mayor a 0");
                return;
            }

            // Realizar el depósito
            clienteSeleccionado.setSaldo(clienteSeleccionado.getSaldo() + monto);

            // Registrar la transacción
            String transaccion = "DEPÓSITO - Cliente: " + clienteSeleccionado.getId() +
                    " - Monto: $" + monto + " - Nuevo saldo: $" + clienteSeleccionado.getSaldo();
            historialTransacciones.add(transaccion);

            // Actualizar la tabla
            clientesTable.refresh();
            montoField.clear();

            mostrarAlerta("Éxito", "Depósito realizado. Nuevo saldo: $" + clienteSeleccionado.getSaldo());

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Ingrese un monto válido");
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al realizar depósito: " + e.getMessage());
        }
    }

    // Método para retirar dinero
    @FXML
    void retirar(ActionEvent event) {
        try {
            Cliente clienteSeleccionado = clientesTable.getSelectionModel().getSelectedItem();
            if (clienteSeleccionado == null) {
                mostrarAlerta("Error", "Seleccione un cliente de la tabla");
                return;
            }

            String montoTexto = montoField.getText().trim();
            if (montoTexto.isEmpty()) {
                mostrarAlerta("Error", "Ingrese un monto");
                return;
            }

            double monto = Double.parseDouble(montoTexto);
            if (monto <= 0) {
                mostrarAlerta("Error", "El monto debe ser mayor a 0");
                return;
            }

            if (clienteSeleccionado.getSaldo() < monto) {
                mostrarAlerta("Error", "Saldo insuficiente. Saldo actual: $" + clienteSeleccionado.getSaldo());
                return;
            }

            // Realizar el retiro
            clienteSeleccionado.setSaldo(clienteSeleccionado.getSaldo() - monto);

            // Registrar la transacción
            String transaccion = "RETIRO - Cliente: " + clienteSeleccionado.getId() +
                    " - Monto: $" + monto + " - Nuevo saldo: $" + clienteSeleccionado.getSaldo();
            historialTransacciones.add(transaccion);

            // Actualizar la tabla
            clientesTable.refresh();
            montoField.clear();

            mostrarAlerta("Éxito", "Retiro realizado. Nuevo saldo: $" + clienteSeleccionado.getSaldo());

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Ingrese un monto válido");
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al realizar retiro: " + e.getMessage());
        }
    }

    // Método para consultar saldo
    @FXML
    void consultarSaldo(ActionEvent event) {
        Cliente clienteSeleccionado = clientesTable.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado == null) {
            mostrarAlerta("Error", "Seleccione un cliente de la tabla");
            return;
        }

        String mensaje = "Cliente: " + clienteSeleccionado.getNombre() + "\n" +
                "ID: " + clienteSeleccionado.getId() + "\n" +
                "Tipo de Cuenta: " + clienteSeleccionado.getTipoCuenta() + "\n" +
                "Saldo Actual: $" + clienteSeleccionado.getSaldo();

        mostrarAlerta("Consulta de Saldo", mensaje);
    }

    // Método para transferir dinero
    @FXML
    void transferir(ActionEvent event) {
        try {
            Cliente clienteOrigen = clientesTable.getSelectionModel().getSelectedItem();
            if (clienteOrigen == null) {
                mostrarAlerta("Error", "Seleccione el cliente origen de la tabla");
                return;
            }

            String idDestino = destinoField.getText().trim();
            String montoTexto = montoField.getText().trim();

            if (idDestino.isEmpty() || montoTexto.isEmpty()) {
                mostrarAlerta("Error", "Ingrese el ID destino y el monto");
                return;
            }

            double monto = Double.parseDouble(montoTexto);
            if (monto <= 0) {
                mostrarAlerta("Error", "El monto debe ser mayor a 0");
                return;
            }

            if (clienteOrigen.getSaldo() < monto) {
                mostrarAlerta("Error", "Saldo insuficiente");
                return;
            }

            // Buscar cliente destino
            Cliente clienteDestino = null;
            for (Cliente cliente : listaClientes) {
                if (cliente.getId().equals(idDestino)) {
                    clienteDestino = cliente;
                    break;
                }
            }

            if (clienteDestino == null) {
                mostrarAlerta("Error", "No se encontró cliente con ID: " + idDestino);
                return;
            }

            if (clienteOrigen.getId().equals(clienteDestino.getId())) {
                mostrarAlerta("Error", "No puede transferir a la misma cuenta");
                return;
            }

            // Realizar la transferencia
            clienteOrigen.setSaldo(clienteOrigen.getSaldo() - monto);
            clienteDestino.setSaldo(clienteDestino.getSaldo() + monto);

            // Registrar la transacción
            String transaccion = "TRANSFERENCIA - De: " + clienteOrigen.getId() +
                    " a: " + clienteDestino.getId() + " - Monto: $" + monto;
            historialTransacciones.add(transaccion);

            // Actualizar la tabla
            clientesTable.refresh();
            montoField.clear();
            destinoField.clear();

            mostrarAlerta("Éxito", "Transferencia realizada correctamente");

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Ingrese un monto válido");
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al realizar transferencia: " + e.getMessage());
        }
    }

    // Método para generar reporte de transacciones
    @FXML
    void generarReporte(ActionEvent event) {
        if (historialTransacciones.isEmpty()) {
            mostrarAlerta("Información", "No hay transacciones registradas");
            return;
        }

        StringBuilder reporte = new StringBuilder();
        reporte.append("=== REPORTE DE TRANSACCIONES ===\n\n");

        for (int i = 0; i < historialTransacciones.size(); i++) {
            reporte.append((i + 1)).append(". ").append(historialTransacciones.get(i)).append("\n");
        }

        reporte.append("\nTotal de transacciones: ").append(historialTransacciones.size());

        // Crear ventana de diálogo para mostrar el reporte
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Reporte de Transacciones");
        alert.setHeaderText("Historial completo de transacciones");

        // Hacer el área de texto expandible
        TextArea textArea = new TextArea(reporte.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);

        alert.getDialogPane().setExpandableContent(textArea);
        alert.getDialogPane().setExpanded(true);
        alert.showAndWait();
    }

    // Método para volver al menú principal
    @FXML
    void volver(ActionEvent event) {
        // Aquí puedes agregar la lógica para cerrar esta ventana
        // y volver al menú principal
        mostrarAlerta("Información", "Función para volver al menú principal");
        // Ejemplo: cerrar la ventana actual
        // Stage stage = (Stage) clientesTable.getScene().getWindow();
        // stage.close();
    }

    // Método auxiliar para mostrar alertas
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Método auxiliar para limpiar campos
    private void limpiarCampos() {
        idField.clear();
        nombreField.clear();
        tipoCuentaCombo.setValue(null);
        montoField.clear();
        destinoField.clear();
    }

    // Clase interna para representar un Cliente
    public static class Cliente {
        private String id;
        private String nombre;
        private String tipoCuenta;
        private double saldo;

        public Cliente(String id, String nombre, String tipoCuenta, double saldo) {
            this.id = id;
            this.nombre = nombre;
            this.tipoCuenta = tipoCuenta;
            this.saldo = saldo;
        }

        // Getters y Setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public String getTipoCuenta() { return tipoCuenta; }
        public void setTipoCuenta(String tipoCuenta) { this.tipoCuenta = tipoCuenta; }

        public double getSaldo() { return saldo; }
        public void setSaldo(double saldo) { this.saldo = saldo; }
    }
}