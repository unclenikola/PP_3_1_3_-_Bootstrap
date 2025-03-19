package ru.unclenikola.springboot.PP_3_1_1_Spring_MVC_Hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.unclenikola.springboot.PP_3_1_1_Spring_MVC_Hibernate.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
//    List<User> id(Long id);
}
