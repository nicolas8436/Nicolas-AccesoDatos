import java.io.Serializable;

public class Empleado implements Serializable {
    private int codigo;
    private String nombre;
    private String direccion;
    private float salario;
    private float comision;

    public Empleado(int codigo, String nombre, String direccion, float salario, float comision) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.direccion = direccion;
        this.salario = salario;
        this.comision = comision;
    }

    // Getters
    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public float getSalario() {
        return salario;
    }

    public float getComision() {
        return comision;
    }
}