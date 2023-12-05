package service.book;

import model.Book;

import java.util.List;

public interface BookService {

    List<Book> findAll();

    Book findById(Long id);
    Book addNewBook(Long id, String author, String title, int price, int quantity);
    boolean save(Book book);
    boolean updateDatabaseForQuantity(Long id, int quantity);
    boolean updateDatabaseForTitle(Long id, String title);
    boolean updateDatabaseForAuthor(Long id, String author);
    boolean updateDatabaseForPrice(Long id, int price);

    void remove(Long id);
}
