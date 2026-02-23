package entities;

import java.util.List;

import javax.persistence.CascadeType;
//import java.lang.annotation.Inherited;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity(name = "modulos")
public class Modulo {
    @Id
    @Column(name = "modulo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long moduloId;
    private String nombre;
    private String abreviatura;
    @ManyToMany(cascade=CascadeType.DETACH, mappedBy="modulos")
    private List<Alumno> alumnos;
    public Modulo() {
    }

    public Modulo(String nombre, String abreviatura) {
        this.nombre = nombre;
        this.abreviatura = abreviatura;
    }

    // ModuloId
    public long getModuloId() {
        return moduloId;
    }

    public void setModuloId(long ModuloId) {
        this.moduloId = ModuloId;
    }

    // Abreviatura
    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    // Nombre
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
