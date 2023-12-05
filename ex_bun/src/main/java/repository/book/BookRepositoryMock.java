package repository.book;

import model.Book;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryMock implements BookRepository{

    private List<Book> books;

    public BookRepositoryMock(){
        books = new ArrayList<>();
    }

    @Override
    public List<Book> findAll() {
        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return books.parallelStream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean save(Book book) {
        return books.add(book);
    }
    @Override
    public void remove(Long id) {}

    @Override
    public boolean updateDatabaseForPrice(Long id, int price){
        return false;
    }

    @Override
    public boolean updateDatabaseForAuthor(Long id, String author){
        return false;
    }

    @Override
    public boolean updateDatabaseForTitle(Long id, String title){
        return false;
    }


    @Override
    public Book addNewBook(Long id, String author, String title, int price, int quantity) {
        return null;
    }

    @Override
    public void removeAll() {
        books.clear();
    }

    @Override
    public boolean updateDatabase(Long id, int quantity) {
        return false;
    }
}
