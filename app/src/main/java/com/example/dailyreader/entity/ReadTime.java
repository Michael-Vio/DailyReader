package com.example.dailyreader.entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


public class ReadTime {

    public String readDate;
    public int readTime;

    public ReadTime( String readDate,  int readTime) {
        this.readDate = readDate;
        this.readTime = readTime;
    }


    public String getReadDate() {
        return readDate;
    }

    public void setReadDate(String readDate) {
        this.readDate = readDate;
    }


    public int getReadTime() {
        return readTime;
    }

    public void setReadTime( int readTime) {
        this.readTime = readTime;
    }
}
