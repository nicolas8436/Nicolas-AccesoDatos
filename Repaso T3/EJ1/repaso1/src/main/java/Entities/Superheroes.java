package Entities;

import java.util.List;
import javax.persistence.*;

@Entity(name = "Superheroes")
public class Superheroes {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Superheroe")
    private int id_superheroe;

    @Column(name = "Nombre")
    private String nombre_heroe;

    @Column(name = "Nivel")
    private int nivel;

    @ManyToOne
    @JoinColumn(name = "superheroe")
    private Equipos equipo;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "heroe_poder", 
                joinColumns = @JoinColumn(name = "superheroe"), inverseJoinColumns = @JoinColumn(name = "poder"))
    private List<Poderes> poder;


    public Superheroes(){}

    public int getId_superheroe() {
    return id_superheroe;
}

public void setId_superheroe(int id_superheroe) {
    this.id_superheroe = id_superheroe;
}

public String getNombre_heroe() {
    return nombre_heroe;
}

public void setNombre_heroe(String nombre_heroe) {
    this.nombre_heroe = nombre_heroe;
}

public int getNivel() {
    return nivel;
}

public void setNivel(int nivel) {
    this.nivel = nivel;
}

public Equipos getEquipo() {
    return equipo;
}

public void setEquipo(Equipos equipo) {
    this.equipo = equipo;
}

public List<Poderes> getPoder() {
    return poder;
}

public void setPoder(List<Poderes> poder) {
    this.poder = poder;
}
}