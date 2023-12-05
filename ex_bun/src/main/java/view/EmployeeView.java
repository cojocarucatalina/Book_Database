package view;

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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import launcher.ComponentFactory;
import model.Book;
import model.User;
import service.book.BookService;
import service.user.AuthenticationService;

import java.sql.Date;
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
    private Label idText;
    private Label titleText;
    private Label authorText;
    private Label dateText;
    private Label stockText;
    private Label priceText;
    private TextArea idArea;
    private TextArea titleArea;
    private TextArea authorArea;
    private TextArea dateArea;
    private TextArea stockArea;
    private TextArea priceArea;
    private LoginView loginView;
    //private final CustomerView customerView;
    private LoginController loginController;
    private AuthenticationService authenticationService;

    private BookService bookService;
    private static ComponentFactory instance;

    private TableView<Book> tableView;
    private TableView<Book> bookTableView;
    private Button sellButton;
    private ObservableList<Book> booksData;
    private TextArea retrieveArea;

    public EmployeeView(Stage primaryStage){

        primaryStage.setTitle("EMPLOYEE, MODE: ON");

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);

        idText = new Label("id:");
        idText.setFont(Font.font("Tahome", FontWeight.EXTRA_BOLD, 10));

        authorText = new Label("author:");
        authorText.setFont(Font.font("Tahome", FontWeight.EXTRA_BOLD, 10));

        titleText = new Label("title:");
        titleText.setFont(Font.font("Tahome", FontWeight.EXTRA_BOLD, 10));

        dateText = new Label("date as dd.mm.yyy:");
        dateText.setFont(Font.font("Tahome", FontWeight.EXTRA_BOLD, 10));

        stockText = new Label("quantity:");
        stockText.setFont(Font.font("Tahome", FontWeight.EXTRA_BOLD, 10));

        priceText = new Label("price:");
        priceText.setFont(Font.font("Tahome", FontWeight.EXTRA_BOLD, 10));


        idArea = new TextArea();
        idArea.setEditable(true);
        idArea.setPrefSize(200, 20);  // Set the preferred width and height
        idArea.setMaxHeight(50);
        idArea.setWrapText(true);

        authorArea = new TextArea();
        authorArea.setEditable(true);
        authorArea.setPrefSize(200, 20);  // Set the preferred width and height
        authorArea.setMaxHeight(50);
        authorArea.setWrapText(true);

        titleArea = new TextArea();
        titleArea.setEditable(true);
        titleArea.setPrefSize(200, 20);  // Set the preferred width and height
        titleArea.setMaxHeight(50);
        titleArea.setWrapText(true);

        dateArea = new TextArea();
        dateArea.setEditable(true);
        dateArea.setPrefSize(200, 20);  // Set the preferred width and height
        dateArea.setMaxHeight(50);
        dateArea.setWrapText(true);

        stockArea = new TextArea();
        stockArea.setEditable(true);
        stockArea.setPrefSize(200, 20);  // Set the preferred width and height
        stockArea.setMaxHeight(50);

        //usernameArea.setPrefSize(10, 50);

        retrieveArea = new TextArea();
        retrieveArea.setPrefSize(100, 20);  // Set the preferred width and height
        retrieveArea.setMaxHeight(50);
        retrieveArea.setWrapText(true);

        priceArea = new TextArea();
        priceArea.setPrefSize(100, 20);  // Set the preferred width and height
        priceArea.setMaxHeight(50);
        priceArea.setWrapText(true);


        actiontarget = new Text();
        actiontarget.setFill(Color.FIREBRICK);

        Scene scene = new Scene(gridPane, 800, 700);
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

        bookTableView = new TableView<>();
        TableColumn<Book, Integer> idColumn = new TableColumn<>("ID");
        TableColumn<Book, Integer> authorColumn = new TableColumn<>("Author");
        TableColumn<Book, Integer> titleColumn = new TableColumn<>("Title");
        TableColumn<Book, Integer> dateColumn = new TableColumn<>("Date");
        TableColumn<Book, Integer> priceColumn = new TableColumn<>("Price");
        TableColumn<Book, Integer> quantityColumn = new TableColumn<>("Quantity");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("publishedDate"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        bookTableView.getColumns().addAll(idColumn, titleColumn, authorColumn,priceColumn,quantityColumn, dateColumn);
        //bookTableView.getColumns().addAll(idColumn, titleColumn, authorColumn, priceColumn, quantityColumn);

        VBox tableViewBox = new VBox(10);
        tableViewBox.getChildren().add(bookTableView);


        logOutButton = new Button("LogOut");
        retrieveButton = new Button("Retrieve");
        sellButton = new Button("Sell");
        updateButton = new Button("Update");
        createButton = new Button("Create");
        deleteButton = new Button("Delete");
        showAllButton = new Button("Show All");


        HBox buttonsDelete = new HBox(10);
        buttonsDelete.setAlignment(Pos.BOTTOM_CENTER);
        buttonsDelete.getChildren().addAll(deleteButton, sellButton);

        HBox buttonsRetrieve = new HBox(10);
        buttonsRetrieve.setAlignment(Pos.BOTTOM_CENTER);
        buttonsRetrieve.getChildren().addAll(retrieveButton);

        HBox buttonsCreate = new HBox(10);
        buttonsCreate.setAlignment(Pos.BOTTOM_CENTER);
        buttonsCreate.getChildren().addAll(createButton);

        HBox buttonsUpdate = new HBox(10);
        buttonsUpdate.setAlignment(Pos.BOTTOM_CENTER);
        buttonsUpdate.getChildren().addAll(updateButton);

        HBox buttonsShowAll = new HBox(10);
        buttonsShowAll.setAlignment(Pos.BOTTOM_CENTER);
        buttonsShowAll.getChildren().addAll(showAllButton, logOutButton);


        gridPane.add(idText,3,0,1,1);
        gridPane.add(titleText,4,0,1,1);
        gridPane.add(authorText,5,0,1,1);
        //gridPane.add(dateText,3,3,1,1);
        gridPane.add(stockText,5,3,1,1);
        gridPane.add(priceText,4,3,1,1);

        gridPane.add(idArea,3,1,1,1);
        gridPane.add(titleArea,4,1,1,1);
        gridPane.add(authorArea,5,1,1,1);
        //gridPane.add(dateArea,3,4,1,1);
        gridPane.add(stockArea,5,4,1,1);
        gridPane.add(priceArea,4,4,1,1);

        gridPane.add(buttonsCreate, 3, 5, 1, 1);
        gridPane.add(buttonsUpdate, 4, 5, 1, 1);


        gridPane.add(buttonsShowAll, 1, 2, 1, 1);
        gridPane.add(buttonsDelete, 1, 8, 1, 1);
        gridPane.add(buttonsRetrieve, 3, 8, 1, 1);

        gridPane.add(retrieveArea, 4, 8, 1, 1);

        gridPane.add(actiontarget, 4, 5);

        gridPane.add(tableViewBox, 1, 4, 1, 2);

        setActionTargetText("Select a book and enter\nnew values to update.\nAlso can update by introducing\na valid id!");


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

    public void addSellButtonListener(EventHandler<ActionEvent> sellButtonHandler) {
        sellButton.setOnAction(sellButtonHandler);
    }


    public void setBooksData(List<Book> books) {
        booksData = FXCollections.observableArrayList(books);
        bookTableView.setItems(booksData);
    }

    public Book getSelectedUser(){
        return bookTableView.getSelectionModel().getSelectedItem();
    }

    public void setActionTargetText(String text){ this.actiontarget.setText(text);}

    public void closeEmployeeWindow() {
        Stage currentStage = (Stage) logOutButton.getScene().getWindow();
        currentStage.close();
    }

    public Long getId() {
        System.out.println(idArea.getText());
        if(idArea.getText().isEmpty()){
            return 0L;
        }
        else {
            return Long.valueOf(idArea.getText());
        }
    }

    public Long getIdForRetrieve() {
        System.out.println(retrieveArea.getText());
        if(retrieveArea.getText().isEmpty()){
            return 0L;
        }
        else {
            return Long.valueOf(retrieveArea.getText());
        }
    }

    public String getAuthor(){
        return authorArea.getText();
    }

    public String getTitle(){
        return titleArea.getText();
    }

//    public Date getDate(){
//        return Date.valueOf(dateArea.getText());
//    }

    public int getQuantity(){
        if(stockArea.getText().isEmpty()){
            return 0;
        }
        else {
            return Integer.parseInt(stockArea.getText());
        }
    }

    public int getPrice(){
        System.out.println(priceArea.getText());
        if(priceArea.getText().isEmpty()){
            return 0;
        }
        else {
            return Integer.parseInt(priceArea.getText());
        }
    }
    public Book getSelectedBook(){
        return bookTableView.getSelectionModel().getSelectedItem();
    }

}
