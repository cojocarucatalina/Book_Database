package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Book;
import model.User;
import model.validator.Notification;
import model.validator.ResultFetchException;
import model.validator.UserValidator;
import service.book.BookService;
import service.user.AuthenticationService;
import view.AdminView;
import view.CustomerView;
import view.EmployeeView;
import view.LoginView;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class LoginController {

    private final LoginView loginView;
   // private final CustomerView customerView;
    private final AuthenticationService authenticationService;
    private final BookService bookService;

    public LoginController(LoginView loginView, AuthenticationService authenticationService, BookService bookService) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
       // this.customerView = customerView;

        this.bookService = bookService;

        this.loginView.addLoginButtonListener(new LoginButtonListener());
        this.loginView.addRegisterButtonListener(new RegisterButtonListener());
    }

    private class LoginButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(javafx.event.ActionEvent event) {
            String username = loginView.getUsername();
            username = "admin@admin";
            username = "employee@here";
            String password = loginView.getPassword();
            password = "suntangajat1/";

            Notification<User> loginNotification = authenticationService.login(username, password);

            try{
            User user = loginNotification.getResult();
            } catch (ResultFetchException e){
                loginView.setActionTargetText("Authentification not successful!");
            }

            if (loginNotification.hasErrors()){
                loginView.setActionTargetText(loginNotification.getFormattedErrors());
            }else {

                loginView.setActionTargetText("LogIn Successfull!");
                if (loginNotification.getResult().getRoles().get(0).getRole().equals("customer")) {
                    System.out.println(loginNotification.getResult().getRoles().get(0).getRole());
                    loginView.closeLogInView();
                    switchToCustomerView();
                } else if (loginNotification.getResult().getRoles().get(0).getRole().equals("administrator"))  {
                    loginView.closeLogInView();
                    System.out.println(loginNotification.getResult().getRoles().get(0).getRole());
                    System.out.println("administrator");
                    switchToAdminView();
                }
                else {
                    loginView.closeLogInView();
                    System.out.println(loginNotification.getResult().getRoles().get(0).getRole());
                    System.out.println("employee");
                    switchToEmployeeView();
                }

            }

        }
    }

    private class RegisterButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Boolean> registerNotification = authenticationService.register(username, password);

            if (registerNotification.hasErrors()) {
                loginView.setActionTargetText(registerNotification.getFormattedErrors());
            } else {
                loginView.setActionTargetText("Register successful!");
            }

//            if (registerNotification.hasErrors()) {
//                String errorMessage = registerNotification.getFormattedErrors();
//                if (errorMessage.contains("Username taken!")) {
//                    loginView.setActionTargetText("Username taken!");
//                } else {
//                    loginView.setActionTargetText(errorMessage);
//                }
//            } else {
//                loginView.setActionTargetText("Register successful!");
//
//            }
        }
    }

    private void switchToCustomerView(){
        Stage customerStage = new Stage();

        CustomerView customerView = new CustomerView(customerStage);
        List<Book> selected = new ArrayList<>();
        CustomerController customerController = new CustomerController(customerView, bookService, selected);

        customerStage.show();

    }

    private void switchToAdminView(){
        Stage adminStage = new Stage();

        AdminView adminView = new AdminView(adminStage);
        List<User> selected = new ArrayList<>();
        AdminController adminController = new AdminController(adminView, authenticationService, selected);

        adminStage.show();

    }

    private void switchToEmployeeView(){
        Stage employeeStage = new Stage();

        EmployeeView employeeView = new EmployeeView(employeeStage);
        List<User> selected = new ArrayList<>();
        EmployeeController adminController = new EmployeeController(employeeView, authenticationService, selected);

        employeeStage.show();

    }
}
