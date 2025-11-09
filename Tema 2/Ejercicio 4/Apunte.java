public class Apunte {
    private int id;
    private String producto;
    private double cantidad;
    private double precioUnidad;

    public Apunte(int id, String producto, double cantidad, double precioUnidad) {
        this.id = id;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnidad = precioUnidad;
    }

    public int getId() {
        return id;
    }

    public String getProducto() {
        return producto;
    }

    public double getCantidad() {
        return cantidad;
    }

    public double getPrecioUnidad() {
        return precioUnidad;
    }

    public double getImporte() {
        return cantidad * precioUnidad;
    }
}