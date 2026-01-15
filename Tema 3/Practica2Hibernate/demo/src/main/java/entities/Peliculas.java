package entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity(name="Peliculas")
public class Peliculas{
@Id
@Column
@GeneratedValue(strategy= GenerationType.IDENTITY)
private long peliculaId;
private String titulo;
private String fecha_Estreno; 

@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER) 
private Set<Actores> actores;

@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER) 
private Set<Directores> directores;

public Peliculas(){}
public Peliculas(long pId, String tit, String estreno){
    this.peliculaId = pId;
    this.titulo = tit;
    this.fecha_Estreno = estreno;
}

public Peliculas(long pId, String tit, String estreno, Set<Actores> actores, Set<Directores> directores){
    this.peliculaId = pId;
    this.titulo = tit;
    this.fecha_Estreno = estreno;
    this.actores = actores;
}

public long getPeliculaId() {
    return peliculaId;
}

public void setPeliculaId(long peliculaId) {
    this.peliculaId = peliculaId;
}

public String getTitulo() {
    return titulo;
}

public void setTitulo(String titulo) {
    this.titulo = titulo;
}

public String getFecha_Estreno() {
    return fecha_Estreno;
}

public void setFecha_Estreno(String fecha_Estreno) {
    this.fecha_Estreno = fecha_Estreno;
}

public Set<Directores> getDirectores(){
    return directores;
}

public Set<Actores> getActores(){
    return actores;
}

public void addDirector(Directores d){
    if(directores == null){
        directores = new HashSet<Directores>();
    }
    directores.add(d);
}

public void addActor(Actores a){
    if(actores == null){
        actores = new HashSet<Actores>();
    }
    actores.add(a);
}
}