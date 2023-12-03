package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Book;
import model.User;
import service.user.AuthenticationService;
import view.AdminView;
import view.EmployeeView;

import java.util.ArrayList;
import java.util.List;

public class EmployeeController {


    private final EmployeeView employeeView;
    private final AuthenticationService authenticationService;
    private final List<User> selectedUsers;
    private int finishCounter =0;
    List<Book> addedToCartBooks = new ArrayList<>();


    public EmployeeController(EmployeeView employeeView, AuthenticationService authenticationService, List<User> selectedUsers) {
        this.selectedUsers = selectedUsers;
        this.employeeView = employeeView;
        this.authenticationService = authenticationService;

//        this.adminView.addLogOutButtonListener(new AdminController.LogOutHandler());
//        this.adminView.addCreateButtonListener(new AdminController.CreateButtonHandler());
//        this.adminView.addFinishButtonListener(new AdminController.FinishButtonHandler());
//        this.employeeView.addShowAllListener(new AdminController.ShowAllHandler());
    }

    private class LogOutHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            //loginView.logOut();
            employeeView.closeAdminWindow();
        }
    }

    private class ShowAllHandler implements EventHandler<ActionEvent> {

        private List<User> users;

        @Override
        public void handle(ActionEvent event) {
            users = authenticationService.findAll();
            System.out.println(users);
            employeeView.setUsersData(users);

        }
    }
}
