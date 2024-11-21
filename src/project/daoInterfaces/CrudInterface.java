package project.daoInterfaces;

import java.util.List;
import java.util.Optional;

public interface CrudInterface<K, E> {
    boolean delete(K id);

    E save(E entity);

    void update(E entity);

    Optional<E> findById(K id);

    List<E> findAll();
}
