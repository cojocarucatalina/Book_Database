package controller;

//admin@admin --> suntadmin1/

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Book;
import model.Role;
import model.User;
import model.validator.Notification;
import service.user.AuthenticationService;
import view.AdminView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AdminController {

    private final AdminView adminView;
    private final AuthenticationService authenticationService;
    private final List<String> selectedUsers;
    private int finishCounter =0;

    private List<String> updates;
    private List<Book> addedToCartBooks = new ArrayList<>();
    private final String pdfFilePath = "Raport admin" +  ".pdf";




    public AdminController(AdminView adminView, AuthenticationService authenticationService, List<String> selectedUsers) {
        this.selectedUsers = selectedUsers;
        this.adminView = adminView;
        this.authenticationService = authenticationService;

        this.adminView.addLogOutButtonListener(new AdminController.LogOutHandler());
        this.adminView.addCreateButtonListener(new AdminController.CreateButtonHandler());
        this.adminView.addUpdateButtonListener(new AdminController.UpdateButtonHandler());
        this.adminView.addDeleteButtonListener(new AdminController.DeleteButtonHandler());
        this.adminView.addRetrieveButtonListener(new AdminController.RetrieveHandler());
        this.adminView.addShowEmployees(new AdminController.ShowEmployees());
        this.adminView.addShowAll(new AdminController.ShowAll());
    }

    private class LogOutHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            //loginView.logOut();

            try {
                PdfWriter writer = new PdfWriter(pdfFilePath);
                PdfDocument pdfDocument = new PdfDocument(writer);
                Document document = new Document(pdfDocument);

                document.add(new Paragraph("USER UPDATES RAPORT\n\n\n"));


                for (String user : selectedUsers) {
                    document.add(new Paragraph(user));
                    //document.add(new Paragraph("Employee id: " + employeeView.getEmployeeId()));
                    document.add(new Paragraph("*   *   **   *   **   *   **   *   **   *   *"));
                }

                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            adminView.closeAdminWindow();
        }
    }

    private class RetrieveHandler implements EventHandler<ActionEvent> {

        //Optional<User> optionalUser;
        User optionalUser;
        List<User> userData = new ArrayList<User>() ;
        private List<User> userRole;
        @Override
        public void handle(ActionEvent event) {

            userRole = authenticationService.findAllEmployees();

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

    private class ShowEmployees implements EventHandler<ActionEvent> {

        private List<User> users;
        private List<Role> userRole;
        @Override
        public void handle(ActionEvent event) {
            users = authenticationService.findAllEmployees();
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


    private class ShowAll implements EventHandler<ActionEvent> {

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

            try {

                User selectedUser = adminView.getSelectedUser();

                String delete = "User with id " + selectedUser.getId() + " got deleted.\n";

                System.out.println(selectedUser.getId());
                authenticationService.remove(selectedUser.getId());

                selectedUsers.add(delete);

                List<User> users = authenticationService.findAllEmployees();
                adminView.setUsersData(users);
            } catch(NullPointerException nullPointerException){
                adminView.setActionTargetText("Select user to delete!");

            }

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
                    String create = "User with username "+username+" got deleted.\n";
                    selectedUsers.add(create);
                    adminView.setActionTargetText("Register successful!");
                }


                List<User> usersUpdatedList = authenticationService.findAllEmployees();
                adminView.setUsersData(usersUpdatedList);
            }

        }
    }

    public class UpdateButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {

            User user = adminView.getSelectedUser();

            try {
                String username = user.getUsername();

                String newUsername = adminView.getUsername();
                String password = adminView.getPassword();
                Long id = user.getId();

                System.out.println(username);
                System.out.println(password);
                System.out.println(id);

                try{
                    //selectedBook.getId()!=0
                    id = user.getId();
                }
                catch(NullPointerException nul){
                    adminView.setActionTargetText("Could not update! Select user!");

                }

                if ((id == 0)){
                    adminView.setActionTargetText("Could not update! Id not valid!");
                }
                else {

                    if (!newUsername.isEmpty()) {
                        authenticationService.updateEmployeeUsername(newUsername, id);
                    }
                    if (!password.isEmpty()) {
                        authenticationService.updateEmployeePassword(password, id);
                    }
                    List<User> users = authenticationService.findAllEmployees();
                    adminView.setUsersData(users);

                    String update = "User with id " + id + " got updated to " + newUsername + "\n";
                    selectedUsers.add(update);

                    adminView.setActionTargetText("Updated!");
                }
            } catch(NullPointerException pointer){
                    adminView.setActionTargetText("Select user to update!");
                }


//            try {
//                if (!user.toString().isEmpty()) {
//
//                    if (!newUsername.isEmpty()) {
//
//                        boolean registerNotification = authenticationService.updateEmployeeUsername(newUsername, id);
//                        adminView.setActionTargetText("Username updated!");
//
//                        if (!password.isEmpty()) {
//
//                            if (user.toString().isEmpty()) {
//
//                                adminView.setActionTargetText("Select an employee!");
//
//                            }
//                            boolean registerNotificationTwo = authenticationService.updateEmployeeUsername(newUsername, id);
//                            adminView.setActionTargetText("Password and Username updated!");
//
//                        }
//                    }
//                    else if (!password.isEmpty()){
//                        boolean registerNotificationTwo = authenticationService.updateEmployeePassword(password, id);
//                        adminView.setActionTargetText("Password updated!");
//                    }
//                }
//
//                List<User> usersUpdatedList = authenticationService.findAllEmployees();
//                adminView.setUsersData(usersUpdatedList);
//
//
//            } catch (NullPointerException nul) {
//
//                adminView.setActionTargetText("Select an employee!");
//            }
        }
    }
}
