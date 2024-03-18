package hiber.service;

import hiber.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {


    void saveUser(User user);

    User findUserById(Long id);

    List<User> findAllUsers();

    void updateUser(User user);

    void deleteUserById(Long id);

    UserDetails loadUserByEmail(String email);

    Long getUserIdByEmail(String email);

    void getUserAndRoles(User user, String[] roles);

    void getNotNullRole(User user);

}
