package com.aerolinea;

import java.sql.*;
import java.util.Scanner;

public class OpcionesMenu {

    // 1. Mostrar la información de la tabla pasajeros
    public static void mostrarPasajeros(Connection conexion) throws SQLException {
        String sql = "SELECT p.num, p.cod_vuelo, p.tipo_plaza, p.fumador, " +
                "v.destino, v.procedencia, v.hora_salida " +
                "FROM pasajeros p " +
                "JOIN vuelos v ON p.cod_vuelo = v.cod_vuelo " +
                "ORDER BY p.num";

        try (Statement stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n=== LISTA DE PASAJEROS ===");
            System.out.printf("%-10s %-15s %-12s %-8s %-15s %-15s %-15s%n",
                    "NUM", "COD_VUELO", "TIPO_PLAZA", "FUMADOR", "DESTINO", "PROCEDENCIA", "HORA_SALIDA");
            System.out.println(
                    "----------------------------------------------------------------------------------------");

            while (rs.next()) {
                int num = rs.getInt("num");
                String codVuelo = rs.getString("cod_vuelo");
                String tipoPlaza = rs.getString("tipo_plaza");
                String fumador = rs.getString("fumador");
                String destino = rs.getString("destino");
                String procedencia = rs.getString("procedencia");
                String horaSalida = rs.getString("hora_salida");

                System.out.printf("%-10d %-15s %-12s %-8s %-15s %-15s %-15s%n",
                        num, codVuelo, tipoPlaza, fumador, destino, procedencia, horaSalida);
            }
        }
    }

    // 2. Listado de pasajeros por vuelo
    public static void listarPasajerosPorVuelo(Connection conexion, Scanner scanner) throws SQLException {
        System.out.print("Ingrese el código del vuelo: ");
        String codVuelo = scanner.nextLine();

        String sql = "SELECT p.num, p.tipo_plaza, p.fumador, v.destino, v.procedencia, v.hora_salida " +
                "FROM pasajeros p " +
                "JOIN vuelos v ON p.cod_vuelo = v.cod_vuelo " +
                "WHERE p.cod_vuelo = ? " +
                "ORDER BY p.num";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, codVuelo);

            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("\n=== PASAJEROS DEL VUELO " + codVuelo + " ===");
                System.out.printf("%-10s %-12s %-8s %-15s %-15s %-15s%n",
                        "NUM", "TIPO_PLAZA", "FUMADOR", "DESTINO", "PROCEDENCIA", "HORA_SALIDA");
                System.out.println("------------------------------------------------------------------------");

                boolean hayResultados = false;
                while (rs.next()) {
                    hayResultados = true;
                    int num = rs.getInt("num");
                    String tipoPlaza = rs.getString("tipo_plaza");
                    String fumador = rs.getString("fumador");
                    String destino = rs.getString("destino");
                    String procedencia = rs.getString("procedencia");
                    String horaSalida = rs.getString("hora_salida");

                    System.out.printf("%-10d %-12s %-8s %-15s %-15s %-15s%n",
                            num, tipoPlaza, fumador, destino, procedencia, horaSalida);
                }

                if (!hayResultados) {
                    System.out.println("No se encontraron pasajeros para el vuelo: " + codVuelo);
                }
            }
        }
    }

    // 3. Insertar un vuelo nuevo
    public static void insertarVuelo(Connection conexion, Scanner scanner) throws SQLException {
        System.out.println("\n=== INSERTAR NUEVO VUELO ===");

        System.out.print("Código del vuelo: ");
        String codVuelo = scanner.nextLine();

        System.out.print("Hora salida (DD/MM/YY-HH:MI): ");
        String horaSalida = scanner.nextLine();

        System.out.print("Destino: ");
        String destino = scanner.nextLine();

        System.out.print("Procedencia: ");
        String procedencia = scanner.nextLine();

        System.out.print("Plazas fumador: ");
        int plazasFumador = Integer.parseInt(scanner.nextLine());

        System.out.print("Plazas no fumador: ");
        int plazasNoFumador = Integer.parseInt(scanner.nextLine());

        System.out.print("Plazas turista: ");
        int plazasTurista = Integer.parseInt(scanner.nextLine());

        System.out.print("Plazas primera: ");
        int plazasPrimera = Integer.parseInt(scanner.nextLine());

        String sql = "INSERT INTO vuelos (cod_vuelo, hora_salida, destino, procedencia, " +
                "plazas_fumador, plazas_no_fumador, plazas_turista, plazas_primera) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, codVuelo);
            pstmt.setString(2, horaSalida);
            pstmt.setString(3, destino);
            pstmt.setString(4, procedencia);
            pstmt.setInt(5, plazasFumador);
            pstmt.setInt(6, plazasNoFumador);
            pstmt.setInt(7, plazasTurista);
            pstmt.setInt(8, plazasPrimera);

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Vuelo insertado correctamente");
            } else {
                System.out.println("Error al insertar el vuelo");
            }
        }
    }

    // 4. Borrar un vuelo
    public static void borrarVuelo(Connection conexion, Scanner scanner) throws SQLException {
        System.out.print("Ingrese el código del vuelo a borrar: ");
        String codVuelo = scanner.nextLine();

        // Primero borrar los pasajeros asociados (por integridad referencial)
        String sqlPasajeros = "DELETE FROM pasajeros WHERE cod_vuelo = ?";
        String sqlVuelo = "DELETE FROM vuelos WHERE cod_vuelo = ?";

        try {
            conexion.setAutoCommit(false);

            // Borrar pasajeros primero
            try (PreparedStatement pstmtPasajeros = conexion.prepareStatement(sqlPasajeros)) {
                pstmtPasajeros.setString(1, codVuelo);
                int pasajerosEliminados = pstmtPasajeros.executeUpdate();
                System.out.println("Eliminados " + pasajerosEliminados + " pasajeros del vuelo");
            }

            // Borrar vuelo
            try (PreparedStatement pstmtVuelo = conexion.prepareStatement(sqlVuelo)) {
                pstmtVuelo.setString(1, codVuelo);
                int filasAfectadas = pstmtVuelo.executeUpdate();

                if (filasAfectadas > 0) {
                    conexion.commit();
                    System.out.println("Vuelo borrado correctamente");
                } else {
                    conexion.rollback();
                    System.out.println("No se encontró el vuelo con código: " + codVuelo);
                }
            }

        } catch (SQLException e) {
            conexion.rollback();
            throw e;
        } finally {
            conexion.setAutoCommit(true);
        }
    }

    // 5. Modificar las plazas de fumadores
    public static void modificarPlazasFumadores(Connection conexion) throws SQLException {
        // Establecer plazas_fumador = 25% de plazas_no_fumador
        String sql = "UPDATE vuelos SET plazas_fumador = ROUND(plazas_no_fumador * 0.25)";

        try (Statement stmt = conexion.createStatement()) {
            int filasAfectadas = stmt.executeUpdate(sql);
            System.out.println("Plazas de fumador actualizadas en " + filasAfectadas + " vuelos");
            System.out
                    .println("Se han establecido las plazas de fumador a la cuarta parte de las plazas de no fumador");
        }
    }
}