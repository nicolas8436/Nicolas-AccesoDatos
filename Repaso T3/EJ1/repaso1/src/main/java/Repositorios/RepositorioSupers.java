package Repositorios;

import org.hibernate.*;
import Entities.Superheroes;


public class RepositorioSupers implements Repository<Superheroes> {
    private final Session session;

    public RepositorioSupers(Session session){
        this.session = session;
    }

    public void save(Superheroes t){
        Transaction trx = session.beginTransaction();
        session.save(t);
        System.out.println("Superheroe guardado con id " + t.getId_superheroe());
        trx.commit();
    }

    public void update(Superheroes t) {
        Transaction trx = session.beginTransaction();
        session.update(t);
        System.out.println("Superheroe actualizado con id " + t.getId_superheroe());
        trx.commit();
    }

    public void delete(Superheroes t) {
        Transaction trx = session.beginTransaction();
        session.delete(t);
        System.out.println("Superheroe borrado con id " + t.getId_superheroe());
        trx.commit();
    }

    public Superheroes findOneById(long id) {
        return session.get(Superheroes.class, id);
    }

}
