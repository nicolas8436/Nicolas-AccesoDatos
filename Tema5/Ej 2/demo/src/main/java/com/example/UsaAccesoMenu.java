package com.example;

import java.util.Scanner;

public class UsaAccesoMenu {
    public static void main(String[] args) {
        AccesoMenu acceso = new AccesoMenu();
        Scanner scanner = new Scanner(System.in);
        int opcion;
        
        System.out.println("=== APLICACIÓN DE GESTIÓN DE MENÚS ===");
        System.out.println("Conectando a eXist-db...");
        
        // 1. Llamar al método conectar
        acceso.conectar();
        
        do {
            // Mostrar menú de opciones
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Mostrar todos los menús");
            System.out.println("2. Insertar nuevo menú");
            System.out.println("3. Buscar menú por nombre");
            System.out.println("4. Borrar menú por nombre");
            System.out.println("5. Actualizar precios (incremento %)");
            System.out.println("6. Buscar menús más baratos que...");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");
            
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer
            
            switch(opcion) {
                case 1:
                    acceso.mostrarMenus();
                    break;
                    
                case 2:
                    acceso.insertMenu();
                    break;
                    
                case 3:
                    System.out.print("Introduzca el nombre del menú a buscar: ");
                    String nombreBuscar = scanner.nextLine();
                    acceso.buscarMenu(nombreBuscar);
                    break;
                    
                case 4:
                    System.out.print("Introduzca el nombre del menú a borrar: ");
                    String nombreBorrar = scanner.nextLine();
                    acceso.borrarMenu(nombreBorrar);
                    break;
                    
                case 5:
                    System.out.print("Introduzca el porcentaje de incremento: ");
                    int incremento = scanner.nextInt();
                    acceso.actualizaPrecio(incremento);
                    break;
                    
                case 6:
                    System.out.print("Introduzca el precio máximo: ");
                    int precioMax = scanner.nextInt();
                    acceso.buscarMasBaratoQue(precioMax);
                    break;
                    
                case 7:
                    System.out.println("Saliendo del programa...");
                    break;
                    
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
            
        } while(opcion != 7);
        
        // 2. Llamar al método desconectar al salir
        acceso.desconectar();
        scanner.close();
    }
}