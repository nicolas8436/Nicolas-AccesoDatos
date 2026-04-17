package Entities;

import java.util.List;

import javax.persistence.*;

@Entity(name = "Poderes")
public class Poderes{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Poder")
    private int id_poder;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Descripcion")
    private String descripcion;

    @Column(name = "Tipo")
    private String tipo;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "poder")//Maped by se pone el campo al que hace referencia en la otra clase es decir
    private List<Superheroes> superheroe;                     //Este va a el atributo poder de la clase superheroe
                                                              //Y en la clase Superheroes apunta a superheroe que es este atributo
    public Poderes(){}

    public int getId_poder() {
    return id_poder;
}

public void setId_poder(int id_poder) {
    this.id_poder = id_poder;
}

public String getNombre() {
    return nombre;
}

public void setNombre(String nombre) {
    this.nombre = nombre;
}

public String getDescripcion() {
    return descripcion;
}

public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
}

public String getTipo() {
    return tipo;
}

public void setTipo(String tipo) {
    this.tipo = tipo;
}

public List<Superheroes> getSuperheroe() {
    return superheroe;
}

public void setSuperheroe(List<Superheroes> superheroe) {
    this.superheroe = superheroe;
}
}
