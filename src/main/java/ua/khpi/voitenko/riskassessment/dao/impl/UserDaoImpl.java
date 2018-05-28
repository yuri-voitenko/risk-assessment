package ua.khpi.voitenko.riskassessment.dao.impl;

import org.springframework.stereotype.Repository;
import ua.khpi.voitenko.riskassessment.dao.UserDao;
import ua.khpi.voitenko.riskassessment.model.User;

import javax.persistence.NoResultException;

@Repository
public class UserDaoImpl extends AbstractDao<User> implements UserDao {
    @Override
    public User getUserByEmailAndPassword(final String email, final String password) {
        try {
            return getEntityManager()
                    .createQuery("SELECT u from User u WHERE u.email = :email AND u.password=MD5(:pas)", User.class).
                            setParameter("email", email)
                    .setParameter("pas", password).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
