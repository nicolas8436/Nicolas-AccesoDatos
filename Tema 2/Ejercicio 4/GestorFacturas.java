import java.sql.*;

public class GestorFacturas {

    private Connection conn;

    public GestorFacturas(Connection conn) {
        this.conn = conn;
    }

    public void guardarFactura(Factura f) throws SQLException {
        String sqlFactura = "INSERT INTO FACTURAS (NUMERO, NOMBRE_CLIENTE) VALUES (?, ?)";
        String sqlApunte = "INSERT INTO APUNTES (ID, NUMERO_FACTURA, PRODUCTO, CANTIDAD, PRECIO_UNIDAD) VALUES (SEQ_APUNTE.NEXTVAL, ?, ?, ?, ?)";

        try (PreparedStatement psFactura = conn.prepareStatement(sqlFactura);
                PreparedStatement psApunte = conn.prepareStatement(sqlApunte)) {

            // Insertar la factura
            psFactura.setInt(1, f.getNumero());
            psFactura.setString(2, f.getNombreCliente());
            psFactura.executeUpdate();

            // Insertar los apuntes
            for (Apunte a : f.getApuntes()) {
                psApunte.setInt(1, f.getNumero());
                psApunte.setString(2, a.getProducto());
                psApunte.setDouble(3, a.getCantidad());
                psApunte.setDouble(4, a.getPrecioUnidad());
                psApunte.executeUpdate();
            }

            conn.commit();
        }
    }
}
