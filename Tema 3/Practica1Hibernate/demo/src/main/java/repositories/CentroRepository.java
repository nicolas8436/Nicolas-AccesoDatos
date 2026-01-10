package repositories;

import org.hibernate.Session;
import org.hibernate.Transaction;
import entities.Centro;

public class CentroRepository implements Repository<Centro> {
    private final Session session;

    public CentroRepository(Session sesion) {
        this.session = sesion;
    }

    @Override
    public void save(Centro t) {
        Transaction trx = session.beginTransaction();
        session.save(t);
        System.out.println("Centro guardado con id " + t.getCentroId());
        trx.commit();
    }

    @Override
    public Centro findOneById(long id) {
        Transaction trx = session.beginTransaction();
        Centro centro = session.createQuery("FROM centros where centroId=:id", Centro.class).setParameter("id", id)
                .getSingleResult();
        trx.commit();
        return centro;
    }

    @Override
    public void update(Centro t) {
        Transaction trx = session.beginTransaction();
        session.update(t);
        System.out.println("Centro actualizado con id " + t.getCentroId());
        trx.commit();
    }

    @Override
    public void delete(Centro t) {
        Transaction trx = session.beginTransaction();
        session.delete(t);
        System.out.println("Centro borrado con id " + t.getCentroId());
        trx.commit();
    }

}
