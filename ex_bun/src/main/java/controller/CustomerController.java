package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import launcher.Launcher;
import model.Book;
import service.book.BookService;
import service.user.AuthenticationService;
import view.CustomerView;
import view.LoginView;

import java.util.List;

public class CustomerController {

    private final CustomerView customerView;
    private final BookService bookService;

    public CustomerController(CustomerView customerView, BookService bookService) {
        this.customerView = customerView;
        this.bookService = bookService;

        this.customerView.addLogOutButtonListener(new LogOutHandler());
        this.customerView.addBuyButtonListener(new BuyButtonHandler());
        this.customerView.addShowAllListener(new ShowAllHandler());
    }

    private class LogOutHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            //loginView.logOut();
            customerView.closeCustomerWindow();
        }
    }

    private class BuyButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {

            System.out.println("Added to cart!");

        }
    }

    private class ShowAllHandler implements EventHandler<ActionEvent> {

        private List<Book> books;
        @Override
        public void handle(ActionEvent event) {
            books = bookService.findAll();
            System.out.println(books);
            customerView.setBooksData(books);

        }
    }



}
