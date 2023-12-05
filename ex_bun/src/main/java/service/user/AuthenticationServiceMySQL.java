package service.user;

import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validator.Notification;
import model.validator.UserValidator;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.util.Collections;
import java.util.List;

import static database.Constants.Roles.CUSTOMER;
import static database.Constants.Roles.EMPLOYEE;

public class AuthenticationServiceMySQL implements AuthenticationService {

    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final Connection connection;


    public AuthenticationServiceMySQL(UserRepository userRepository, RightsRolesRepository rightsRolesRepository, Connection connection) {
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
        this.connection = connection;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
    @Override
    public User findById(Long id) {
        return userRepository.findById(id);
    }
    public List<Role> findAllRoles(){
        return userRepository.findAllRoles();
    }

    public void remove(Long id){
        userRepository.remove(id);
    }

    public List<User> findAllEmployees(){
        return userRepository.findAllEmployees();
    }

    @Override
    public Notification<Boolean> register(String username, String password) {

        Role customerRole = rightsRolesRepository.findRoleByTitle(CUSTOMER);

        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(password)
                .setRoles(Collections.singletonList(customerRole))
                .build();

        UserValidator userValidator = new UserValidator(user);

        boolean userValid = userValidator.validate();
        Notification<Boolean> userRegisterNotification = new Notification<>();

        if (!userValid){
            userValidator.getErrors().forEach(userRegisterNotification::addError);
            userRegisterNotification.setResult(Boolean.FALSE);
        } else {
            user.setPassword(hashPassword(password));
            userRegisterNotification.setResult(userRepository.save(user));
        }

        return userRegisterNotification;
    }

    @Override
    public Notification<Boolean> registerEmployee(String username, String password) {

        Role customerRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);

        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(password)
                .setRoles(Collections.singletonList(customerRole))
                .build();

        UserValidator userValidator = new UserValidator(user);

        boolean userValid = userValidator.validateEmployee();
        Notification<Boolean> userRegisterNotification = new Notification<>();

        if (!userValid){
            userValidator.getErrors().forEach(userRegisterNotification::addError);
            userRegisterNotification.setResult(Boolean.FALSE);
        } else {
            user.setPassword(hashPassword(password));
            userRegisterNotification.setResult(userRepository.save(user));
        }

        return userRegisterNotification;
    }

    @Override
    public boolean updateEmployeeUsername(String username, Long id){
        return userRepository.updateEmployeeUsername(username,id);
    }

    @Override
    public boolean updateEmployeePassword(String password,  Long id){
        return userRepository.updateEmployeePassword(hashPassword(password),id);

    }


    @Override
    public Notification<User> login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, hashPassword(password));
    }

    @Override
    public boolean updateDatabase(Long id, String username, String password) {
        return userRepository.updateDatabase(id, username, hashPassword(password));
    }


    private String hashPassword(String password) {
        try {
            // Sercured Hash Algorithm - 256
            // 1 byte = 8 biți
            // 1 byte = 1 char
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
