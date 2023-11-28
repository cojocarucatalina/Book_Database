package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import launcher.Launcher;
import model.Book;
import service.book.BookService;
import service.user.AuthenticationService;
import view.CustomerView;
import view.LoginView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomerController {

    private final CustomerView customerView;
    private final BookService bookService;
    private final List<Book> selectedBooks;
    private int finishCounter =0;
    List<Book> addedToCartBooks = new ArrayList<>();

    public CustomerController(CustomerView customerView, BookService bookService, List<Book> selectedBooks) {
        this.selectedBooks = selectedBooks;
        this.customerView = customerView;
        this.bookService = bookService;

        this.customerView.addLogOutButtonListener(new LogOutHandler());
        this.customerView.addBuyButtonListener(new BuyButtonHandler());
        this.customerView.addFinishButtonListener(new FinishButtonHandler());
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
        private List<Book> books;

        @Override
        public void handle(ActionEvent event) {

            Book selectedBook = customerView.getSelectedBook();
            addedToCartBooks.add(selectedBook);

            int count =0;
            for (Book b : addedToCartBooks){
                if (b.getTitle().equals(selectedBook.getTitle()))
                    count++;
            }
            //int count = Collections.frequency(addedToCartBooks,selectedBook);
            System.out.println("aici "+ count);
            if (count > selectedBook.getQuantity()){
                customerView.setAddedToCartArea(selectedBook.getTitle() + "   - is unavailable\n");
            }
            else{

                // update quantity
                for (Book e : addedToCartBooks){
                    int quantity = e.getQuantity();
                    if (quantity > 1) {
                    bookService.updateDatabase(e.getId(),quantity -1, e.getTitle());
                    }
                }

                List<Book> books = bookService.findAll();
                customerView.setBooksData(books);

                System.out.println("Added to cart: "+ selectedBook.getTitle());
                customerView.setAddedToCartArea(selectedBook.getTitle()+", quantity: "+ count +"\n" );

            }

            int quantityOfSelectedBook = selectedBook.getQuantity();
            System.out.println("Quantity is : "+quantityOfSelectedBook);

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

    private class FinishButtonHandler implements EventHandler<ActionEvent> {
        private List<Book> books;
        @Override
        public void handle(ActionEvent event) {
            if (finishCounter %2 !=1){
                float total = 0;

                for (Book e : addedToCartBooks){
                    total += e.getPrice();
                }

                List<Book> books = bookService.findAll();
                customerView.setBooksData(books);

                customerView.setTotalArea(total +" RON\n");
                customerView.setTotalArea("\npress the FinishShopping button again to confirm");
                customerView.setTotalArea("\nHAVE A NICE DAY!");
            }
            else {

                System.out.println("Shopping is done!");
                customerView.closeCustomerWindowForFinishShopping();
            }
            finishCounter++;
        }
    }


}
