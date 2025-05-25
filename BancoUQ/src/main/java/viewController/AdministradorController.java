package viewController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class AdministradorController implements Initializable {

    // Campos del FXML
    @FXML
    private PasswordField contrasenaField;
    @FXML
    private TableView<Empleado> empleadosTable;
    @FXML
    private TableColumn<Transaccion, String> fechaColumn;
    @FXML
    private TableColumn<Empleado, String> idColumn;
    @FXML
    private TextField idField;
    @FXML
    private TableColumn<Transaccion, Double> montoColumn;
    @FXML
    private TableColumn<Empleado, String> nombreColumn;
    @FXML
    private TextField nombreField;
    @FXML
    private TableColumn<Empleado, String> rolColumn;
    @FXML
    private ComboBox<String> rolCombo;
    @FXML
    private TableColumn<Transaccion, String> tipoColumn;
    @FXML
    private TableView<Transaccion> transaccionesTable;
    @FXML
    private TextField usuarioField;

    // Listas para guardar datos
    private ObservableList<Empleado> listaEmpleados = FXCollections.observableArrayList();
    private ObservableList<Transaccion> listaTransacciones = FXCollections.observableArrayList();

    // Variable para saber si el admin está logueado
    private boolean estaLogueado = false;

    // Se ejecuta cuando se abre la ventana
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarComboBox();
        configurarTablas();
        agregarDatosDePrueba();
        bloquearControles(); // Al inicio todo está bloqueado
    }

    // Configura las opciones del ComboBox de roles
    private void configurarComboBox() {
        rolCombo.getItems().addAll("Gerente", "Contador", "Asesor", "Cajero");
    }

    // Configura las tablas para mostrar datos
    private void configurarTablas() {
        // Tabla de empleados
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        rolColumn.setCellValueFactory(new PropertyValueFactory<>("rol"));
        empleadosTable.setItems(listaEmpleados);

        // Tabla de transacciones
        tipoColumn.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        montoColumn.setCellValueFactory(new PropertyValueFactory<>("monto"));
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        transaccionesTable.setItems(listaTransacciones);

        // Cuando selecciono un empleado en la tabla, se cargan sus datos
        empleadosTable.getSelectionModel().selectedItemProperty().addListener((obs, viejo, nuevo) -> {
            if (nuevo != null) {
                idField.setText(nuevo.getId());
                nombreField.setText(nuevo.getNombre());
                rolCombo.setValue(nuevo.getRol());
            }
        });
    }

    // Agrega algunos datos de ejemplo
    private void agregarDatosDePrueba() {
        // Empleados de prueba
        listaEmpleados.add(new Empleado("001", "Juan Pérez", "Gerente"));
        listaEmpleados.add(new Empleado("002", "María García", "Contador"));
        listaEmpleados.add(new Empleado("003", "Carlos López", "Cajero"));

        // Transacciones de prueba
        listaTransacciones.add(new Transaccion("Depósito", 1000.0, "25/05/2025 10:30"));
        listaTransacciones.add(new Transaccion("Retiro", 500.0, "25/05/2025 11:15"));
        listaTransacciones.add(new Transaccion("Transferencia", 2000.0, "25/05/2025 12:00"));
    }

    // Bloquea los controles hasta que se autentique
    private void bloquearControles() {
        idField.setDisable(true);
        nombreField.setDisable(true);
        rolCombo.setDisable(true);
        empleadosTable.setDisable(true);
        transaccionesTable.setDisable(true);
    }

    // Desbloquea los controles después de autenticarse
    private void desbloquearControles() {
        idField.setDisable(false);
        nombreField.setDisable(false);
        rolCombo.setDisable(false);
        empleadosTable.setDisable(false);
        transaccionesTable.setDisable(false);
    }

    // Limpia los campos de texto
    private void limpiarCampos() {
        idField.clear();
        nombreField.clear();
        rolCombo.setValue(null);
    }

    // Muestra un mensaje de alerta
    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Muestra un mensaje de error
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Botón para autenticar al administrador
    @FXML
    void autenticar(ActionEvent event) {
        String usuario = usuarioField.getText();
        String contraseña = contrasenaField.getText();

        // Verificar que los campos no estén vacíos
        if (usuario.isEmpty() || contraseña.isEmpty()) {
            mostrarError("Por favor complete todos los campos");
            return;
        }

        // Verificar credenciales (usuario: admin, contraseña: 123)
        if (usuario.equals("admin") && contraseña.equals("123")) {
            estaLogueado = true;
            desbloquearControles();
            mostrarMensaje("Éxito", "Autenticación exitosa");

            // Limpiar campos de login
            usuarioField.clear();
            contrasenaField.clear();
        } else {
            mostrarError("Usuario o contraseña incorrectos");
        }
    }

    // Botón para registrar un nuevo empleado
    @FXML
    void registrarEmpleado(ActionEvent event) {
        if (!estaLogueado) {
            mostrarError("Debe autenticarse primero");
            return;
        }

        String id = idField.getText();
        String nombre = nombreField.getText();
        String rol = rolCombo.getValue();

        // Verificar que todos los campos estén llenos
        if (id.isEmpty() || nombre.isEmpty() || rol == null) {
            mostrarError("Por favor complete todos los campos");
            return;
        }

        // Verificar que el ID no exista
        for (Empleado emp : listaEmpleados) {
            if (emp.getId().equals(id)) {
                mostrarError("Ya existe un empleado con ese ID");
                return;
            }
        }

        // Crear y agregar el nuevo empleado
        Empleado nuevoEmpleado = new Empleado(id, nombre, rol);
        listaEmpleados.add(nuevoEmpleado);

        mostrarMensaje("Éxito", "Empleado registrado correctamente");
        limpiarCampos();
    }

    // Botón para modificar un empleado existente
    @FXML
    void modificarEmpleado(ActionEvent event) {
        if (!estaLogueado) {
            mostrarError("Debe autenticarse primero");
            return;
        }

        Empleado seleccionado = empleadosTable.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarError("Seleccione un empleado de la tabla");
            return;
        }

        String nombre = nombreField.getText();
        String rol = rolCombo.getValue();

        if (nombre.isEmpty() || rol == null) {
            mostrarError("Por favor complete todos los campos");
            return;
        }

        // Modificar el empleado seleccionado
        seleccionado.setNombre(nombre);
        seleccionado.setRol(rol);

        // Actualizar la tabla
        empleadosTable.refresh();

        mostrarMensaje("Éxito", "Empleado modificado correctamente");
        limpiarCampos();
    }

    // Botón para eliminar un empleado
    @FXML
    void eliminarEmpleado(ActionEvent event) {
        if (!estaLogueado) {
            mostrarError("Debe autenticarse primero");
            return;
        }

        Empleado seleccionado = empleadosTable.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarError("Seleccione un empleado de la tabla");
            return;
        }

        // Confirmar eliminación
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Está seguro de eliminar al empleado " + seleccionado.getNombre() + "?");

        if (confirmacion.showAndWait().get() == ButtonType.OK) {
            listaEmpleados.remove(seleccionado);
            mostrarMensaje("Éxito", "Empleado eliminado correctamente");
            limpiarCampos();
        }
    }

    // Botón para generar reporte de transacciones
    @FXML
    void generarReporteTransacciones(ActionEvent event) {
        if (!estaLogueado) {
            mostrarError("Debe autenticarse primero");
            return;
        }

        if (listaTransacciones.isEmpty()) {
            mostrarError("No hay transacciones para reportar");
            return;
        }

        // Crear un reporte simple
        StringBuilder reporte = new StringBuilder();
        reporte.append("=== REPORTE DE TRANSACCIONES ===\n\n");

        double totalMonto = 0;
        for (Transaccion t : listaTransacciones) {
            reporte.append("Tipo: ").append(t.getTipo()).append("\n");
            reporte.append("Monto: $").append(t.getMonto()).append("\n");
            reporte.append("Fecha: ").append(t.getFecha()).append("\n");
            reporte.append("------------------------\n");
            totalMonto += t.getMonto();
        }

        reporte.append("\nTotal de transacciones: ").append(listaTransacciones.size());
        reporte.append("\nMonto total: $").append(totalMonto);

        // Mostrar el reporte en una ventana
        Alert reporteAlert = new Alert(Alert.AlertType.INFORMATION);
        reporteAlert.setTitle("Reporte de Transacciones");
        reporteAlert.setHeaderText("Reporte generado exitosamente");

        TextArea textArea = new TextArea(reporte.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);

        reporteAlert.getDialogPane().setExpandableContent(textArea);
        reporteAlert.getDialogPane().setExpanded(true);
        reporteAlert.showAndWait();
    }

    // Botón para volver al menú principal
    @FXML
    void volver(ActionEvent event) {
        mostrarMensaje("Información", "Volviendo al menú principal...");
        // Aquí normalmente cerrarías esta ventana y abrirías el menú principal
        // ((Stage) usuarioField.getScene().getWindow()).close();
    }

    // Clase para representar un Empleado
    public static class Empleado {
        private String id;
        private String nombre;
        private String rol;

        public Empleado(String id, String nombre, String rol) {
            this.id = id;
            this.nombre = nombre;
            this.rol = rol;
        }

        // Getters y Setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public String getRol() { return rol; }
        public void setRol(String rol) { this.rol = rol; }
    }

    // Clase para representar una Transacción
    public static class Transaccion {
        private String tipo;
        private Double monto;
        private String fecha;

        public Transaccion(String tipo, Double monto, String fecha) {
            this.tipo = tipo;
            this.monto = monto;
            this.fecha = fecha;
        }

        // Getters y Setters
        public String getTipo() { return tipo; }
        public void setTipo(String tipo) { this.tipo = tipo; }

        public Double getMonto() { return monto; }
        public void setMonto(Double monto) { this.monto = monto; }

        public String getFecha() { return fecha; }
        public void setFecha(String fecha) { this.fecha = fecha; }
    }
}