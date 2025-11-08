import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionOracleNicolas {
    public static void main(String[] args) {
        Connection conexion;
        try {
            String urijdbc = "jdbc:oracle:thin:@localhost:49161:xe";

            conexion = DriverManager.getConnection(urijdbc, "Nicolas", "Nicolas");

            Statement smt = conexion.createStatement();
            ResultSet rset = smt.executeQuery("select emp_no, Apellido, oficio from emple order by 1");

            while (rset.next())
                System.out
                        .println("empleado numero " + rset.getString(1) + " apellido " + rset.getString(2) + " oficio "
                                + rset.getString(3));

        } catch (SQLException e) {
            System.out.println("Error al conectarse a Oracle: \n" + e.getMessage());
        }
    }
}
