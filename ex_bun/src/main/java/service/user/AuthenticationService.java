package service.user;

import model.Book;
import model.Role;
import model.User;
import model.validator.Notification;

import java.util.List;

public interface AuthenticationService {
    Notification<Boolean> register(String username, String password);
    Notification<Boolean> registerEmployee(String username, String password);
    //Notification<Boolean> updateEmployee(String username, String password);
    boolean updateEmployee(String username, String password);
    List<User> findAll();

    void remove(Long id);

    Notification<User> login(String username, String password);

    boolean updateDatabase(Long id, String username, String password);

    List<Role> findAllRoles();
}
