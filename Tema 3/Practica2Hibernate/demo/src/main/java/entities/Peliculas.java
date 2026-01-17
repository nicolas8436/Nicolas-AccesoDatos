package entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity(name="Peliculas")
public class Peliculas{
@Id
@Column
@GeneratedValue(strategy= GenerationType.AUTO)
private long peliculaId;
private String titulo;
private String fecha_Estreno; 

@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER) 
private Set<Actores> actores;

@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "DIRECTOR_ID")  // Columna FK en tabla PELICULAS
    private Directores director;

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

public Directores getdirector(){
    return director;
}

public Set<Actores> getActores(){
    return actores;
}

public void setDirector(Directores d){
   this.director = d;
}

public void addActor(Actores a){
    if(actores == null){
        actores = new HashSet<Actores>();
    }
    actores.add(a);
}
}