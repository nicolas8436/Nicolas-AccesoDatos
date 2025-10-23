import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Motor {
    private String combustible;
    private int cilindros;
    private int potencia;

    // Constructores
    public Motor() {
    }

    public Motor(String combustible, int cilindros, int potencia) {
        this.combustible = combustible;
        this.cilindros = cilindros;
        this.potencia = potencia;
    }

    // Getters y Setters
    public String getCombustible() {
        return combustible;
    }

    public void setCombustible(String combustible) {
        this.combustible = combustible;
    }

    public int getCilindros() {
        return cilindros;
    }

    public void setCilindros(int cilindros) {
        this.cilindros = cilindros;
    }

    @XmlAttribute
    public int getPotencia() {
        return potencia;
    }

    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }

    @Override
    public String toString() {
        return "Motor{combustible='" + combustible + "', cilindros=" + cilindros + ", potencia=" + potencia + "}";
    }
}