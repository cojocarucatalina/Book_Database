package controller;

//admin@admin --> suntadmin1/

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Book;
import model.User;
import model.validator.Notification;
import service.book.BookService;
import service.user.AuthenticationService;
import view.AdminView;
import view.CustomerView;

import java.util.ArrayList;
import java.util.List;


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
        this.adminView.addShowAllListener(new AdminController.ShowAllHandler());
    }

    private class LogOutHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            //loginView.logOut();
            adminView.closeAdminWindow();
        }
    }

    private class ShowAllHandler implements EventHandler<ActionEvent> {

        private List<User> users;
        @Override
        public void handle(ActionEvent event) {
            users = authenticationService.findAll();
            System.out.println(users);
            adminView.setUsersData(users);

        }
    }

    public class DeleteButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
        }
    }

    public class CreateButtonHandler implements EventHandler<ActionEvent> {

        String password = "defaultpass1/";
        @Override
        public void handle(ActionEvent event) {

            List<User> users;
            users = authenticationService.findAll();

            String username = adminView.getUsername();
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
        }
    }

    public class UpdateButtonHandler implements EventHandler<ActionEvent> {

        String username = adminView.getUsername();
        @Override
        public void handle(ActionEvent event) {


        }
    }
}
