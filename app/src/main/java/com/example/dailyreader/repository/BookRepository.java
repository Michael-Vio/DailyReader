package com.example.dailyreader.repository;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.dailyreader.DAO.BookDAO;
import com.example.dailyreader.database.BookDatabase;
import com.example.dailyreader.entity.Book;

import java.util.List;
import java.util.concurrent.CompletableFuture;


public class BookRepository {
    private final BookDAO bookDao;
    private final LiveData<List<Book>> allBooks;

    public BookRepository(Application application){
        BookDatabase bookDb = BookDatabase.getInstance(application);
        bookDao = bookDb.bookDAO();
        allBooks = bookDao.getAll();
    }

    public LiveData<List<Book>> getAllBooks() {
        return allBooks;
    }

    public CompletableFuture<List<Book>> getBookList() {
        return CompletableFuture.supplyAsync(bookDao::getBookList, BookDatabase.databaseWriteExecutor);
    }

    public void insert(final Book book){
        BookDatabase.databaseWriteExecutor.execute(() -> bookDao.insert(book));
    }
    public void deleteAll(){
        BookDatabase.databaseWriteExecutor.execute(bookDao::deleteAll);
    }
    public void delete(final Book book){
        BookDatabase.databaseWriteExecutor.execute(() -> bookDao.delete(book));
    }
    public void updateBook(final Book book){
        BookDatabase.databaseWriteExecutor.execute(() -> bookDao.updateBook(book));
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<Book> findByIdFuture(final int bookId) {
        return CompletableFuture.supplyAsync(() -> bookDao.findById(bookId), BookDatabase.databaseWriteExecutor);
    }

    public CompletableFuture<Book> findByNameFuture(final String bookName) {
        return CompletableFuture.supplyAsync(() -> bookDao.findByName(bookName), BookDatabase.databaseWriteExecutor);
    }

}


