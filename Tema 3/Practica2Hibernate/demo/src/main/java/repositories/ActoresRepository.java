package repositories;

import org.hibernate.Session;
import org.hibernate.Transaction;

import entities.Actores;

public class ActoresRepository implements Repository<Actores> {
    private final Session session;

    public ActoresRepository(Session sesion) {
        this.session = sesion;
    }

    @Override
    public void save(Actores t) {
        Transaction trx = session.beginTransaction();
        session.save(t);
        System.out.println("Actor guardado con id " + t.getActorId());
        trx.commit();
    }

    @Override
    public Actores findOneById(long id) {
        Transaction trx = session.beginTransaction();
        Actores Actores = session.createQuery("FROM Actores WHERE actorId=:id", Actores.class).setParameter("id", id).getSingleResult();
        trx.commit();
        return Actores;
    }

    @Override
    public void update(Actores t) {
        Transaction trx = session.beginTransaction();
        session.update(t);
        System.out.println("Actor actualizado con id " + t.getActorId());
        trx.commit();
    }
    
    @Override
    public void delete(Actores t) {
        Transaction trx = session.beginTransaction();
        session.delete(t);
        System.out.println("Actor borrado con id " + t.getActorId());
        trx.commit();
    }

}
