import java.io.*;
import java.nio.file.*;
import java.util.*;

public class EjercicioAccesoAleatorio {

    // Tamaños de los campos para movernos mas facilmente
    private static final int TAM_APELLIDO = 20;
    private static final int TAM_REGISTRO = 4 + (TAM_APELLIDO * 2) + 4 + 8 + 8 + 8;

    public static void main(String[] args) throws IOException {
        crearFicheroAlumnos();
        mostrarMenu();
    }

    // Método para crear el fichero de alumnos inicial
    private static void crearFicheroAlumnos() throws IOException {
        File fichero = new File("alumnos.dat");

        // Si el fichero ya existe, no lo creamos de nuevo
        if (fichero.exists()) {
            System.out.println("El fichero alumnos.dat ya existe.");
            return;
        }

        RandomAccessFile file = new RandomAccessFile(fichero, "rw");

        String[] apellidos = { "FERNANDEZ", "LOPEZ", "GOMEZ", "SERRANO", "ALONSO" };
        int[] edades = { 17, 20, 18, 17, 19 };
        double[] notas1 = { 7.5, 4.2, 6.5, 8.0, 3.2 };
        double[] notas2 = { 5.5, 9.2, 8.5, 5.0, 2.0 };
        double[] notas3 = { 4.6, 3.5, 9.0, 7.1, 1.9 };

        StringBuffer buffer = null;

        for (int i = 0; i < apellidos.length; i++) {
            // Escribir el numero de orden
            file.writeInt(i + 1);

            // Escribir apellido
            buffer = new StringBuffer(apellidos[i]);
            buffer.setLength(TAM_APELLIDO);// Hacemos q ocupe 20 espacios si no se llegan se añaden espacios
            file.writeChars(buffer.toString());

            // Escribir edad y notas correspondientes de cada alumno
            file.writeInt(edades[i]);
            file.writeDouble(notas1[i]);
            file.writeDouble(notas2[i]);
            file.writeDouble(notas3[i]);
        }

        file.close();
        System.out.println("Se ha creado alumnos.dat");
    }

    // Método para mostrar el menú principal
    private static void mostrarMenu() throws IOException {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("");
            System.out.println("1. Mostrar datos");
            System.out.println("2. Generar fichero de medias ");
            System.out.println("3. Mostrar las medias ");
            System.out.println("4. Borrar el fichero ");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    mostrarDatosAlumno();
                    break;
                case 2:
                    generarFicheroMedias();
                    break;
                case 3:
                    mostrarNotasMedias();
                    break;
                case 4:
                    borrarFicheroMedias();
                    break;
                case 5:
                    System.out.println("Saliendo del programa");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 5);

