import java.io.*;

public class CrearEmpleadosDat {
    public static void main(String[] args) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("EMPLEADOS.DAT"))) {
            oos.writeObject(new Empleado(1, "Juan", "Madrid", 1500.0f, 12.0f));
            oos.writeObject(new Empleado(2, "María", "Murcia", 1700.0f, 9.0f));
            oos.writeObject(new Empleado(3, "Luis", "Sevilla", 1600.0f, 10.0f));
            oos.writeObject(new Empleado(4, "Ana", "Barcelona", 1800.0f, 11.0f));
            oos.writeObject(new Empleado(5, "Carlos", "Valencia", 1550.0f, 8.5f));
            System.out.println("Archivo EMPLEADOS.DAT creado correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
