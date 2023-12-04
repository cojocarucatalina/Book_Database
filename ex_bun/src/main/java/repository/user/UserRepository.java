package repository.user;

import model.Role;
import model.User;
import model.validator.Notification;

import java.util.*;

public interface UserRepository {

    List<User> findAll();

    Notification<User> findByUsernameAndPassword(String username, String password);

    boolean save(User user);

    void removeAll();

    boolean existsByUsername(String username);

    boolean updateDatabase(Long id, String username, String password) ;

    List<Role> findAllRoles();

    void remove(Long id);
}
