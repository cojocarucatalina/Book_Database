package org.example;

import database.DatabaseConnectionFactory;
import model.Book;
import model.builder.BookBuilder;
import repository.BookRepository;
import repository.BookRepositoryMySQL;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args){
        System.out.println("Hello world!");


        BookRepository bookRepository = new BookRepositoryMySQL(DatabaseConnectionFactory.getConnectionWrapper(true).getConnection());

        Book book = new BookBuilder()
                .setTitle("Harry Potter")
                .setAuthor("J.K. Rowling")
                .setPublishedDate(LocalDate.of(2010, 7, 3))
                .build();


        System.out.println(book);
        System.out.println(bookRepository.findAll());






    }
}