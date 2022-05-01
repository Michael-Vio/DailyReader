package com.example.dailyreader.entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ReadTime {

    @PrimaryKey(autoGenerate = true)
    public int rid;

    @ColumnInfo(name = "read_date")
    @NonNull
    public String readDate;

    @ColumnInfo(name = "read_time")
    @NonNull
    public int readTime;

    public ReadTime(@NonNull String readDate, @NonNull int readTime) {
        this.readDate = readDate;
        this.readTime = readTime;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    @NonNull
    public String getReadDate() {
        return readDate;
    }

    public void setReadDate(@NonNull String readDate) {
        this.readDate = readDate;
    }

    @NonNull
    public int getReadTime() {
        return readTime;
    }

    public void setReadTime(@NonNull int readTime) {
        this.readTime = readTime;
    }
}
