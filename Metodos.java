import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Scanner;

public class Metodos {

    private Connection conexion;
    private Scanner scanner;

    // Constructor
    public Metodos() {
        this.scanner = new Scanner(System.in);
    }

    public void conectar() {

        String url = "jdbc:oracle:thin:@localhost:49161:xe";
        try {

            conexion = DriverManager.getConnection(url, "Nicolas", "Nicolas");

            System.out.println("Conexion establecida con exito");

        } catch (SQLException e) {
            System.out.println("Error al conectarse a la base de datos");
        }

    }

    public void desconectar() {

        try {

            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            } else {
                System.out.println("No puedes cerrar una conexion que no existe");
            }

        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexion");
        }

    }

    public void mostrarTabla() {

        if (conexion == null) {
            System.out.println("Debes iniciar conexion primero");
            return;
        }

        String sql = "SELECT * FROM persona";

        try {

            Statement stm = conexion.createStatement();
            ResultSet rset = stm.executeQuery(sql);

            while (rset.next()) {
                System.out.println("DNI: " + rset.getString(1) + " , nombre: " + rset.getString(2) + " ,edad: "
                        + rset.getInt(3) + " ,genero: " + rset.getString(4) + ".\n");
            }
        } catch (SQLException e) {

            System.out.println("Error al mostrar la tabla");

        }

    }

    public void mostrarPersona(String id_Pers) {
        String sql = "Select * FROM persona WHERE ID_PERSONA = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setString(1, id_Pers);

            ResultSet rset = pstmt.executeQuery();

            if (rset.next()) {
                System.out.println("DNI: " + rset.getString(1) + " , nombre: " + rset.getString(2) + " ,edad: "
                        + rset.getInt(3) + " ,genero: " + rset.getString(4) + ".\n");
            } else {
                System.out.println("Persona no encontrada");
            }

        } catch (SQLException e) {
            System.out.println("Error al mostrar la persona");
        }
    }

    public void insertarPersona(Persona p) {

        if (conexion == null) {
            System.out.println("Primero debes conectar con la base de datos");
            return;
        }

        String sql = "Insert into persona (ID_PERSONA, NOMBRE, EDAD, GENERO) VALUES (?,?,?,?)";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setString(1, p.getId_persona());
            pstmt.setString(2, p.getNombre());
            pstmt.setInt(3, p.getEdad());
            pstmt.setString(4, p.getGenero());

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Estudiante " + p.getNombre() + " insertado correctamente");
            }

        } catch (SQLException e) {
            System.out.println("Error al insertar la persona");
        }
    }

    public void borrarPersona(String id_Pers) {

        if (conexion == null) {
            System.out.println("Primero debes conectar con la base de datos");
            return;
        }

        String sql = "Delete FROM persona WHERE ID_PERSONA = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setString(1, id_Pers);

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Estudiante " + id_Pers + " borrado correctamente");
            }

        } catch (SQLException e) {
            System.out.println("Error al borrar a la persona");
        }
    }

    public void ejecutarFunction(int num1, int num2) {

        if (conexion == null) {
            System.out.println("Primero debes conectar con la base de datos");
            return;
        }

        String sql = "{call PROC_SUMA(?, ?, ?)}";// 3 Parametros = 2 in, 1 out
        try (CallableStatement cstmt = conexion.prepareCall(sql)) {

            // Parametro in
            cstmt.setInt(1, num1);
            cstmt.setInt(2, num2);

            // Parametro out
            cstmt.registerOutParameter(3, Types.INTEGER);

            cstmt.execute();

            int resultado = cstmt.getInt(3);

            System.out.println("Resultado: " + resultado);

        } catch (SQLException e) {
            System.out.println("Error al ejecutar la funcion");
        }
    }
}