        scanner.close();
    }

    // Opción 1: Mostrar datos de un alumno por número de orden
    private static void mostrarDatosAlumno() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduzca el número del alumno: ");
        int numero = scanner.nextInt();

        File fichero = new File("alumnos.dat");
        if (!fichero.exists()) {
            System.out.println("El fichero alumnos.dat no existe.");
            return;
        }

        RandomAccessFile file = new RandomAccessFile(fichero, "r");

        // Calcular posición del registro
        long posicion = (long) (numero - 1) * TAM_REGISTRO;

        if (posicion >= file.length()) {
            System.out.println("No hay tantos alumnos");
            file.close();
            return;
        }

        file.seek(posicion);

        // Leer datos
        int id = file.readInt();
        String apellido = "";
        for (int i = 0; i < TAM_APELLIDO; i++) {
            apellido += file.readChar();
        }
        apellido = apellido.trim(); // Eliminar espacios en blanco

        int edad = file.readInt();
        double nota1 = file.readDouble();
        double nota2 = file.readDouble();
        double nota3 = file.readDouble();

        // Mostrar datos
        System.out.println(" ");
        System.out.println("Número de orden: " + id);
        System.out.println("Apellido: " + apellido);
        System.out.println("Edad: " + edad);
        System.out.println("Nota 1: " + nota1);
        System.out.println("Nota 2: " + nota2);
        System.out.println("Nota 3: " + nota3);
        System.out.printf("Nota media: %.2f\n", (nota1 + nota2 + nota3) / 3);

        file.close();
    }

    // Opción 2: Generar fichero de medias
    private static void generarFicheroMedias() throws IOException {
        File ficheroOrigen = new File("alumnos.dat");
        if (!ficheroOrigen.exists()) {// si no exsiste el fichero no se puede trabajar en el
            System.out.println("El fichero alumnos.dat no existe.");
            return;
        }

        File ficheroDestino = new File("mediasalumnos.dat");// Creamos medias
        RandomAccessFile fileOrigen = new RandomAccessFile(ficheroOrigen, "r");// Leemos alumnos.dat
        RandomAccessFile fileDestino = new RandomAccessFile(ficheroDestino, "rw");// Escribimos medias

        int numRegistros = (int) (fileOrigen.length() / TAM_REGISTRO);// Divide el tamaño total del fichero entre los
                                                                      // alumnos

        for (int i = 0; i < numRegistros; i++) {
            long posicion = (long) i * TAM_REGISTRO;// Saltamos al registro en el q esta el alumno i * 72bytes q ocupa
                                                    // cada alumno
            fileOrigen.seek(posicion);// Salta a la posicion

            // Leer datos del alumno
            int id = fileOrigen.readInt();
            String apellido = "";
            for (int j = 0; j < TAM_APELLIDO; j++) {
                apellido += fileOrigen.readChar();
            }
            apellido = apellido.trim();

            fileOrigen.readInt(); // Saltar edad
            double nota1 = fileOrigen.readDouble();
            double nota2 = fileOrigen.readDouble();
            double nota3 = fileOrigen.readDouble();

            // Calcular media
            double media = (nota1 + nota2 + nota3) / 3;

            // Escribir en el fichero de medias
            fileDestino.writeInt(id);// Escribe el numero de alumno

            StringBuffer buffer = new StringBuffer(apellido);
            buffer.setLength(TAM_APELLIDO);// INdica lo q ocupa el apellido
            fileDestino.writeChars(buffer.toString());// Escribe apellido

            fileDestino.writeDouble(media);// Escribe la media
        }

        fileOrigen.close();
        fileDestino.close();
        System.out.println("Se ha creado mediasalumnos.dat");
    }

    // Opción 3: Mostrar notas medias
    private static void mostrarNotasMedias() throws IOException {
        File fichero = new File("mediasalumnos.dat");
        if (!fichero.exists()) {
            System.out.println("Genera primero el fichero de medias.");
            return;
        }

        RandomAccessFile file = new RandomAccessFile(fichero, "r");

        // Tamaño del registro en el fichero de medias
        int tamRegistroMedias = 4 + (TAM_APELLIDO * 2) + 8;// tamalo id tamaño apellido tamaño de la media
        int numRegistros = (int) (file.length() / tamRegistroMedias);// Tamaño del fichero partido por tamaño registro

        System.out.println("");
        System.out.println("Medias");

        for (int i = 0; i < numRegistros; i++) {
            long posicion = (long) i * tamRegistroMedias;// Posicion alumno
            file.seek(posicion);// Saltar a la posicion

            int id = file.readInt();// Lee el id

            double media = file.readDouble();// Lee la media

            System.out.printf("Alumno " + id + ". " + media);
        }

        file.close();
    }

    // Opción 4: Borrar fichero de medias
    private static void borrarFicheroMedias() {
        File fichero = new File("mediasalumnos.dat");

        if (fichero.exists()) {
            if (fichero.delete()) {
                System.out.println("Borrado correctamente.");
            } else {
                System.out.println("No se ha podido borrar mediasalumnos.dat.");
            }
        } else {
            System.out.println("mediasalumnos.dat no existe.");
        }
    }
}