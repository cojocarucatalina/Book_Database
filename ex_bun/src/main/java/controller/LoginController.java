package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Book;
import model.User;
import model.validator.Notification;
import model.validator.UserValidator;
import service.book.BookService;
import service.user.AuthenticationService;
import view.CustomerView;
import view.LoginView;

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
            username = "ana@are.mere";
            String password = loginView.getPassword();
            password="parola1/";

            Notification<User> loginNotification = authenticationService.login(username, password);

            User user = loginNotification.getResult();

            if (loginNotification.hasErrors()){
                loginView.setActionTargetText(loginNotification.getFormattedErrors());
            }else {

                loginView.setActionTargetText("LogIn Successfull!");
                if (loginNotification.getResult().getRoles().get(0).getRole().equals("customer")) {
                    loginView.closeLogInView();
                    switchToCustomerView();
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
        CustomerController customerController = new CustomerController(customerView, bookService);

        customerStage.show();

    }
}
