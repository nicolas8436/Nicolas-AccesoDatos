import java.sql.*;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ConectaEstudiantes {
    private Connection conexion;
    private Scanner scanner;

    // Constructor
    public ConectaEstudiantes() {
        this.scanner = new Scanner(System.in);
    }

    public void conectar() {// Conectar//////////////////////////////////////////////////////////////////////////////
        try {
            String urijdbc = "jdbc:oracle:thin:@localhost:49161:xe";
            conexion = DriverManager.getConnection(urijdbc, "Nicolas", "Nicolas");
            System.out.println("Conexión establecida correctamente con Oracle");
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }// Conectar

    public void desconectar() {// Desconectar//////////////////////////////////////////////////////////////////////////
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexión cerrada correctamente");
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }// Desconectar

    public void insertar(Estudiante e) {// Insertar////////////////////////////////////////////////////////////////////
        if (conexion == null) {
            System.out.println("Primero debes conectar con la base de datos");
            return;
        }

        String sql = "INSERT INTO estudiantes (nif, nombre, edad) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, e.getNif());
            pstmt.setString(2, e.getNombre());
            pstmt.setInt(3, e.getEdad());

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Estudiante " + e.getNombre() + " insertado correctamente");
            }
        } catch (SQLException ex) {
            System.out.println("Error al insertar estudiante: " + ex.getMessage());
        }
    }// Insertar

    public void actualizarEdad(String nif, int nuevaEdad) {// Actualizar
                                                           // edad///////////////////////////////////////////////////////////
        if (conexion == null) {
            System.out.println("Primero debes conectar con la base de datos");
            return;
        }

        // Verificar si el estudiante existe
        if (!existeEstudiante(nif)) {
            System.out.println("No existe ningún estudiante con NIF: " + nif);
            return;
        }

        try (PreparedStatement pstmt = conexion.prepareStatement("UPDATE estudiantes SET edad = ? WHERE nif = ?")) {
            pstmt.setInt(1, nuevaEdad);
            pstmt.setString(2, nif);

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Edad actualizada correctamente para el estudiante con NIF: " + nif);
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar edad: " + e.getMessage());
        }
    } // Actualizar edad

    public void borrarPreguntando(String nif) {// Borrar
                                               // estudiante///////////////////////////////////////////////////////////////
        if (conexion == null) {
            System.out.println("Primero debes conectar con la base de datos");
            return;
        }

        // Verificar si el estudiante existe
        if (!existeEstudiante(nif)) {
            System.out.println("No existe ningún estudiante con NIF: " + nif);
            return;
        }

        // Obtener nombre del estudiante para mostrar en la confirmación
        String nombreEstudiante = obtenerNombre(nif);

        System.out.print("¿Estás seguro que quieres borrar a " + nombreEstudiante + "? (S/N): ");
        String respuesta = scanner.nextLine();

        if (respuesta.equalsIgnoreCase("S")) {
            String sql = "DELETE FROM estudiantes WHERE nif = ?";

            try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
                pstmt.setString(1, nif);

                int filasAfectadas = pstmt.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("Estudiante " + nombreEstudiante + " borrado correctamente");
                }
            } catch (SQLException e) {
                System.out.println("Error al borrar estudiante: " + e.getMessage());
            }
        } else {
            System.out.println("Operación cancelada");
        }
    }// Borrar estudiante

    public void estudiantesEntreEdades(int edadMin, int edadMax) {// Entre
                                                                  // edades//////////////////////////////////////////////////////
        if (conexion == null) {
            System.out.println("Primero debes conectar con la base de datos");
            return;
        }

        String sql = "SELECT nif, nombre, edad FROM estudiantes WHERE edad BETWEEN ? AND ? ORDER BY edad";
        int contador = 0;

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, edadMin);
            pstmt.setInt(2, edadMax);

            ResultSet rs = pstmt.executeQuery();

            System.out.println("\n--- ESTUDIANTES ENTRE " + edadMin + " Y " + edadMax + " AÑOS ---");
            while (rs.next()) {
                String nif = rs.getString("nif");
                String nombre = rs.getString("nombre");
                int edad = rs.getInt("edad");

                System.out.printf("NIF: %s | Nombre: %-15s | Edad: %d%n", nif, nombre, edad);
                contador++;
            }

            System.out.println("Total de estudiantes encontrados: " + contador);

        } catch (SQLException e) {
            System.out.println("Error al consultar estudiantes: " + e.getMessage());
        }
    }// Entre edades

    // Método para insertar desde archivo
    // CSV///////////////////////////////////////////////////////////////
    public void insertarDesdeArchivo() {
        if (conexion == null) {
            System.out.println("Primero debes conectar con la base de datos");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader("estudiantes.txt"))) {
            String linea;
            int contador = 0;

            System.out.println("Leyendo datos del archivo: " + "estudiantes.txt");

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos.length == 3) {
                    String nif = datos[0].trim();
                    String nombre = datos[1].trim();
                    int edad = Integer.parseInt(datos[2].trim());

                    Estudiante estudiante = new Estudiante(nif, nombre, edad);
                    insertar(estudiante);
                    contador++;
                }
            }

            System.out.println("Se han procesado " + contador + " estudiantes desde el archivo");

        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error en el formato de los datos del archivo: " + e.getMessage());
        }
    }

    private boolean existeEstudiante(String nif) {// existe estudiante//////////////////////////////////
        String sql = "SELECT COUNT(*) FROM estudiantes WHERE nif = ?";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, nif);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar estudiante: " + e.getMessage());
        }

        return false;
    }// existe estudiante

    private String obtenerNombre(String nif) {// Obtener nombre////////////////////////////////////////
        String sql = "SELECT nombre FROM estudiantes WHERE nif = ?";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, nif);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("nombre");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener nombre del estudiante: " + e.getMessage());
        }

        return "Estudiante";
    }// Obtener nombre
}