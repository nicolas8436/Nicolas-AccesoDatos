import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class EjercicioObjetStream {

    public static ArrayList<Articulo> listaProductos = new ArrayList<>();
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        cargarDatos();
        menu();

    }

    public static void menu() {

        int menu = 0;

        do {
            menu = 0;
            System.out.println("\n----Menu de gestion----");
            System.out.println("1.Mostrar articulos");
            System.out.println("2.Añadir un articulo nuevo");
            System.out.println("3.Mostrar un articulo");
            System.out.println("4.Buscador por stock minimo");
            System.out.println("5.Modificar precio de articulo");
            System.out.println("6.Guardar y salir");
            System.out.println("Que desea hacer(1-6) ");
            System.out.println("");

            menu = scanner.nextInt();
            scanner.nextLine();

            switch (menu) {
                case 1:
                    mostrarArticulos();
                    break;

                case 2:
                    añadirArticulo();
                    break;

                case 3:
                    mostrarArticulo();
                    break;

                case 4:
                    mostrarPorStock();
                    break;

                case 5:
                    cambioPrecio();
                    break;

                case 6:
                    guardarySalir();
                    break;

                default:
                    System.out.println("Numero no valido");
                    break;
            }
        } while (menu != 6);
    }

    public static void mostrarArticulos() {
        if (!listaProductos.isEmpty()) {
            int cont = 1;
            for (Articulo n : listaProductos) {

                System.out.println("--Articulo " + cont + "--");
                n.infoArticulo();
                cont++;
            }
        } else {
            System.out.println("Introduce articulos primero");
        }
    }

    public static void añadirArticulo() {

        System.out.println("");
        int codigo;
        while (true) {
            System.out.println("Indica código del artículo: ");
            codigo = scanner.nextInt();
            scanner.nextLine();

            if (codigoExiste(codigo)) {
                System.out.println("El codigo ya esta pon otro");
            } else {
                break;
            }
        }

        String nombre;
        while (true) {
            System.out.println("Indica el nombre del artículo: ");
            nombre = scanner.nextLine().trim();

            if (nombre.isEmpty()) {
                System.out.println("El nombre no puede estar vacío.");
            } else if (nombreExiste(nombre)) {
                System.out.println("No puedes repetir nombres");
            } else {
                break;
            }
        }
        double precio;
        do {
            System.out.println("Indica el precio del articulo: ");
            precio = scanner.nextDouble();

            if (precio <= 0) {
                System.out.println("El precio no puede ser negativo");
            }
        } while (precio <= 0);

        System.out.println("Indica la cantidad en stock: ");
        int stock = scanner.nextInt();
        scanner.nextLine();

        Articulo articulo = new Articulo(codigo, nombre, precio, stock);
        listaProductos.add(articulo);
    }

    private static boolean codigoExiste(int codigo) {
        for (Articulo art : listaProductos) {
            if (art.getCodigo() == codigo) {
                return true;
            }
        }
        return false;
    }

    private static boolean nombreExiste(String nombre) {
        for (Articulo art : listaProductos) {
            if (art.getNombre().equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
    }

    private static void mostrarArticulo() {
        if (!listaProductos.isEmpty()) {
            String nombre;
            System.out.println("");
            System.out.println("Que articulo quieres buscar");
            nombre = scanner.nextLine();

            buscar(nombre);
        } else {
            System.out.println("No hay articulos en la lista");
        }

    }

    public static void buscar(String nombre) {
        boolean encontrado = false;
        for (Articulo n : listaProductos) {
            if (n.getNombre().equalsIgnoreCase(nombre)) {
                System.out.println("");
                System.out.println("--Articulo encontrado--");
                n.infoArticulo();
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("Artículo no encontrado");
        }
    }

    public static void mostrarPorStock() {
        if (!listaProductos.isEmpty()) {
            int stock;
            System.out.println("");
            System.out.println("Indica la cantidad de stock por la q quieres buscar: ");
            stock = scanner.nextInt();
            scanner.nextLine();

            buscarStock(stock);
        } else {
            System.out.println("La lista esta vacia");
        }

    }

    public static void buscarStock(int stock) {
        boolean encontrado = false;
        for (Articulo n : listaProductos) {
            if (n.getStock() <= stock) {
                n.infoArticulo();
                encontrado = true;

            }

        }
        if (!encontrado) {
            System.out.println("No hay articulos con ese stock o menos");
        }

    }

    public static void cambioPrecio() {
        if (!listaProductos.isEmpty()) {
            String nombre;
            System.out.println("");
            System.out.println("Indique el nombre del producto a cambiar el precio: ");
            nombre = scanner.nextLine();
            System.out.println("Indique el nuevo precio: ");
            double precio = scanner.nextDouble();
            scanner.nextLine();

            Articulo articulo = buscarParaCambio(nombre);
            if (articulo != null) {
                articulo.setPrecio(precio);
                System.out.println("Precio actualizado");
            }

        } else {
            System.out.println("La lista esta vacia");
        }
    }

    public static Articulo buscarParaCambio(String nombre) {
        boolean encontrado = false;
        Articulo articuloEncontrado = null;

        for (Articulo n : listaProductos) {
            if (n.getNombre().equalsIgnoreCase(nombre)) {
                System.out.println("");
                System.out.println("--Articulo encontrado--");
                encontrado = true;
                articuloEncontrado = n;
                break;
            }
        }

        if (!encontrado) {
            System.out.println("Artículo no encontrado");

        }
        return articuloEncontrado;
    }

    public static void guardarySalir() {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("articulos.dat"))) {

            oos.writeObject(listaProductos);
            System.out.println("Articulos guardados en el archivo");

        } catch (IOException e) {
            System.out.println("Fallo al guardar los objetos: " + e.getMessage());
        }

    }

    @SuppressWarnings("unchecked")
    public static void cargarDatos() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("articulos.dat"))) {

            listaProductos = (ArrayList<Articulo>) ois.readObject();
            System.out.println("Artículos cargados correctamente desde el archivo");

        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo, empezando con lista vacía");
            listaProductos = new ArrayList<>();
        } catch (ClassNotFoundException e) {
            System.out.println("Error: El archivo está corrupto o ha sido modificado");
            listaProductos = new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Error de acceso al archivo: " + e.getMessage());
            listaProductos = new ArrayList<>();
        }

    }
}