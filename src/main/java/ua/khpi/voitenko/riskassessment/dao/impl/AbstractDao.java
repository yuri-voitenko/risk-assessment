package ua.khpi.voitenko.riskassessment.dao.impl;

import org.springframework.core.GenericTypeResolver;
import ua.khpi.voitenko.riskassessment.dao.CommonDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

import static java.lang.String.format;

@Transactional
public class AbstractDao<T> implements CommonDao<T> {

    private final Class<T> genericType;

    @PersistenceContext
    private EntityManager em;

    public EntityManager getEntityManager() {
        return em;
    }

    public AbstractDao() {
        this.genericType = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), AbstractDao.class);
    }

    @Override
    public List<T> findAll() {
        final String selectAll = format("select e from %s e", genericType.getSimpleName());
        return em.createQuery(selectAll, genericType).getResultList();
    }

    @Override
    public void insertOrUpdate(T entity) {
        em.merge(entity);
    }

    @Override
    public T findById(int id) {
        return em.getReference(genericType, id);
    }

    @Override
    public void delete(T entity) {
        em.remove(entity);
    }
}
