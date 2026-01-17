package entities;

/*import java.util.ArrayList;
import java.util.List;*/

import javax.persistence.*;


@Entity(name="Directores")
public class Directores{
@Id
@Column
@GeneratedValue(strategy= GenerationType.AUTO)
private long directorId;
private String nombre;
private String fecha_Nac; 

@OneToOne(mappedBy = "director", fetch = FetchType.LAZY)
private Peliculas pelicula;

/*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER) 
private List<Peliculas> peliculas;*/

public Directores(){}
public Directores(long pId, String nombre, String fecha_Nac){
    this.directorId = pId;
    this.nombre = nombre;
    this.fecha_Nac = fecha_Nac;
}

/*public Directores(long pId, String nombre, String fecha_Nac, List<Peliculas> peliculas){
    this.directorId = pId;
    this.nombre = nombre;
    this.fecha_Nac = fecha_Nac;
    this.peliculas=peliculas;
}*/


public long getDirectorId() {
    return directorId;
}

public void setDirectorId(long directorId) {
    this.directorId = directorId;
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

public Peliculas getPelicula() { return pelicula; }
public void setPelicula(Peliculas pelicula) { this.pelicula = pelicula; }

/*public List<Peliculas> getPeliculas() {
    return peliculas;
}

public void addPeliculas(Peliculas pelicula) {
    if (peliculas == null){
        peliculas = new ArrayList<Peliculas>();
    }

    peliculas.add(pelicula);
}*/
}

