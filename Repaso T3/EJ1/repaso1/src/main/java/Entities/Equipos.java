package Entities;

import java.util.List;
import javax.persistence.*;

@Entity(name = "Equipos")
public class Equipos
{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Equipo")
    private int id_equipo;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Base_Secreta")
    private String base_secreta;

    @OneToMany(mappedBy ="id_equipo", cascade = CascadeType.ALL)
    private List<Superheroes> superheroe;

    public Equipos(){}

    public int getId_equipo() {
    return id_equipo;
}

public void setId_equipo(int id_equipo) {
    this.id_equipo = id_equipo;
}

public String getNombre() {
    return nombre;
}

public void setNombre(String nombre) {
    this.nombre = nombre;
}

public String getBase_secreta() {
    return base_secreta;
}

public void setBase_secreta(String base_secreta) {
    this.base_secreta = base_secreta;
}

public List<Superheroes> getSuperheroe() {
    return superheroe;
}

public void setSuperheroe(List<Superheroes> superheroe) {
    this.superheroe = superheroe;
}

}