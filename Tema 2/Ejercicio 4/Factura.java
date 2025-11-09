import java.util.*;

public class Factura {
    private int numero;
    private String nombreCliente;
    private List<Apunte> apuntes = new ArrayList<>();

    public Factura(int numero, String nombreCliente) {
        this.numero = numero;
        this.nombreCliente = nombreCliente;
    }

    public void addApunte(Apunte a) {
        apuntes.add(a);
    }

    public int getNumero() {
        return numero;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public List<Apunte> getApuntes() {
        return apuntes;
    }
}
