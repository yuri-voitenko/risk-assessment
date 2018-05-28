package ua.khpi.voitenko.riskassessment.service.impl;

import org.springframework.stereotype.Service;
import ua.khpi.voitenko.riskassessment.dao.UserDao;
import ua.khpi.voitenko.riskassessment.model.User;
import ua.khpi.voitenko.riskassessment.service.UserService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public List<User> findAllUsers() {
        return userDao.findAll();
    }

    @Override
    public User login(final String email, final String password) {
        return userDao.getUserByEmailAndPassword(email, password);
    }
}
