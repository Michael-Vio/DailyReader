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

    @Query("SELECT * FROM readTime ORDER BY read_date ASC")
    LiveData<List<ReadTime>> getAll();
    @Query("SELECT * FROM readTime WHERE read_date = :readDate LIMIT 1")
    ReadTime findByDate(String readDate);
    @Insert
    void insert(ReadTime readTime);
    @Delete
    void delete(ReadTime readTime);
    @Update
    void updateReadTime(ReadTime readTime);
    @Query("DELETE FROM readTime")
    void deleteAll();

}
