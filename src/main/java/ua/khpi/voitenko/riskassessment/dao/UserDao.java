package ua.khpi.voitenko.riskassessment.dao;

import ua.khpi.voitenko.riskassessment.model.User;

import java.util.List;

public interface UserDao extends CommonDao<User> {
    default List<User> findByName() {
        throw new UnsupportedOperationException();
    }

    User getUserByEmailAndPassword(String email, String password);
}
