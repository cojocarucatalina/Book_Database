package controller;

//admin@admin --> suntadmin1/

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Book;
import model.Role;
import model.User;
import model.validator.Notification;
import service.user.AuthenticationService;
import view.AdminView;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


public class AdminController {

    private final AdminView adminView;
    private final AuthenticationService authenticationService;
    private final List<User> selectedUsers;
    private int finishCounter =0;
    List<Book> addedToCartBooks = new ArrayList<>();


    public AdminController(AdminView adminView, AuthenticationService authenticationService, List<User> selectedUsers) {
        this.selectedUsers = selectedUsers;
        this.adminView = adminView;
        this.authenticationService = authenticationService;

        this.adminView.addLogOutButtonListener(new AdminController.LogOutHandler());
        this.adminView.addCreateButtonListener(new AdminController.CreateButtonHandler());
        this.adminView.addUpdateButtonListener(new AdminController.UpdateButtonHandler());
        this.adminView.addDeleteButtonListener(new AdminController.DeleteButtonHandler());
        this.adminView.addRetrieveButtonListener(new AdminController.RetrieveHandler());
        this.adminView.addShowAllListener(new AdminController.ShowAllHandler());
    }

    private class LogOutHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            //loginView.logOut();
            adminView.closeAdminWindow();
        }
    }

    private class RetrieveHandler implements EventHandler<ActionEvent> {

        //Optional<User> optionalUser;
        User optionalUser;
        List<User> userData = new ArrayList<User>() ;
        @Override
        public void handle(ActionEvent event) {

            Long id = adminView.getId();
            optionalUser = authenticationService.findById(id);
            userData.clear();
            userData.add(optionalUser);
            String checkForNull = optionalUser.toString();
            if (checkForNull == null){
                adminView.setActionTargetText("Id not found!");
            }
            System.out.println(optionalUser);
            adminView.setUsersDataOptional(userData);

        }
    }

    private class ShowAllHandler implements EventHandler<ActionEvent> {

        private List<User> users;
        private List<Role> userRole;
        @Override
        public void handle(ActionEvent event) {
            users = authenticationService.findAll();
            userRole = authenticationService.findAllRoles();
            System.out.println(users);
            for (User user: users){
//                Notification<User> loginNotification = authenticationService.login(user.getUsername(), user.getPassword());
//                System.out.println(user.getUsername()+" and "+ user.getPassword());
//                if((loginNotification.getResult().getRoles().get(0).getRole().equals("customer")) || (loginNotification.getResult().getRoles().get(0).getRole().equals("administrator"))){
//                    users.remove(user);
//                }
                System.out.println(user.getId());
            }
            System.out.println(users);
            adminView.setUsersData(users);

        }
    }

    public class DeleteButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {

            User selectedUser = adminView.getSelectedUser();

            System.out.println(selectedUser.getId());
            authenticationService.remove(selectedUser.getId());

            List<User> users = authenticationService.findAll();
            adminView.setUsersData(users);

        }
    }

    public class CreateButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {

            List<User> users;
            users = authenticationService.findAll();

            String username = adminView.getUsername();
            String password = adminView.getPassword();
            System.out.println(username);
            Notification<Boolean> registerNotification = authenticationService.registerEmployee(username, password);
            int verification = 0;
            for (User element : users){
                if (element.getUsername().equals(username)) {
                    System.out.println(element.getUsername());
                    System.out.println(username);
                    adminView.setActionTargetText("Username taken!");
                    verification = 1;
                }
            }
            if (registerNotification.hasErrors()) {
                adminView.setActionTargetText(registerNotification.getFormattedErrors());
                //if (registerNotification.getFormattedErrors())
            } else {
                if(verification == 0) {
                    adminView.setActionTargetText("Register successful!");
                }
            }

            List<User> usersUpdatedList = authenticationService.findAll();
            adminView.setUsersData(usersUpdatedList);
        }
    }

    public class UpdateButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {

            List<User> users;
            users = authenticationService.findAll();

            String username = adminView.getUsername();
            String password = adminView.getPassword();
            System.out.println(username);
            boolean registerNotification = authenticationService.updateEmployee(username, password);

            if (registerNotification){
                adminView.setActionTargetText("Something went wrong!");
            }
            else {
                adminView.setActionTargetText("Password updated!");
            }

        }
    }
}
