package repository.book;

import model.Book;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> findAll();

    Optional<Book> findById(Long id);

    boolean save(Book book);

    void removeAll();

    boolean updateDatabase(Long id, int quantity);
    boolean updateDatabaseForPrice(Long id, int price);

    boolean updateDatabaseForTitle(Long id, String title);
    boolean updateDatabaseForAuthor(Long id, String author);

    Book addNewBook(Long id, String author, String title, int price, int quantity);
    void remove(Long id);
}
