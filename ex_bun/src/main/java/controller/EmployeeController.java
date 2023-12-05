package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Book;
import service.book.BookService;
import view.EmployeeView;

import java.util.ArrayList;
import java.util.List;

public class EmployeeController {

    private final EmployeeView employeeView;
    List<Book> addedToCartBooks = new ArrayList<>();
    private final BookService bookService;
    private final List<Book> selectedBooks;

    public EmployeeController(EmployeeView employeeView, BookService bookService, List<Book> selectedBooks) {
        this.selectedBooks = selectedBooks;
        this.employeeView = employeeView;
        this.bookService = bookService;

        this.employeeView.addLogOutButtonListener(new EmployeeController.LogOutHandler());
        this.employeeView.addCreateButtonListener(new EmployeeController.CreateButtonHandler());
        this.employeeView.addUpdateButtonListener(new EmployeeController.UpdateButtonHandler());
        this.employeeView.addDeleteButtonListener(new EmployeeController.DeleteButtonHandler());
        this.employeeView.addRetrieveButtonListener(new EmployeeController.RetrieveHandler());
        this.employeeView.addSellButtonListener(new EmployeeController.SellButtonHandler());
        this.employeeView.addShowAllListener(new EmployeeController.ShowAllHandler());
    }

    private class LogOutHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            //loginView.logOut();
            employeeView.closeEmployeeWindow();
        }
    }

    private class ShowAllHandler implements EventHandler<ActionEvent> {

        private List<Book> books;
        @Override
        public void handle(ActionEvent event) {
            books = bookService.findAll();
            System.out.println(books);
            employeeView.setBooksData(books);

        }
    }

    public class CreateButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {

            Long id = employeeView.getId();
            String author = employeeView.getAuthor();
            String title = employeeView.getTitle();
            int quantity = employeeView.getQuantity();
            int price = employeeView.getPrice();
            //Date date = employeeView.getDate();

            System.out.println(id+" "+author+" "+title+" "+quantity+" "+price);

            if (author.isEmpty() || title.isEmpty() || quantity ==0 || price==0){
                employeeView.setActionTargetText("Could not create new book!");

            }
            else{

                bookService.addNewBook(id,author,title,price,quantity);

                List<Book> books = bookService.findAll();
                employeeView.setBooksData(books);
            }

        }
    }

    public class UpdateButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {

            Long id = employeeView.getId();
            String author = employeeView.getAuthor();
            String title = employeeView.getTitle();
            int quantity = employeeView.getQuantity();
            int price = employeeView.getPrice();

            System.out.println(id);
            System.out.println(title);
            System.out.println(quantity);

            Book selectedBook = employeeView.getSelectedBook();
            try{
                //selectedBook.getId()!=0
                id = selectedBook.getId();
            }
            catch(NullPointerException nul){
                employeeView.setActionTargetText("Could not update! Select a book!");

            }


            if ((id == 0)){
                employeeView.setActionTargetText("Could not update! Id not valid!");
            }
            else {

                if (quantity != 0) {
                    bookService.updateDatabaseForQuantity(id, quantity);
                }
                if (price != 0) {
                    bookService.updateDatabaseForPrice(id, price);
                }
                if (!author.isEmpty()) {
                    bookService.updateDatabaseForAuthor(id, author);
                }
                if (!title.isEmpty()) {
                    bookService.updateDatabaseForTitle(id, title);
                }

                List<Book> books = bookService.findAll();
                employeeView.setBooksData(books);

                employeeView.setActionTargetText("Updated!");
            }

        }
    }

    public class DeleteButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {

            Book selectedBook = employeeView.getSelectedUser();

            System.out.println(selectedBook.getId());
            bookService.remove(selectedBook.getId());

            List<Book> books = bookService.findAll();
            employeeView.setBooksData(books);

            employeeView.setActionTargetText("Deleted!");
        }
    }

    public class RetrieveHandler implements EventHandler<ActionEvent> {

        Book book;
        List<Book> books = new ArrayList<Book>();

        @Override
        public void handle(ActionEvent event) {

            Long id = employeeView.getIdForRetrieve();
            System.out.println(id);

            try {
                book = bookService.findById(id);
            } catch (IllegalArgumentException il) {
                employeeView.setActionTargetText("Id not found!");
            }
            books.clear();
            books.add(book);
            System.out.println(book);
            employeeView.setBooksData(books);
        }
    }

    public class SellButtonHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            Book selectedBook = employeeView.getSelectedBook();
            int quantity = selectedBook.getQuantity();
            if (quantity<1){
                employeeView.setActionTargetText("Not enough books!");
            }
            else {
                bookService.updateDatabaseForQuantity(selectedBook.getId(), quantity - 1);

                List<Book> books = bookService.findAll();
                employeeView.setBooksData(books);
            }
        }
    }
}
