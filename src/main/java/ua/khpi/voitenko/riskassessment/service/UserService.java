package ua.khpi.voitenko.riskassessment.service;

import ua.khpi.voitenko.riskassessment.model.User;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();

    User login(String email, String password);

    User getAdminUser();
}
