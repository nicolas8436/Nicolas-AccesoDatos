package entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity(name="Actores")
public class Actores{
@Id
@Column
@GeneratedValue(strategy= GenerationType.IDENTITY)
private long actorId;
private String nombre;
private String fecha_Nac;

@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER) 
private List<Peliculas> peliculas;

public Actores(){}
public Actores(long aId, String nombre, String fecha_Nac){
    this.actorId = aId;
    this.nombre = nombre;
    this.fecha_Nac = fecha_Nac;
}

public Actores(long aId, String nombre, String fecha_Nac, List<Peliculas> peliculas){
    this.actorId = aId;
    this.nombre = nombre;
    this.fecha_Nac = fecha_Nac;
    this.peliculas = peliculas;
}


public long getActorId() {
    return actorId;
}

public void setActorId(long actorId) {
    this.actorId = actorId;
}

public String getNombre() {
    return nombre;
}

public void setNombre(String nombre) {
    this.nombre = nombre;
}

public String getFecha_Nac() {
    return fecha_Nac;
}

public void setFecha_Nac(String fecha_Nac) {
    this.fecha_Nac = fecha_Nac;
}

    public List<Peliculas> getPelis() {
        return peliculas;
    }

    public void adPeli(Peliculas pelicula) {
        if (peliculas == null) {
            peliculas = new ArrayList<Peliculas>();

        }

        peliculas.add(pelicula);
    }
}