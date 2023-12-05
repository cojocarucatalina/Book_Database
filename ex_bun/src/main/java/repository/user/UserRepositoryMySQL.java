package repository.user;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validator.Notification;
import repository.security.RightsRolesRepository;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static database.Constants.Tables.USER;

public class UserRepositoryMySQL implements UserRepository {

    private final Connection connection;
    private final RightsRolesRepository rightsRolesRepository;
    private AuthenticationServiceMySQL authenticationService;


    public UserRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository) {
        //this.authenticationService =authenticationService;
        this.connection = connection;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM user;";

        List<User> users = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                users.add(getUserFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public List<Role> findAllRoles() {
        String sql = "SELECT * FROM user_role;";

        List<Role> users = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                //users.add(getUserFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }


    public boolean updateDatabase(Long id, String username, String password) {
        String updateSql = "UPDATE user SET password = ? WHERE id = ?";

        try {
            PreparedStatement updateStatement = connection.prepareStatement(updateSql);
            //updateStatement.setInt(1, quantity);
            updateStatement.setString(1, password);
            updateStatement.setLong(2, id);

            int rowsUpdated = updateStatement.executeUpdate();
            return (rowsUpdated != 1);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException{
        return new UserBuilder()
                .setId(resultSet.getLong("id"))
                .setUsername(resultSet.getString("username"))
                .setPassword(resultSet.getString("password"))
                //.setRoles(new java.sql.Date(resultSet.getDate("publishedDate").getTime()).toLocalDate())
                .build();
    }

    // SQL Injection Attacks should not work after fixing functions
    // Be careful that the last character in sql injection payload is an empty space
    // alexandru.ghiurutan95@gmail.com' and 1=1; --
    // ' or username LIKE '%admin%'; --

    @Override
    public Notification<User> findByUsernameAndPassword(String username, String password) {

        Notification<User> findByUsernameAndPasswordNotification = new Notification<>();

        try {
            Statement statement = connection.createStatement();

            String fetchUserSql =
                    "Select * from `" + USER + "` where `username`=\'" + username + "\' and `password`=\'" + password + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            if (userResultSet.next())
            {
                User user = new UserBuilder()
                        .setUsername(userResultSet.getString("username"))
                        .setPassword(userResultSet.getString("password"))
                        .setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getLong("id")))
                        .build();

                findByUsernameAndPasswordNotification.setResult(user);
            } else {
                findByUsernameAndPasswordNotification.addError("Invalid username or password!");
                return findByUsernameAndPasswordNotification;
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
            findByUsernameAndPasswordNotification.addError("Something is wrong with the Database!");
        }

        return findByUsernameAndPasswordNotification;
    }

    @Override
    public boolean save(User user) {
        Notification<User> findByUsernameAndPasswordNotification = new Notification<>();

        if (existsByUsername(user.getUsername())){
            System.out.println("Username exists");
            findByUsernameAndPassword("","");
            findByUsernameAndPasswordNotification.addError("Username taken!");

            //findByUsernameAndPasswordNotification.addError("Username taken!");
            return false;
        }
        try {
            //ana@are.mere

            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO user values (null, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, user.getUsername());
            insertUserStatement.setString(2, user.getPassword());
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long userId = rs.getLong(1);
            user.setId(userId);

            rightsRolesRepository.addRolesToUser(user, user.getRoles());

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from user where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Long id) {
        try {
            String sql = "DELETE FROM user WHERE id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> findAllEmployees() {
        String sql = "SELECT u.* FROM user u " +
                "JOIN user_role ur ON u.id = ur.user_id " +
                "WHERE ur.role_id = ?";

        //String sql = "SELECT user_id FROM user_role WHERE role_id = ?;";

        List<User> users = new ArrayList<>();

        long role_id = 2;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, role_id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                users.add(getUserFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
    @Override
    public User findById(Long id) {
        String sql = "SELECT * FROM user WHERE id = ?";
        User user = new User();

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                user = getUserFromResultSet(resultSet);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return user;
    }


    @Override
    public boolean existsByUsername(String email) {
        try {
            Statement statement = connection.createStatement();

            String fetchUserSql =
                    "Select * from `" + USER + "` where `username`=\'" + email + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            return userResultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean updateEmployeeUsername(String username, Long id) {
        String updateSql = "UPDATE user SET username = ? WHERE id = ?";

        try {
            PreparedStatement updateStatement = connection.prepareStatement(updateSql);
            updateStatement.setString(1, username);
            updateStatement.setLong(2, id);

            int rowsUpdated = updateStatement.executeUpdate();
            return (rowsUpdated != 1);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateEmployeePassword(String password, Long id) {
        String updateSql = "UPDATE user SET password = ? WHERE id = ?";

        try {
            PreparedStatement updateStatement = connection.prepareStatement(updateSql);
            updateStatement.setString(1, password);
            updateStatement.setLong(2, id);

            int rowsUpdated = updateStatement.executeUpdate();
            return (rowsUpdated != 1);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}