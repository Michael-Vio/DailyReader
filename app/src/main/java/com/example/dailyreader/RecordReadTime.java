package com.example.dailyreader;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.example.dailyreader.DAO.ReadTimeFirebaseDAO;
import com.example.dailyreader.entity.ReadTime;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RecordReadTime {
    private Date startTime;
    private Date endTime;
    private String startDate;

    public void startRecord() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        startTime = new Date(System.currentTimeMillis());
        String time = dateFormat.format(startTime);
        startDate = time.split(" ")[0];


    }

    public void stopRecord() {
        endTime = new Date(System.currentTimeMillis());
        writeRecord();
    }

    public void writeRecord() {

        long difference = endTime.getTime() - startTime.getTime();
        int minutes = (int) (difference % (1000 * 60 * 60)) / (1000 * 60);
        ReadTimeFirebaseDAO readTimeDAO = new ReadTimeFirebaseDAO(startDate);
//        readTimeDAO.upload(startDate, minutes);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users").child("records");

// Attach a listener to read the data at our posts reference
        ref.child(startDate).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer totalReadMinute = dataSnapshot.child("readTime").getValue(int.class);
                if (totalReadMinute!=null) {
                    int min = totalReadMinute + minutes;
                    readTimeDAO.upload(startDate, min);
                    Log.d("xxx", totalReadMinute + "");
                } else {
                    ReadTime readTime = new ReadTime(startDate, minutes);
                    readTimeDAO.add(readTime);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });



    }



}
