package com.example.dailyreader.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dailyreader.entity.Book;;

import java.util.List;

@Dao
public interface BookDAO {
    @Query("SELECT * FROM book ORDER BY uid ASC")
    LiveData<List<Book>> getAll();
    @Query("SELECT * FROM book WHERE uid = :bookId LIMIT 1")
    Book findById(int bookId);
    @Insert
    void insert(Book book);
    @Delete
    void delete(Book book);
    @Update
    void updateBook(Book book);
    @Query("DELETE FROM book")
    void deleteAll();
}

