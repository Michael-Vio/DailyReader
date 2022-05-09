package com.example.dailyreader.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.example.dailyreader.IntegerConverter;
import java.util.List;

@Entity

public class Book {
    @PrimaryKey(autoGenerate = true)
    public int bid;

    @ColumnInfo(name = "book_name")
    @NonNull
    public String bookName;

    @ColumnInfo(name = "filepath")
    @NonNull
    public String filepath;

    @ColumnInfo(name = "read_position")
    @TypeConverters(IntegerConverter.class)
    public List<Integer> readPosition;

    @ColumnInfo(name = "read_goal")
    public int readGoal;

    public Book(@NonNull String bookName, @NonNull String filepath, List<Integer> readPosition, int readGoal) {
        this.bookName = bookName;
        this.filepath = filepath;
        this.readPosition = readPosition;
        this.readGoal = readGoal;
    }

    public int getBid() {
        return bid;
    }

    @NonNull
    public String getBookName() {
        return bookName;
    }

    @NonNull
    public String getFilepath() {
        return filepath;
    }

    public List<Integer> getReadPosition() {
        return readPosition;
    }

    public void setReadPosition(List<Integer> readPosition) {
        this.readPosition = readPosition;
    }

    public int getReadGoal() {
        return readGoal;
    }

    public void setReadGoal(int readGoal) {
        this.readGoal = readGoal;
    }


}


