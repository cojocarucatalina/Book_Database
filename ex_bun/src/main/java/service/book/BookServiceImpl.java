package service.book;

import model.Book;
import repository.book.BookRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class BookServiceImpl implements BookService{

    final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Book with id: %d not found".formatted(id)));
    }

    @Override
    public boolean save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book addNewBook(Long id, String author, String title, int price, int quantity){
        return bookRepository.addNewBook(id,author, title, price, quantity);
    }

    @Override
    public boolean updateDatabaseForQuantity(Long id, int quantity) {
        return bookRepository.updateDatabase(id, quantity);
    }

    @Override
    public boolean updateDatabaseForPrice(Long id, int price){
        return bookRepository.updateDatabaseForPrice(id, price);
    }

    @Override
    public boolean updateDatabaseForTitle(Long id, String title){
        return bookRepository.updateDatabaseForTitle(id,title);
    }

    @Override
    public boolean updateDatabaseForAuthor(Long id, String author){
        return bookRepository.updateDatabaseForAuthor(id,author);
    }

    @Override
    public void remove(Long id){
        bookRepository.remove(id);
    }

}
