package com.example.dailyreader.module;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private String bookName;
    private String bookAuthor;
    public Book(String bookName, String bookAuthor) {
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
    }
    public String getBookName() {
        return bookName;
    }
    public String getBookAuthor() {
        return bookAuthor;
    }
    //this is used to populate the list with a few items at the start of the application
    //it is static so it can be called without instantiating the class
    public static List<Book> createContactsList() {
        List<Book> books = new ArrayList<Book>();
        books.add(new Book( "Harry Potter","J.K"));
        books.add(new Book( "Normal People","Somebody"));
        return books;
    }
}
