package org.example;

import database.DatabaseConnectionFactory;
import database.JDBConnectionWrapper;
import model.Book;
import model.EBook;
import model.builder.BookBuilder;
import model.builder.EBookBuilder;
import repository.BookRepository;
import repository.BookRepositoryCacheDecorator;
import repository.BookRepositoryMySQL;
import repository.Cache;
import service.BookService;
import service.BookServiceImpl;

import java.time.LocalDate;
import java.util.Date;

public class Main {
    public static void main(String[] args){
        System.out.println("Hello world!");


        BookRepository bookRepository = new BookRepositoryCacheDecorator(
                new BookRepositoryMySQL(DatabaseConnectionFactory.getConnectionWrapper(true).getConnection()),
                new Cache<>()
        );

        BookService bookService = new BookServiceImpl(bookRepository);


        Book book1 = new BookBuilder()
                .setTitle("Harry Potter")
                .setAuthor("J.K. Rowling")
                .setPublishedDate(LocalDate.of(2010, 7, 3))
                .build();

        Book book2 = new BookBuilder()
                .setTitle("Portretul lui Dorian Gray")
                .setAuthor("Oscar Wilde")
                .setPublishedDate(LocalDate.of(2010, 7, 3))
                .build();

        Book book3 = new BookBuilder()
                .setTitle("Printre tonuri cenusii")
                .setAuthor("Ruta Sepetys")
                .setPublishedDate(LocalDate.of(2010, 7, 3))
                .build();

        EBook book4  = new EBookBuilder()
                .setTitle("Percy Jackson")
                .setAuthor("Rick Riordan")
                .setPublishedDate(LocalDate.of(2010, 10, 14))
                .setFormat("pdf")
                .build();



        System.out.println(book1);
        System.out.println(book2);
        System.out.println(book3);
        System.out.println(book4);

        bookService.save(book1);
        bookService.save(book2);
        bookService.save(book3);
        bookService.save(book4);

        //  bookRepository.removeAll();

        System.out.println(bookService.findAll());

        System.out.println(bookService.findAll());
        System.out.println(bookService.findAll());




    }
}
