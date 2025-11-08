import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ConectaEstudiantes conecta = new ConectaEstudiantes();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n=== MENÚ GESTIÓN DE ESTUDIANTES ===");
            System.out.println("1. Conectar a la base de datos");
            System.out.println("2. Insertar estudiante");
            System.out.println("3. Actualizar edad de estudiante");
            System.out.println("4. Borrar estudiante");
            System.out.println("5. Buscar estudiantes por rango de edades");
            System.out.println("6. Insertar estudiantes desde archivo");
            System.out.println("7. Desconectar");
            System.out.println("0. Salir");
            System.out.print("Selecciona una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    conecta.conectar();
                    break;

                case 2:
                    System.out.print("Introduce NIF: ");
                    String nif = scanner.nextLine();
                    System.out.print("Introduce nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Introduce edad: ");
                    int edad = scanner.nextInt();
                    scanner.nextLine();

                    Estudiante nuevoEstudiante = new Estudiante(nif, nombre, edad);
                    conecta.insertar(nuevoEstudiante);
                    break;

                case 3:
                    System.out.print("Introduce NIF del estudiante a actualizar: ");
                    String nifActualizar = scanner.nextLine();
                    System.out.print("Introduce nueva edad: ");
                    int nuevaEdad = scanner.nextInt();
                    scanner.nextLine();

                    conecta.actualizarEdad(nifActualizar, nuevaEdad);
                    break;

                case 4:
                    System.out.print("Introduce NIF del estudiante a borrar: ");
                    String nifBorrar = scanner.nextLine();
                    conecta.borrarPreguntando(nifBorrar);
                    break;

                case 5:
                    System.out.print("Introduce edad mínima: ");
                    int edadMin = scanner.nextInt();
                    System.out.print("Introduce edad máxima: ");
                    int edadMax = scanner.nextInt();
                    scanner.nextLine();

                    conecta.estudiantesEntreEdades(edadMin, edadMax);
                    break;

                case 6:
                    conecta.insertarDesdeArchivo();
                    break;

                case 7:
                    conecta.desconectar();
                    break;

                case 0:
                    System.out.println("Cerrando programa...");
                    break;

                default:
                    System.out.println("Opción no válida");
            }

        } while (opcion != 0);

        scanner.close();
    }
}