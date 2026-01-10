package repositories;

import org.hibernate.Session;
import org.hibernate.Transaction;

import entities.Modulo;

public class ModuloRepository implements Repository<Modulo> {
    private final Session session;

    public ModuloRepository(Session sesion) {
        this.session = sesion;
    }

    public void save(Modulo t) {
        Transaction trx = session.beginTransaction();
        session.save(t);
        System.out.println("Centro guardado con id " + t.getModuloId());
        trx.commit();

    }

    public Modulo findOneById(long id) {
        Transaction trx = session.beginTransaction();
        Modulo modulo = session.createQuery("FROM centros where moduloId=:id", Modulo.class).setParameter("id", id)
                .getSingleResult();
        trx.commit();
        return modulo;
    }

    public void update(Modulo t) {
        Transaction trx = session.beginTransaction();
        session.update(t);
        System.out.println("Centro actualizado con id " + t.getModuloId());
        trx.commit();
    }

    public void delete(Modulo t) {
        Transaction trx = session.beginTransaction();
        session.delete(t);
        System.out.println("Centro borrado con id " + t.getModuloId());
        trx.commit();
    }

}
