package org.uniquindio.edu.co.poo.bancouq.viewController;

import org.uniquindio.edu.co.poo.bancouq.model.*;
import org.uniquindio.edu.co.poo.bancouq.util.ArchivoUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CajeroViewController {

    @FXML
    private TextField idField;
    @FXML
    private TextField nombreField;
    @FXML
    private TextField montoField;
    @FXML
    private TextField destinoField;
    @FXML
    private ComboBox<String> tipoCuentaCombo;
    @FXML
    private TableView<Cliente> clientesTable;
    @FXML
    private TableColumn<Cliente, Integer> idColumn;
    @FXML
    private TableColumn<Cliente, String> nombreColumn;
    @FXML
    private TableColumn<Cliente, String> tipoCuentaColumn;
    @FXML
    private BorderPane contenidoPane;

    private ObservableList<Cliente> clientes;
    private static final String RUTA_ARCHIVO = "clientes.txt";

    @FXML
    public void initialize() {
        clientes = FXCollections.observableArrayList();

        idColumn.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        nombreColumn.setCellValueFactory(data -> data.getValue().nombreProperty());
        tipoCuentaColumn.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getCuenta() instanceof CuentaAhorro ? "Ahorros" :
                        data.getValue().getCuenta() instanceof CuentaCorriente ? "Corriente" :
                                "Empresarial"
        ));

        clientesTable.setItems(clientes);
        tipoCuentaCombo.setItems(FXCollections.observableArrayList("Ahorros", "Corriente", "Empresarial"));
        cargarClientes();
    }

    @FXML
    public void registrarCliente() {
        // Verificar que los campos no estén vacíos
        if (idField.getText().isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar un ID.");
            return;
        }

        if (nombreField.getText().isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar un nombre.");
            return;
        }

        if (tipoCuentaCombo.getValue() == null) {
            mostrarAlerta("Error", "Debe seleccionar un tipo de cuenta.");
            return;
        }

        try {
            int id = Integer.parseInt(idField.getText());
            String nombre = nombreField.getText();
            String tipoCuenta = tipoCuentaCombo.getValue();

            Cuenta cuenta;
            if (tipoCuenta.equals("Ahorros")) {
                cuenta = new CuentaAhorro(id, 0);
            } else if (tipoCuenta.equals("Corriente")) {
                cuenta = new CuentaCorriente(id, 0, 1000);
            } else if (tipoCuenta.equals("Empresarial")) {
                cuenta = new CuentaEmpresarial(id, 0);
            } else {
                mostrarAlerta("Error", "Tipo de cuenta no válido.");
                return;
            }

            Cliente cliente = new Cliente(id, nombre, cuenta);
            clientes.add(cliente);
            guardarClientes();

            idField.clear();
            nombreField.clear();
            tipoCuentaCombo.setValue(null);

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El ID debe ser un número.");
        }
    }

    @FXML
    public void depositar() {
        Cliente cliente = clientesTable.getSelectionModel().getSelectedItem();

        if (cliente == null) {
            mostrarAlerta("Error", "Seleccione un cliente.");
            return;
        }

        if (montoField.getText().isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar un monto.");
            return;
        }

        try {
            double monto = Double.parseDouble(montoField.getText());
            cliente.getCuenta().depositar(monto);
            guardarClientes();
            montoField.clear();
            mostrarAlerta("Éxito", "Depósito realizado correctamente.");

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El monto debe ser un número.");
        }
    }

    @FXML
    public void retirar() {
        Cliente cliente = clientesTable.getSelectionModel().getSelectedItem();

        if (cliente == null) {
            mostrarAlerta("Error", "Seleccione un cliente.");
            return;
        }

        if (montoField.getText().isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar un monto.");
            return;
        }

        try {
            double monto = Double.parseDouble(montoField.getText());

            if (cliente.getCuenta().retirar(monto)) {
                guardarClientes();
                montoField.clear();
                mostrarAlerta("Éxito", "Retiro realizado correctamente.");
            } else {
                mostrarAlerta("Error", "Saldo insuficiente.");
            }

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El monto debe ser un número.");
        }
    }

    @FXML
    public void consultarSaldo() {
        Cliente cliente = clientesTable.getSelectionModel().getSelectedItem();

        if (cliente != null) {
            double saldo = cliente.getCuenta().getSaldo();
            mostrarAlerta("Saldo", "Saldo disponible: " + saldo);
        } else {
            mostrarAlerta("Error", "Seleccione un cliente.");
        }
    }

    @FXML
    public void volver(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/uniquindio/edu/co/poo/bancouq/main-view.fxml"));
            BorderPane mainView = loader.load();
            Scene currentScene = ((Node) event.getSource()).getScene();
            currentScene.setRoot(mainView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void transferir() {
        Cliente origen = clientesTable.getSelectionModel().getSelectedItem();

        if (origen == null) {
            mostrarAlerta("Error", "Seleccione un cliente de origen.");
            return;
        }

        if (destinoField.getText().isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar el ID destino.");
            return;
        }

        if (montoField.getText().isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar un monto.");
            return;
        }

        try {
            int idDestino = Integer.parseInt(destinoField.getText());
            double monto = Double.parseDouble(montoField.getText());

            Cliente destino = null;
            for (Cliente c : clientes) {
                if (c.getId() == idDestino) {
                    destino = c;
                    break;
                }
            }

            if (destino != null) {
                if (origen.getCuenta().retirar(monto)) {
                    destino.getCuenta().depositar(monto);
                    guardarClientes();
                    mostrarAlerta("Éxito", "Transferencia realizada correctamente.");
                } else {
                    mostrarAlerta("Error", "Saldo insuficiente.");
                }
            } else {
                mostrarAlerta("Error", "Cliente destino no encontrado.");
            }

            montoField.clear();
            destinoField.clear();

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Los valores deben ser números.");
        }
    }

    @FXML
    public void generarReporte() {
        Cliente cliente = clientesTable.getSelectionModel().getSelectedItem();

        if (cliente != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Exportar Información de Cliente");

            FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("Archivo CSV (*.csv)", "*.csv");
            FileChooser.ExtensionFilter htmlFilter = new FileChooser.ExtensionFilter("Archivo HTML (*.html)", "*.html");

            fileChooser.getExtensionFilters().addAll(csvFilter, htmlFilter);
            fileChooser.setSelectedExtensionFilter(csvFilter);

            File archivo = fileChooser.showSaveDialog(null);

            if (archivo != null) {
                String extension = fileChooser.getSelectedExtensionFilter().getDescription();

                if (extension.contains("CSV")) {
                    exportarClienteCSV(archivo, cliente);
                } else if (extension.contains("HTML")) {
                    exportarClienteHTML(archivo, cliente);
                }
            }
        } else {
            mostrarAlerta("Error", "Seleccione un cliente para generar el reporte.");
        }
    }

    private void exportarClienteCSV(File archivo, Cliente cliente) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));

            LocalDateTime ahora = LocalDateTime.now();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String fechaHora = ahora.format(formato);

            writer.write("Reporte de Cliente,Generado en: " + fechaHora + "\n\n");
            writer.write("ID,Nombre,Tipo de Cuenta,Saldo\n");

            String tipoCuenta = "";
            if (cliente.getCuenta() instanceof CuentaAhorro) {
                tipoCuenta = "Ahorros";
            } else if (cliente.getCuenta() instanceof CuentaCorriente) {
                tipoCuenta = "Corriente";
            } else {
                tipoCuenta = "Empresarial";
            }

            String linea = cliente.getId() + "," + cliente.getNombre() + "," + tipoCuenta + "," + cliente.getCuenta().getSaldo();
            writer.write(linea + "\n");

            writer.close();
            mostrarAlerta("Éxito", "Reporte CSV guardado exitosamente.");

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo generar el reporte CSV: " + e.getMessage());
        }
    }

    private void exportarClienteHTML(File archivo, Cliente cliente) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));

            LocalDateTime ahora = LocalDateTime.now();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String fechaHora = ahora.format(formato);

            writer.write("<!DOCTYPE html>\n");
            writer.write("<html>\n");
            writer.write("<head>\n");
            writer.write("<title>Reporte de Cliente</title>\n");
            writer.write("<style>\n");
            writer.write("body { font-family: Arial, sans-serif; margin: 20px; }\n");
            writer.write("h1 { color: #003366; }\n");
            writer.write("table { border-collapse: collapse; width: 100%; margin-top: 20px; }\n");
            writer.write("th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }\n");
            writer.write("th { background-color: #f2f2f2; }\n");
            writer.write("tr:nth-child(even) { background-color: #f9f9f9; }\n");
            writer.write(".header { background-color: #003366; color: white; padding: 10px; }\n");
            writer.write("</style>\n");
            writer.write("</head>\n");
            writer.write("<body>\n");

            writer.write("<div class=\"header\">\n");
            writer.write("<h1>Reporte de Cliente</h1>\n");
            writer.write("<p>Generado: " + fechaHora + "</p>\n");
            writer.write("</div>\n");

            writer.write("<h2>Información de Cliente</h2>\n");
            writer.write("<table>\n");
            writer.write("<tr><th>ID</th><th>Nombre</th><th>Tipo de Cuenta</th><th>Saldo</th></tr>\n");

            String tipoCuenta = "";
            if (cliente.getCuenta() instanceof CuentaAhorro) {
                tipoCuenta = "Ahorros";
            } else if (cliente.getCuenta() instanceof CuentaCorriente) {
                tipoCuenta = "Corriente";
            } else {
                tipoCuenta = "Empresarial";
            }

            writer.write("<tr>\n");
            writer.write("<td>" + cliente.getId() + "</td>\n");
            writer.write("<td>" + cliente.getNombre() + "</td>\n");
            writer.write("<td>" + tipoCuenta + "</td>\n");
            writer.write("<td>" + cliente.getCuenta().getSaldo() + "</td>\n");
            writer.write("</tr>\n");

            writer.write("</table>\n");
            writer.write("</body>\n");
            writer.write("</html>");

            writer.close();
            mostrarAlerta("Éxito", "Reporte HTML guardado exitosamente.");

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo generar el reporte HTML: " + e.getMessage());
        }
    }

    private void guardarClientes() {
        List<String> lineas = new ArrayList<>();

        for (Cliente cliente : clientes) {
            String linea = cliente.getId() + "," + cliente.getNombre() + "," + cliente.getCuenta().getSaldo();
            lineas.add(linea);
        }

        try {
            ArchivoUtil.escribirArchivo(RUTA_ARCHIVO, lineas);
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo guardar los clientes.");
        }
    }

    private void cargarClientes() {
        try {
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

        } catch (IOException e) {
            System.out.println("No se pudo cargar el archivo de clientes: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}