package service.book;

import model.Book;

import java.sql.Date;
import java.util.List;

public interface BookService {

    List<Book> findAll();

    Book findById(Long id);
    Book addNewBook(Long id, String author, String title, int price, int quantity);
    boolean save(Book book);
    boolean updateDatabase(Long id, int quantity, String title);
    boolean updateDatabaseForPrice(Long id, int price);

    void remove(Long id);
}
