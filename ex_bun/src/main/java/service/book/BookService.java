package service.book;

import model.Book;

import java.util.List;

public interface BookService {

    List<Book> findAll();

    Book findById(Long id);

    boolean save(Book book);
    boolean updateDatabase(Long id, int quantity, String title);

    int getAgeOfBook(Long id);
}
