package com.example.dailyreader.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Book {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo(name = "book_name")
    @NonNull
    public String bookName;
    @ColumnInfo(name = "filepath")
    @NonNull
    public String filepath;
    @ColumnInfo(name = "read_position")
    public int readPosition;
    public Book( @NonNull String bookName, @NonNull String filepath, int readPosition) {
        this.bookName = bookName;
        this.filepath = filepath;
        this.readPosition = readPosition;
    }
}

