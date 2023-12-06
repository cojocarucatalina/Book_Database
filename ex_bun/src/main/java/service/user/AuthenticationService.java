package service.user;

import model.Role;
import model.User;
import model.validator.Notification;

import java.util.List;

public interface AuthenticationService {
    Notification<Boolean> register(String username, String password);
    Notification<Boolean> registerEmployee(String username, String password);
    //Notification<Boolean> updateEmployee(String username, String password);
    boolean updateEmployeeUsername(String username, Long id);

    boolean updateEmployeePassword(String password,  Long id);

    List<User> findAll();
    User findById(Long id);
    void remove(Long id);

    List<User> findAllEmployees() ;

    Notification<User> login(String username, String password);

    boolean updateDatabase(Long id, String username, String password);

    List<Role> findAllRoles();
}
