package repositories;

import org.hibernate.Session;
import org.hibernate.Transaction;

import entities.Alumno;

public class AlumnoRepository implements Repository<Alumno> {
    private final Session session;

    public AlumnoRepository(Session sesion) {
        this.session = sesion;
    }

    public void save(Alumno t) {
        Transaction trx = session.beginTransaction();
        session.save(t);
        System.out.println("Centro guardado con id " + t.getPersonaId());
        trx.commit();
    }

    public Alumno findOneById(long id) {
        Transaction trx = session.beginTransaction();
        Alumno alumno = session.createQuery("FROM alumnos", Alumno.class).setParameter("id", id).getSingleResult();
        trx.commit();
        return alumno;
    }

    public void update(Alumno t) {
        Transaction trx = session.beginTransaction();
        session.update(t);
        System.out.println("Centro actualizado con id " + t.getPersonaId());
        trx.commit();
    }

    public void delete(Alumno t) {
        Transaction trx = session.beginTransaction();
        session.delete(t);
        System.out.println("Centro borrado con id " + t.getPersonaId());
        trx.commit();
    }

}
