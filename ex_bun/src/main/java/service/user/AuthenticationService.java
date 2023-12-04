package service.user;

import model.Book;
import model.Role;
import model.User;
import model.validator.Notification;

import java.util.List;
import java.util.Optional;

public interface AuthenticationService {
    Notification<Boolean> register(String username, String password);
    Notification<Boolean> registerEmployee(String username, String password);
    //Notification<Boolean> updateEmployee(String username, String password);
    boolean updateEmployee(String username, String password);
    List<User> findAll();
    User findById(Long id);
    void remove(Long id);

    Notification<User> login(String username, String password);

    boolean updateDatabase(Long id, String username, String password);

    List<Role> findAllRoles();
}
