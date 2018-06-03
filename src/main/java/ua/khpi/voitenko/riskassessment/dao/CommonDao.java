package ua.khpi.voitenko.riskassessment.dao;

import java.util.List;

public interface CommonDao<T> {

    default List<T> findAll() {
        throw new UnsupportedOperationException();
    }

    default T findById(int id) {
        throw new UnsupportedOperationException();
    }

    default void insertOrUpdate(T entity) {
        throw new UnsupportedOperationException();
    }

    default boolean update(T entity) {
        throw new UnsupportedOperationException();
    }

    default void delete(T entity) {
        throw new UnsupportedOperationException();
    }
}
