package com.example.dailyreader;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.example.dailyreader.entity.ReadTime;
import com.example.dailyreader.viewmodel.ReadTimeViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RecordReadTime {
    private Date startTime;
    private Date endTime;
    private String startDate;
    private ReadTimeViewModel readTimeViewModel;
    private ReadTime readTime;

    public void startRecord(Activity activity) {
        readTimeViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(activity.getApplication()).create(ReadTimeViewModel.class);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        startTime = new Date(System.currentTimeMillis());
        String time = dateFormat.format(startTime);
        startDate = time.split(" ")[0];

        CompletableFuture<ReadTime> readTimeCompletableFuture = readTimeViewModel.findByDateFuture(startDate);
        try {
            readTime = readTimeCompletableFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void stopRecord() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        endTime = new Date(System.currentTimeMillis());
        String time = dateFormat.format(endTime);
        String endDate = time.split(" ")[0];

        writeRecord();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("users");
        reference.push().setValue("Hello World");
//        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
//        String TAG = "TTTTTest";
//        connectedRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                boolean connected = snapshot.getValue(Boolean.class);
//                if (connected) {
//                    Log.d(TAG, "connected");
//                } else {
//                    Log.d(TAG, "not connected");
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.w(TAG, "Listener was cancelled");
//            }
//        });
        Log.d("msg", "write to db");

    }

    public void writeRecord() {
        long difference = startTime.getTime() - endTime.getTime();
        int minutes = (int)(difference % (1000 * 60 * 60)) / (1000 * 60);
        if (readTime != null) {
            readTime.setReadTime(minutes);
            readTimeViewModel.update(readTime);
        } else {
            readTimeViewModel.insert(new ReadTime(startDate, minutes));
        }
    }



}
