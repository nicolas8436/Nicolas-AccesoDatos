package entities;

import javax.persistence.*;

@Entity(name = "Centros")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "nombre", name = "nombreUniqueConstraint"))
public class Centro {
    @Id
    @Column(name = "Centro_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long centroId;
    private String nombre;

    public Centro() {
    }

    public Centro(String nombre) {
        this.nombre = nombre;
    }

    public void setCentroId(long centroId) {
        this.centroId = centroId;
    }

    public long getCentroId() {
        return centroId;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
