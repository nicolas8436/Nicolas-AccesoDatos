package repositories;

import org.hibernate.Session;
import org.hibernate.Transaction;
import entities.Directores;

public class DirectoresRepository implements Repository<Directores> {
    private final Session session;

    public DirectoresRepository(Session sesion) {
        this.session = sesion;
    }

    @Override
    public void save(Directores t) {
        Transaction trx = session.beginTransaction();
        session.save(t);
        System.out.println("Director guardado con id " + t.getDirectorId());
        trx.commit();
    }

    @Override
    public Directores findOneById(long id) {
        Transaction trx = session.beginTransaction();
        Directores Directores = session.createQuery("FROM Directores where directorId=:id", Directores.class).setParameter("id", id)
                .getSingleResult();
        trx.commit();
        return Directores;
    }

    @Override
    public void update(Directores t) {
        Transaction trx = session.beginTransaction();
        session.update(t);
        System.out.println("Director actualizado con id " + t.getDirectorId());
        trx.commit();
    }

    @Override
    public void delete(Directores t) {
        Transaction trx = session.beginTransaction();
        session.delete(t);
        System.out.println("Director borrado con id " + t.getDirectorId());
        trx.commit();
    }

}
