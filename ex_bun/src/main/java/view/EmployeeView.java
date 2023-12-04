package view;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;

import java.util.List;

public class EmployeeView {


    private Button logOutButton;
    private TableView<User> userTableView;
    private Text actiontarget;
    private Button retrieveButton;
    private Button deleteButton;
    private Button createButton;
    private Button updateButton;
    private Button showAllButton;
    private ObservableList<User> userData;
    private Stage currentStage;
    private Stage previousStage;
    private Label usernameText;
    private TextArea usernameArea;
    private PasswordField passwordArea;

    public EmployeeView(Stage primaryStage){

        primaryStage.setTitle("EMPLOYEE, MODE: ON");

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);

        usernameText = new Label("username and password to add or update:");
        usernameText.setFont(Font.font("Tahome", FontWeight.EXTRA_BOLD, 10));

        usernameArea = new TextArea();
        passwordArea = new PasswordField();
        usernameArea.setEditable(true);
        passwordArea.setEditable(true);

        usernameArea.setPrefSize(200, 20);  // Set the preferred width and height
        passwordArea.setPrefSize(200, 20);  // Set the preferred width and height
        usernameArea.setMaxHeight(50);
        passwordArea.setMaxHeight(50);
        usernameArea.setWrapText(true);
        //usernameArea.setPrefSize(10, 50);


        actiontarget = new Text();
        actiontarget.setFill(Color.FIREBRICK);

        Scene scene = new Scene(gridPane, 750, 500);
        primaryStage.setScene(scene);

        // initializeButtons(gridPane);
        initializeEmployeeView(gridPane);

        primaryStage.show();
    }


    private void initializeGridPane(GridPane gridPane){
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(0, 0, 0, 0));
    }

    private void initializeEmployeeView(GridPane gridPane){

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

        gridPane.add(usernameText,3,0,1,1);
        gridPane.add(usernameArea,3,1,1,1);
        gridPane.add(passwordArea,3,2,1,1);
        gridPane.add(buttonsCreate, 3, 3, 1, 1);

        gridPane.add(buttonsShowAll, 1, 1, 1, 1);
        gridPane.add(buttonsDelete, 1, 6, 1, 1);

        gridPane.add(actiontarget, 3, 6);

        gridPane.add(tableViewBox, 1, 2, 1, 2);

    }

    public void closeAdminWindow() {
    }

    public void setUsersData(List<User> users) {
    }
}
