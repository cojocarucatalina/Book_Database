package view;

//admin@admin --> suntadmin1/

import controller.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import launcher.ComponentFactory;
import model.Book;
import model.User;
import service.book.BookService;
import service.user.AuthenticationService;

import java.util.List;

public class AdminView {

    private Button logOutButton;
    private TableView<User> userTableView;
    private Button retrieveButton;
    private Button deleteButton;
    private Button createButton;
    private Button updateButton;
    private Button showAllButton;
    private ObservableList<User> userData;
    private Stage currentStage;
    private Stage previousStage;
    private Label newUsername;
    private TextArea usernameArea;

    public AdminView(Stage primaryStage){

        primaryStage.setTitle("Admin MODE: ON");

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);

        newUsername = new Label("username to add or update:");
        newUsername.setFont(Font.font("Tahome", FontWeight.EXTRA_BOLD, 10));
        usernameArea = new TextArea();
        usernameArea.setEditable(true);

        usernameArea = new TextArea();
        usernameArea.setEditable(true);
        usernameArea.setPrefSize(200, 20);  // Set the preferred width and height
        usernameArea.setMaxHeight(50);
        usernameArea.setWrapText(true);
        //usernameArea.setPrefSize(10, 50);

        Scene scene = new Scene(gridPane, 750, 500);
        primaryStage.setScene(scene);

        // initializeButtons(gridPane);
        initializeCustomerView(gridPane);
        initializeText(gridPane);

        primaryStage.show();
    }

    private void initializeGridPane(GridPane gridPane){
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(0, 0, 0, 0));
    }

    private void initializeCustomerView(GridPane gridPane){

        userTableView = new TableView<>();
        TableColumn<User, Integer> usernameColumn = new TableColumn<>("user-name");

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        userTableView.getColumns().add(usernameColumn);
        userTableView.setPrefSize(200, 200);
        usernameColumn.setPrefWidth(200);
        //bookTableView.getColumns().addAll(idColumn, titleColumn, authorColumn, priceColumn, quantityColumn);

        VBox tableViewBox = new VBox(10);
        tableViewBox.getChildren().add(userTableView);

        logOutButton = new Button("LogOut");
        retrieveButton = new Button("Retrieve");
        updateButton = new Button("Update");
        createButton = new Button("Create");
        deleteButton = new Button("Delete");
        showAllButton = new Button("Show All");


        HBox buttonsDelete = new HBox(10);
        buttonsDelete.setAlignment(Pos.BOTTOM_CENTER);
        buttonsDelete.getChildren().addAll(retrieveButton,deleteButton);

        HBox buttonsCreate = new HBox(10);
        buttonsCreate.setAlignment(Pos.BOTTOM_CENTER);
        buttonsCreate.getChildren().addAll(createButton, updateButton);

        HBox buttonsShowAll = new HBox(10);
        buttonsShowAll.setAlignment(Pos.BOTTOM_CENTER);
        buttonsShowAll.getChildren().addAll(showAllButton, logOutButton);

        gridPane.add(newUsername,3,0,1,1);
        gridPane.add(usernameArea,3,1,1,1);
        gridPane.add(buttonsCreate, 3, 2, 1, 1);

        gridPane.add(buttonsShowAll, 1, 1, 1, 1);
        gridPane.add(buttonsDelete, 1, 5, 1, 1);

        gridPane.add(tableViewBox, 1, 2, 1, 2);

    }

    public void initializeText(GridPane gridPane){

    }

    public void addLogOutButtonListener(EventHandler<ActionEvent> logOutButtonHandler) {
        logOutButton.setOnAction(logOutButtonHandler);
    }

    public void addRetrieveButtonListener(EventHandler<ActionEvent> retrieveButtonHandler) {
        retrieveButton.setOnAction(retrieveButtonHandler);
    }

    public void addDeleteButtonListener(EventHandler<ActionEvent> deleteButtonHandler) {
        deleteButton.setOnAction(deleteButtonHandler);
    }

    public void addCreateButtonListener(EventHandler<ActionEvent> createButtonHnadler) {
        createButton.setOnAction(createButtonHnadler);
    }

    public void addUpdateButtonListener(EventHandler<ActionEvent> updateButtonHandler) {
        updateButton.setOnAction(updateButtonHandler);
    }

    public void addShowAllListener(EventHandler<ActionEvent> showAllHandler) {
        showAllButton.setOnAction(showAllHandler);
    }

    public void setUsersData(List<User> users) {
        userData = FXCollections.observableArrayList(users);
        userTableView.setItems(userData);
    }

    public void closeAdminWindow() {
        //??
    }
}
