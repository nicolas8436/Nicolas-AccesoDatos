package com.aerolinea;

import java.sql.Connection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:oracle:thin:@localhost:49161:xe";

        GestorBaseDatos gestorBD = null;
        Connection conexion = null;
        Scanner scanner = new Scanner(System.in);

        try {
            gestorBD = new GestorBaseDatos(url, "Nicolas", "Nicolas");
            conexion = gestorBD.creaConexion();

            int opcion;
            do {
                mostrarMenu();
                System.out.print("Seleccione una opción: ");
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        OpcionesMenu.mostrarPasajeros(conexion);
                        break;
                    case 2:
                        OpcionesMenu.listarPasajerosPorVuelo(conexion, scanner);
                        break;
                    case 3:
                        OpcionesMenu.insertarVuelo(conexion, scanner);
                        break;
                    case 4:
                        OpcionesMenu.borrarVuelo(conexion, scanner);
                        break;
                    case 5:
                        OpcionesMenu.modificarPlazasFumadores(conexion);
                        break;
                    case 6:
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }

            } while (opcion != 6);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (gestorBD != null) {
                gestorBD.cierraConexion();
            }
            scanner.close();
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n=== GESTIÓN AEROPUERTO ===");
        System.out.println("1. Mostrar información de pasajeros");
        System.out.println("2. Listado de pasajeros por vuelo");
        System.out.println("3. Insertar un vuelo nuevo");
        System.out.println("4. Borrar un vuelo");
        System.out.println("5. Modificar plazas de fumadores");
        System.out.println("6. Salir del programa");
        System.out.println("============================");
    }
}