package repository;

import model.AudioBook;
import model.Book;
import model.EBook;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> findAll();
    // List<EBook> findAll();

    Optional<Book> findById(Long id);
    // Optional<EBook> findById(Long id);

    boolean save(Book book);

    void removeAll();

}
