package com.aerolinea;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GestorBaseDatos {
    private Connection conexion;
    private String url;
    private String usuario;
    private String contrasena;

    public GestorBaseDatos(String url, String usuario, String contrasena) {
        this.url = url;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public Connection creaConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            try {
                // Cargar el driver de Oracle
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conexion = DriverManager.getConnection(url, usuario, contrasena);
                System.out.println("Conexión establecida con Oracle");
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver Oracle no encontrado", e);
            }
        }
        return conexion;
    }

    public void cierraConexion() {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Conexión cerrada");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    public Connection getConexion() {
        return conexion;
    }
}