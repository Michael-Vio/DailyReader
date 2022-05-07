package com.example.dailyreader.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "User_table")
public class User {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "User_email")
    private String user_email;

    @NonNull
    @ColumnInfo(name = "Username")
    private String username;

    @NonNull
    @ColumnInfo(name = "Gender")
    private String gender;

    @NonNull
    @ColumnInfo(name = "Address")
    private String address;

    public User(@NonNull String user_email, @NonNull String username, @NonNull String gender, @NonNull String address){
        this.user_email = user_email;
        this.username = username;
        this.gender = gender;
        this.address = address;
    }

    @NonNull
    public String getUser_email(){return user_email;}
    public void setUser_email(@NonNull String user_email){this.user_email = user_email;}

    @NonNull
    public String getUsername(){return username;}
    public void setUsername(@NonNull String username){this.username = username;}
    @NonNull
    public String getGender(){return gender;}
    public void setGender(@NonNull String gender){this.username = gender;}
    @NonNull
    public String getAddress(){return address;}
    public void setAddress(@NonNull String address){this.username = address;}


}
