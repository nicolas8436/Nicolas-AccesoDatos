package repositories;

import org.hibernate.Session;
import org.hibernate.Transaction;

import entities.Peliculas;

public class PeliculasRepository implements Repository<Peliculas> {
    private final Session session;

    public PeliculasRepository(Session sesion) {
        this.session = sesion;
    }

    @Override
    public void save(Peliculas t) {
        Transaction trx = session.beginTransaction();
        session.save(t);
        System.out.println("Pelicula guardada con id " + t.getPeliculaId());
        trx.commit();

    }

    @Override
    public Peliculas findOneById(long id) {
        Transaction trx = session.beginTransaction();
        Peliculas Peliculas = session.createQuery("FROM peliculas where peliculaId=:id", Peliculas.class).setParameter("id", id)
                .getSingleResult();
        trx.commit();
        return Peliculas;
    }

    @Override
    public void update(Peliculas t) {
        Transaction trx = session.beginTransaction();
        session.update(t);
        System.out.println("Pelicula actualizada con id " + t.getPeliculaId());
        trx.commit();
    }
    
    @Override
    public void delete(Peliculas t) {
        Transaction trx = session.beginTransaction();
        session.delete(t);
        System.out.println("Pelicula borrada con id " + t.getPeliculaId());
        trx.commit();
    }

}
