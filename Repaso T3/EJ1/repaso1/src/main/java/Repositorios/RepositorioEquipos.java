package Repositorios;

import org.hibernate.*;
import Entities.Equipos;


public class RepositorioEquipos implements Repository<Equipos> {
    private final Session session;

    public RepositorioEquipos(Session session){
        this.session = session;
    }

    public void save(Equipos t){
        Transaction trx = session.beginTransaction();
        session.save(t);
        System.out.println("Equipo guardado con id " + t.getId_equipo());
        trx.commit();
    }

    public void update(Equipos t) {
        Transaction trx = session.beginTransaction();
        session.update(t);
        System.out.println("Equipo actualizado con id " + t.getId_equipo());
        trx.commit();
    }

    public void delete(Equipos t) {
        Transaction trx = session.beginTransaction();
        session.delete(t);
        System.out.println("Equipo borrado con id " + t.getId_equipo());
        trx.commit();
    }

    public Equipos findOneById(long id) {
        return session.get(Equipos.class, id);
    }

}
