package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;
import model.validator.Notification;
import model.validator.UserValidator;
import service.user.AuthenticationService;
import view.CustomerView;
import view.LoginView;

import java.util.EventListener;
import java.util.List;

public class LoginController {

    private final LoginView loginView;
    private final AuthenticationService authenticationService;


    public LoginController(LoginView loginView, AuthenticationService authenticationService) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;

        this.loginView.addLoginButtonListener(new LoginButtonListener());
        this.loginView.addRegisterButtonListener(new RegisterButtonListener());
    }

    private class LoginButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(javafx.event.ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<User> loginNotification = authenticationService.login(username, password);

            if (loginNotification.hasErrors()){
                loginView.setActionTargetText(loginNotification.getFormattedErrors());
            }else{
                loginView.setActionTargetText("LogIn Successfull!");

                switchToCustomerView();
                //switch scenes
            }

        }
    }

    private class RegisterButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Boolean> registerNotification = authenticationService.register(username, password);

//            if (registerNotification.hasErrors()) {
//                loginView.setActionTargetText(registerNotification.getFormattedErrors());
//                //loginView.setActionTargetText(registerNotification.usernameTaken());
//               //loginView.setActionTargetText("Username taken!");
//            } else {
//                loginView.setActionTargetText("Register successful!");
//            }
            if (registerNotification.hasErrors()) {
                String errorMessage = registerNotification.getFormattedErrors();
                if (errorMessage.contains("Username taken!")) {
                    loginView.setActionTargetText("Username taken!");
                } else {
                    loginView.setActionTargetText(errorMessage);
                }
            } else {
                loginView.setActionTargetText("Register successful!");

            }
        }
    }

    private void switchToCustomerView(){
        Stage customerStage = new Stage();
        CustomerView customerView = new CustomerView(customerStage);
        new CustomerController(customerView);

        //Scene customerScene = new Scene(customerView 720, 480);
        //customerStage.setScene(customerScene);
        customerStage.show();

    }
}
