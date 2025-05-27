package org.uniquindio.edu.co.poo.bancouq.viewController;

import org.uniquindio.edu.co.poo.bancouq.model.Empleado;
import org.uniquindio.edu.co.poo.bancouq.util.ArchivoUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdministradorViewController {

    @FXML
    private TextField usuarioField;
    @FXML
    private TextField contrasenaField;
    @FXML
    private TextField idField;
    @FXML
    private TextField nombreField;
    @FXML
    private ComboBox<String> rolCombo;
    @FXML
    private TableView<Empleado> empleadosTable;
    @FXML
    private TableColumn<Empleado, Integer> idColumn;
    @FXML
    private TableColumn<Empleado, String> nombreColumn;
    @FXML
    private TableColumn<Empleado, String> rolColumn;
    @FXML
    private TableView<String> transaccionesTable;
    @FXML
    private BorderPane contenidoPane;
    @FXML
    private TableColumn<String, String> tipoColumn;
    @FXML
    private TableColumn<String, String> montoColumn;
    @FXML
    private TableColumn<String, String> fechaColumn;

    private ObservableList<Empleado> empleados;
    private static final String RUTA_ARCHIVO = "empleados.txt";

    @FXML
    public void initialize() {
        empleados = FXCollections.observableArrayList();

        idColumn.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        nombreColumn.setCellValueFactory(data -> data.getValue().nombreProperty());
        rolColumn.setCellValueFactory(data -> data.getValue().puestoProperty());

        empleadosTable.setItems(empleados);

        rolCombo.setItems(FXCollections.observableArrayList("Administrador", "Cajero"));

        cargarEmpleados();
    }

    @FXML
    private void autenticar() {
        String usuario = usuarioField.getText();
        String contrasena = contrasenaField.getText();

        if ("admin".equals(usuario) && "1234".equals(contrasena)) {
            mostrarAlerta("Éxito", "Autenticación exitosa.");
        } else {
            mostrarAlerta("Error", "Usuario o contraseña incorrectos.");
        }
    }

    @FXML
    private void registrarEmpleado() {
        try {
            int id = Integer.parseInt(idField.getText());
            String nombre = nombreField.getText();
            String rol = rolCombo.getValue();

            Empleado empleado = new Empleado(id, nombre, rol);
            empleados.add(empleado);
            guardarEmpleados();

            idField.clear();
            nombreField.clear();
            rolCombo.setValue(null);

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El ID debe ser un número.");
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
    private void modificarEmpleado() {
        Empleado empleadoSeleccionado = empleadosTable.getSelectionModel().getSelectedItem();

        if (empleadoSeleccionado == null) {
            mostrarAlerta("Error", "Debe seleccionar un empleado para modificar.");
            return;
        }

        String nuevoNombre = nombreField.getText();
        String nuevoRol = rolCombo.getValue();

        if (nuevoNombre.isEmpty() || nuevoRol == null) {
            mostrarAlerta("Error", "Todos los campos son obligatorios.");
            return;
        }

        empleadoSeleccionado.setNombre(nuevoNombre);
        empleadoSeleccionado.setPuesto(nuevoRol);
        empleadosTable.refresh();
        guardarEmpleados();
    }

    @FXML
    private void eliminarEmpleado() {
        Empleado empleadoSeleccionado = empleadosTable.getSelectionModel().getSelectedItem();

        if (empleadoSeleccionado != null) {
            empleados.remove(empleadoSeleccionado);
            guardarEmpleados();
        } else {
            mostrarAlerta("Error", "Debe seleccionar un empleado para eliminar.");
        }
    }

    @FXML
    private void generarReporteTransacciones() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exportar Transacciones");

        FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("Archivo CSV (*.csv)", "*.csv");
        FileChooser.ExtensionFilter htmlFilter = new FileChooser.ExtensionFilter("Archivo HTML (*.html)", "*.html");

        fileChooser.getExtensionFilters().addAll(csvFilter, htmlFilter);
        fileChooser.setSelectedExtensionFilter(csvFilter);

        File archivo = fileChooser.showSaveDialog(null);

        if (archivo != null) {
            String extension = fileChooser.getSelectedExtensionFilter().getDescription();

            if (extension.contains("CSV")) {
                exportarCSV(archivo);
            } else if (extension.contains("HTML")) {
                exportarHTML(archivo);
            }
        }
    }

    private void exportarCSV(File archivo) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));

            writer.write("Tipo,Monto,Fecha\n");

            for (String transaccion : transaccionesTable.getItems()) {
                writer.write(transaccion + "\n");
            }

            writer.close();
            mostrarAlerta("Éxito", "Reporte CSV guardado exitosamente.");

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo generar el reporte CSV: " + e.getMessage());
        }
    }

    private void exportarHTML(File archivo) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));

            writer.write("<!DOCTYPE html>\n");
            writer.write("<html>\n");
            writer.write("<head>\n");
            writer.write("<title>Reporte de Transacciones</title>\n");
            writer.write("<style>\n");
            writer.write("table { border-collapse: collapse; width: 100%; }\n");
            writer.write("th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }\n");
            writer.write("th { background-color: #f2f2f2; }\n");
            writer.write("tr:nth-child(even) { background-color: #f9f9f9; }\n");
            writer.write("</style>\n");
            writer.write("</head>\n");
            writer.write("<body>\n");
            writer.write("<h1>Reporte de Transacciones</h1>\n");
            writer.write("<table>\n");

            writer.write("<tr>\n");
            writer.write("<th>Tipo</th>\n");
            writer.write("<th>Monto</th>\n");
            writer.write("<th>Fecha</th>\n");
            writer.write("</tr>\n");

            for (String transaccion : transaccionesTable.getItems()) {
                String[] partes = transaccion.split(",");
                writer.write("<tr>\n");
                if (partes.length >= 1) {
                    writer.write("<td>" + partes[0] + "</td>\n");
                }
                if (partes.length >= 2) {
                    writer.write("<td>" + partes[1] + "</td>\n");
                }
                if (partes.length >= 3) {
                    writer.write("<td>" + partes[2] + "</td>\n");
                }
                writer.write("</tr>\n");
            }

            writer.write("</table>\n");
            writer.write("</body>\n");
            writer.write("</html>");

            writer.close();
            mostrarAlerta("Éxito", "Reporte HTML guardado exitosamente.");

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo generar el reporte HTML: " + e.getMessage());
        }
    }

    private void guardarEmpleados() {
        List<String> lineas = new ArrayList<>();

        for (Empleado empleado : empleados) {
            String linea = empleado.getId() + "," + empleado.getNombre() + "," + empleado.getPuesto();
            lineas.add(linea);
        }

        try {
            ArchivoUtil.escribirArchivo(RUTA_ARCHIVO, lineas);
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo guardar los empleados.");
        }
    }

    private void cargarEmpleados() {
        try {
            List<String> lineas = ArchivoUtil.leerArchivo(RUTA_ARCHIVO);

            for (String linea : lineas) {
                String[] partes = linea.split(",");
                int id = Integer.parseInt(partes[0]);
                String nombre = partes[1];
                String rol = partes[2];
                Empleado empleado = new Empleado(id, nombre, rol);
                empleados.add(empleado);
            }

        } catch (IOException e) {
            System.out.println("No se pudo cargar el archivo de empleados: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}