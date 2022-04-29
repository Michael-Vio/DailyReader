package com.example.dailyreader.entity;

import android.os.Parcelable;

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
    @NonNull
    public int readPosition;

    @ColumnInfo(name = "read_goal")
    public int readGoal;

    public Book(@NonNull String bookName, @NonNull String filepath, int readPosition, int readGoal) {
        this.bookName = bookName;
        this.filepath = filepath;
        this.readPosition = readPosition;
        this.readGoal = readGoal;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @NonNull
    public String getBookName() {
        return bookName;
    }

    public void setBookName(@NonNull String bookName) {
        this.bookName = bookName;
    }

    @NonNull
    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(@NonNull String filepath) {
        this.filepath = filepath;
    }

    public int getReadPosition() {
        return readPosition;
    }

    public void setReadPosition(int readPosition) {
        this.readPosition = readPosition;
    }

    public int getReadGoal() {
        return readGoal;
    }

    public void setReadGoal(int readGoal) {
        this.readGoal = readGoal;
    }
}


