package repositories;

public interface Repository<T> {
    void save(T t);

    T findOneById(long id);

    void update(T t);

    void delete(T t);

}
