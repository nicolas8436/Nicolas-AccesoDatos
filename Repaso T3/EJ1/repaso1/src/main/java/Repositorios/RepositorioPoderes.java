package Repositorios;

import org.hibernate.*;
import Entities.Poderes;


public class RepositorioPoderes implements Repository<Poderes> {
    private final Session session;

    public RepositorioPoderes(Session session){
        this.session = session;
    }

    public void save(Poderes t){
        Transaction trx = session.beginTransaction();
        session.save(t);
        System.out.println("Poder guardado con id " + t.getId_poder());
        trx.commit();
    }

    public void update(Poderes t) {
        Transaction trx = session.beginTransaction();
        session.update(t);
        System.out.println("Poder actualizado con id " + t.getId_poder());
        trx.commit();
    }

    public void delete(Poderes t) {
        Transaction trx = session.beginTransaction();
        session.delete(t);
        System.out.println("Poder borrado con id " + t.getId_poder());
        trx.commit();
    }

    public Poderes findOneById(long id) {
        return session.get(Poderes.class, id);
    }

}
