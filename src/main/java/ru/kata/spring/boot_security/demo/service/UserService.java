package ru.unclenikola.springboot.PP_3_1_1_Spring_MVC_Hibernate.service;



import ru.unclenikola.springboot.PP_3_1_1_Spring_MVC_Hibernate.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserById(Long id);

    void addUser(User user);

    void updateUser(User user);

    void deleteUser(Long id);
}


