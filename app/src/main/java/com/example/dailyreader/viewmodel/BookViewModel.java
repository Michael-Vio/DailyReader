package com.example.dailyreader.viewmodel;

import android.app.Application;
import android.os.Build;


import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.dailyreader.entity.Book;
import com.example.dailyreader.repository.BookRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;


public class BookViewModel extends AndroidViewModel {
    private final BookRepository bRepository;
    private final LiveData<List<Book>> allBooks;
    public BookViewModel (Application application) {
        super(application);
        bRepository = new BookRepository(application);
        allBooks = bRepository.getAllBooks();
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<Book> findByIdFuture(final int bookId){
        return bRepository.findByIdFuture(bookId);
    }
    public LiveData<List<Book>> getAllBooks() {

        return allBooks;
    }

    public CompletableFuture<List<Book>> getBookList() {
        return bRepository.getBookList();
    }

    public void insert(Book book) {

        bRepository.insert(book);
    }
    public void delete(Book book) {

        bRepository.delete(book);
    }
    public void deleteAll() {

        bRepository.deleteAll();
    }
    public void update(Book book) {

        bRepository.updateBook(book);
    }


}





