package com.example.dailyreader.repository;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;


import com.example.dailyreader.DAO.BookDAO;
import com.example.dailyreader.database.BookDatabase;
import com.example.dailyreader.database.ReadTimeDatabase;
import com.example.dailyreader.entity.Book;
import com.example.dailyreader.entity.ReadTime;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;


public class BookRepository {
    private final BookDAO bookDao;
    private final LiveData<List<Book>> allBooks;

    public BookRepository(Application application){
        BookDatabase bookDb = BookDatabase.getInstance(application);
        bookDao = bookDb.bookDAO();
        allBooks = bookDao.getAll();
    }
    // Room executes this query on a separate thread
    public LiveData<List<Book>> getAllBooks() {

        return allBooks;
    }

    public CompletableFuture<List<Book>> getBookList() {
        return CompletableFuture.supplyAsync(new Supplier<List<Book>>() {
            @Override
            public List<Book> get() {
                return bookDao.getBookList();
            }
        }, BookDatabase.databaseWriteExecutor);
    }

    public void insert(final Book book){
        BookDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {

                bookDao.insert(book);
            }
        });
    }
    public void deleteAll(){
        BookDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {

                bookDao.deleteAll();
            }
        });
    }
    public void delete(final Book book){
        BookDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {

                bookDao.delete(book);
            }
        });
    }
    public void updateBook(final Book book){
        BookDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {

                bookDao.updateBook(book);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<Book> findByIdFuture(final int bookId) {
        return CompletableFuture.supplyAsync(new Supplier<Book>() {
            @Override
            public Book get() {
                return bookDao.findById(bookId);
            }
        }, BookDatabase.databaseWriteExecutor);
    }

}


