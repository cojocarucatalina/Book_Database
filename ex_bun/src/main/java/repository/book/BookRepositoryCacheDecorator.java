package repository.book;

import model.Book;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class BookRepositoryCacheDecorator extends BookRepositoryDecorator{
    private Cache<Book> cache;

    public BookRepositoryCacheDecorator(BookRepository bookRepository, Cache<Book> cache){
        super(bookRepository);
        this.cache = cache;
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
    public List<Book> findAll() {
        if (cache.hasResult()){
           return cache.load();
        }

        List<Book> books = decoratedRepository.findAll();
        cache.save(books);

        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {

        if (cache.hasResult()){
            return cache.load()
                    .stream()
                    .filter(it -> it.getId().equals(id))
                    .findFirst();
        }

        return decoratedRepository.findById(id);
    }

    @Override
    public boolean save(Book book) {
        cache.invalidateCache();
        return decoratedRepository.save(book);
    }

    @Override
    public void remove(Long id) {}


    @Override
    public Book addNewBook(Long id, String author, String title, int price, int quantity) {
        return null;
    }

    @Override
    public boolean updateDatabaseForPrice(Long id, int price){
        return false;
    }


    @Override
    public void removeAll() {
        cache.invalidateCache();
        decoratedRepository.removeAll();
    }

    @Override
    public boolean updateDatabase(Long id, int quantity) {
        return false;
    }
}
