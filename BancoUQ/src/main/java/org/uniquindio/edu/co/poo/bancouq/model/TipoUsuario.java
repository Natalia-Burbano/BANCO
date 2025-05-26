package org.uniquindio.edu.co.poo.bancouq.model;

public enum TipoUsuario {
    ADMINISTRADOR("admin", "admin123", "/org/uniquindio/edu/co/poo/bancouq/administrador.fxml", "BANCO UQ - Panel de Administrador"),
    CAJERO("cajero", "cajero123", "/org/uniquindio/edu/co/poo/bancouq/cajero.fxml", "BANCO UQ - Panel de Cajero"),
    CLIENTE("cliente", "cliente123", "/org/uniquindio/edu/co/poo/bancouq/transaccion-view.fxml", "BANCO UQ - Panel de Cliente");

    private final String usuario;
    private final String contrasena;
    private final String rutaFxml;
    private final String tituloVentana;

    TipoUsuario(String usuario, String contrasena, String rutaFxml, String tituloVentana) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.rutaFxml = rutaFxml;
        this.tituloVentana = tituloVentana;
    }

    // Método para validar credenciales y obtener el tipo de usuario
    public static TipoUsuario validarCredenciales(String usuario, String contrasena, String rolSeleccionado) {
        for (TipoUsuario tipo : values()) {
            if (tipo.usuario.equals(usuario) &&
                    tipo.contrasena.equals(contrasena) &&
                    tipo.name().equals(rolSeleccionado.toUpperCase())) {
                return tipo;
            }
        }
        return null; // Credenciales inválidas
    }

    // Getters
    public String getUsuario() { return usuario; }
    public String getContrasena() { return contrasena; }
    public String getRutaFxml() { return rutaFxml; }
    public String getTituloVentana() { return tituloVentana; }
    public String getNombreRol() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}