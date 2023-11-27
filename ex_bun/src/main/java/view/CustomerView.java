package view;

import com.mysql.cj.xdevapi.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import model.Book;
import model.User;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import service.book.BookServiceImpl;

// ana@are.mere --> parola1/

public class CustomerView {

    private TableView<String> tableView;
    private Button logOutButton;
    private Button buyButton;

    public CustomerView(Stage primaryStage){

        primaryStage.setTitle("Client View");

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);

        Scene scene = new Scene(gridPane, 720, 480);
        primaryStage.setScene(scene);

        initializeCustomerView(gridPane);
        initializeButtons(gridPane);

        primaryStage.show();
    }

    private void initializeGridPane(GridPane gridPane){
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
    }

    private void initializeCustomerView(GridPane gridPane){
        tableView = new TableView<>();
        TableColumn<String, String> columnBookTitle = new TableColumn<>("Book Title");
        TableColumn<String, String> columnAuthor = new TableColumn<>("Author");
        TableColumn<String, String> columnPrice = new TableColumn<>("Price (RON)");
        TableColumn<String, String> columnQuantity= new TableColumn<>("Quantity");
        //columnBookTitle.setCellFactory(cellData -> cellData.findAll());

        tableView.getColumns().add(columnBookTitle);
        tableView.getColumns().add(columnAuthor);
        tableView.getColumns().add(columnPrice);
        tableView.getColumns().add(columnQuantity);

        //tableView.setItems(data);


        gridPane.add(tableView, 0, 0, 2, 1);
    }

    private void initializeButtons(GridPane gridPane){

        logOutButton = new Button("LogOut");
        buyButton = new Button("Buy");

        HBox buttonsHBox = new HBox(10);
        buttonsHBox.setAlignment(Pos.BOTTOM_CENTER);
        buttonsHBox.getChildren().addAll(logOutButton, buyButton);

        gridPane.add(buttonsHBox, 0, 1, 2, 1);
    }

    public TableView<String> getTableView(){
        return tableView;
    }

    public Button getBuyButton(){
        return buyButton;
    }

    public Button getLogOutButton(){
        return logOutButton;
    }

    public Scene createScene(){
        return new Scene(new VBox(tableView), 720, 480);
    }
}
