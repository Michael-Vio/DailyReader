package com.example.dailyreader.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.dailyreader.entity.ReadTime;

import java.util.List;
@Dao
public interface ReadTimeDAO {
    @Query("SELECT * FROM readtime ORDER BY rid ASC")
    LiveData<List<ReadTime>> getAll();
    @Query("SELECT * FROM readtime WHERE read_date = :readTimeDate LIMIT 1")
    ReadTime findByDate(String readTimeDate);
    @Insert
    void insert(ReadTime readTime);
    @Delete
    void delete(ReadTime readTime);
    @Update
    void updateBook(ReadTime readTime);
    @Query("DELETE FROM readtime")
    void deleteAll();
}
