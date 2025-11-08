import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "numero", "tipo" })
public class Ruedas {
    private int numero;
    private String tipo;

    // Constructores
    public Ruedas() {
    }

    public Ruedas(int numero, String tipo) {
        this.numero = numero;
        this.tipo = tipo;
    }

    // Getters y Setters
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Ruedas{numero=" + numero + ", tipo='" + tipo + "'}";
    }
}