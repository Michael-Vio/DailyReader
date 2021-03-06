package com.example.dailyreader.entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ReadTime {

    @PrimaryKey
    @ColumnInfo(name = "read_date")
    @NonNull
    public String readDate;

    @ColumnInfo(name = "read_time")
    public int readTime;

    public ReadTime(@NonNull String readDate, int readTime) {
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
