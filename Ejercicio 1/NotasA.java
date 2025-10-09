import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class NotasA {
    public static void main(String[] args) {
        // Escribir el
        // archivo/////////////////////////////////////////////////////////////////////////
        System.out.println("//////////Notas//////////");
        try {
            FileWriter writer = new FileWriter("alumnosNotas.txt");
            writer.write("Pepe:5:4:3\n");
            writer.write("Ana:6:7:8\n");
            writer.write("Luisa:9:10:8\n");
            writer.write("Diego:5:5:2");
            writer.close();// Cerramos el escritor
        } catch (IOException e) {
            System.out.println("Error al escribir");
        }
        // Escribir el
        // archivo/////////////////////////////////////////////////////////////////////////

        // Mostrar contenido
        // /////////////////////////////////////////////////////////////////////////
        try {
            BufferedReader br = new BufferedReader(new FileReader("alumnosNotas.txt"));
            String linea = br.readLine();
            while (linea != null) {
                System.out.println(linea);
                linea = br.readLine(); // Leer la siguiente línea

            }
            br.close();// Cerramos el buffer
        } catch (IOException e) {
            System.out.println("Fallo al mostrar");
        }

        // Mostrar contenido
        // /////////////////////////////////////////////////////////////////////////

        // Hacer
        // medias/////////////////////////////////////////////////////////////////////////////
        try {
            BufferedReader br = new BufferedReader(new FileReader("alumnosNotas.txt"));
            FileWriter fw = new FileWriter("alumnosMedias.txt");

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(":");
                String alumno = partes[0];

                double suma = 0;
                for (int i = 1; i < partes.length; i++) {
                    double nota = Double.parseDouble(partes[i]);// Cambiamos el numero de string a double
                    suma += nota;
                }
                double media = suma / (partes.length - 1);
                fw.write(alumno + ":" + media + "\n");
            }
            br.close();
            fw.close();
        } catch (IOException e) {
            System.out.println("Error al leer");
        }
        // Hacer
        // medias/////////////////////////////////////////////////////////////////////////////

        // Mostrar
        // medias//////////////////////////////////////////////////////////////////////
        System.out.println("");
        System.out.println("//////////Medias//////////");
        try {
            BufferedReader br = new BufferedReader(new FileReader("alumnosMedias.txt"));
            String linea = br.readLine();
            while (linea != null) {
                System.out.println(linea);
                linea = br.readLine(); // Leer la siguiente línea

            }
            br.close();// Cerramos el buffer
        } catch (IOException e) {
            System.out.println("Fallo al mostrar");
        }

        // Mostrar
        // medias//////////////////////////////////////////////////////////////////////
    }

}
