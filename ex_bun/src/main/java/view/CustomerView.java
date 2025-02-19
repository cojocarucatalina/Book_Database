package view;

import controller.CustomerController;
import controller.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import launcher.ComponentFactory;
import model.Book;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.User;
import repository.book.BookRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;
import service.book.BookService;
import service.book.BookServiceImpl;
import service.user.AuthenticationService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// ana@are.mere --> parola1/

public class CustomerView {


    private LoginView loginView;
    //private final CustomerView customerView;
    private LoginController loginController;
    private AuthenticationService authenticationService;

    private BookService bookService;
    private static ComponentFactory instance;

    private TableView<Book> tableView;
    private Button logOutButton;
    private TableView<Book> bookTableView;
    private Button buyButton;
    private Button finishButton;
    private Button showAllButton;
    private ObservableList<Book> booksData;
    private Stage currentStage;
    private Stage previousStage;
    private TextArea addedToCartArea;
    private TextArea totalArea;
    private Label totalLabel;
    Label addedToCartLabel;


    public CustomerView(Stage primaryStage){

        primaryStage.setTitle("Client View");

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);

        Scene scene = new Scene(gridPane, 800, 700);
        primaryStage.setScene(scene);

        addedToCartArea = new TextArea();
        addedToCartArea.setEditable(false);
        totalArea = new TextArea();
        totalArea.setEditable(false);
        totalArea.setText("");
        totalArea.setPrefWidth(100);
        totalArea.setPrefHeight(200);
        addedToCartLabel = new Label("YOUR CART: \n");
        addedToCartLabel.setFont(Font.font("Tahome", FontWeight.EXTRA_BOLD, 10));
        totalLabel = new Label("YOUR TOTAL IS: ");
        totalLabel.setFont(Font.font("Tahome", FontWeight.EXTRA_BOLD, 10));
        addedToCartArea.setText("");
       // initializeButtons(gridPane);
        initializeCustomerView(gridPane);


        primaryStage.show();
    }

    private void initializeGridPane(GridPane gridPane){
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
    }

    private void initializeCustomerView(GridPane gridPane){
//
//        tableView = new TableView<>();
//        TableColumn<Book, String> columnBookTitle = new TableColumn<>("Book Title");
//        TableColumn<Book, String> columnAuthor = new TableColumn<>("Author");
//        TableColumn<Book, String> columnPrice = new TableColumn<>("Price (RON)");
//        TableColumn<Book, String> columnQuantity= new TableColumn<>("Quantity");
//
//        columnBookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
//        columnAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
//        columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
//        columnQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
//
//        tableView.getColumns().add(columnBookTitle);
//        tableView.getColumns().add(columnAuthor);
//        tableView.getColumns().add(columnPrice);
//        tableView.getColumns().add(columnQuantity);


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

        bookTableView.getColumns().addAll(idColumn, titleColumn, authorColumn, dateColumn, priceColumn, quantityColumn);
        //bookTableView.getColumns().addAll(idColumn, titleColumn, authorColumn, priceColumn, quantityColumn);

        VBox tableViewBox = new VBox(10);
        tableViewBox.getChildren().add(bookTableView);

        logOutButton = new Button("LogOut");
        buyButton = new Button("Buy");
        finishButton = new Button("FinishShopping");
        showAllButton = new Button("Show All Books");

        HBox buttonsHBox = new HBox(10);
        buttonsHBox.setAlignment(Pos.BOTTOM_CENTER);
        buttonsHBox.getChildren().addAll(showAllButton, logOutButton, buyButton, finishButton);

        gridPane.add(buttonsHBox, 0, 5, 2, 1);

        gridPane.add(tableViewBox, 0, 0, 2, 1);

        gridPane.add(addedToCartArea, 0, 7, 2, 1);
        gridPane.add(addedToCartLabel, 0, 6, 2, 1);
        gridPane.add(totalLabel, 0, 8, 2, 1);
        gridPane.add(totalArea, 0, 9, 2, 1);


    }

        private void initializeButtons(GridPane gridPane){

        logOutButton = new Button("LogOut");
        buyButton = new Button("Buy");
        finishButton = new Button("FinishShopping");
        showAllButton = new Button("Show All Books");

        HBox buttonsHBox = new HBox(10);
        buttonsHBox.setAlignment(Pos.BOTTOM_CENTER);
        buttonsHBox.getChildren().addAll(showAllButton, logOutButton, buyButton, finishButton);

        gridPane.add(buttonsHBox, 0, 1, 2, 1);
    }

    public TableView<Book> getTableView(){
        return tableView;
    }

    public void setAddedToCartArea(String addedToCart) {
        addedToCartArea.appendText(addedToCart);
    }
    public void setTotalArea(String addedToCart) {
        totalArea.appendText(addedToCart);
    }

    public Button getBuyButton(){
        return buyButton;
    }
    public Button getShowAllButton(){
        return showAllButton;
    }

    public Button getLogOutButton(){
        return logOutButton;
    }
    public Book getSelectedBook(){
        return bookTableView.getSelectionModel().getSelectedItem();
    }

    public Scene createScene(){
        return new Scene(new VBox(tableView), 720, 480);
    }

    public void addLogOutButtonListener(EventHandler<ActionEvent> logOutButtonHandler) {
        logOutButton.setOnAction(logOutButtonHandler);
    }

    public void addBuyButtonListener(EventHandler<ActionEvent> buyButtonHandler) {
        buyButton.setOnAction(buyButtonHandler);
    }

    public void addFinishButtonListener(EventHandler<ActionEvent> finishButtonHandler) {
        finishButton.setOnAction(finishButtonHandler);
    }

    public void addShowAllListener(EventHandler<ActionEvent> showAllHandler) {
        showAllButton.setOnAction(showAllHandler);
    }

    public void setBooksData(List<Book> books) {
        booksData = FXCollections.observableArrayList(books);
        bookTableView.setItems(booksData);
    }

    public void closeCustomerWindow() {
        Stage stage = new Stage();
        LoginView logInView = new LoginView(stage);


        loginController = new LoginController(loginView, authenticationService, bookService);
        previousStage = (Stage) logInView.getLogInButton().getScene().getWindow();
        previousStage.show();
        ComponentFactory componentFactory = ComponentFactory.getInstance(true, stage);

        currentStage = (Stage) logOutButton.getScene().getWindow();
        currentStage.close();
        //javafx.application.Platform.exit();
    }

    public void closeCustomerWindowForFinishShopping() {
        currentStage = (Stage) logOutButton.getScene().getWindow();
        currentStage.close();
    }
}
