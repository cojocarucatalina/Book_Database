package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Book;
import service.book.BookService;
import view.CustomerView;

import java.util.List;

public class CustomerController {

    private final CustomerView customerView;
    private final ObservableList<String> data;
    private BookService bookService;

    public CustomerController(CustomerView customerView) {
        this.customerView = customerView;
        this.data = FXCollections.observableArrayList();

        initialize();
        customerView.getLogOutButton().setOnAction(new LogOutHandler());
        customerView.getBuyButton().setOnAction(new BuyButtonHandler());
    }

    private class LogOutHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {

        }
    }

    private class BuyButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {

        }
    }

    public void displayBooks() {
        List<Book> books = bookService.findAll();
        data.clear();
        for (Book book : books) {
            data.add(book.getTitle() + " by " + book.getAuthor() +
                    ", Price: " + book.getPrice() + " RON, Quantity: " + book.getQuantity());
        }

        customerView.getTableView().setItems(data);
    }

    private void initialize(){
        customerView.getTableView().setItems(data);
    }
}
