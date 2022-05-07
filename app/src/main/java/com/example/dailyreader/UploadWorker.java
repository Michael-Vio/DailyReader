package com.example.dailyreader;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.dailyreader.DAO.ReadTimeDAO;
import com.example.dailyreader.DAO.ReadTimeFirebaseDAO;
import com.example.dailyreader.database.ReadTimeDatabase;
import com.example.dailyreader.entity.ReadTime;
import com.google.firebase.auth.FirebaseAuth;


import java.util.List;


public class UploadWorker extends Worker {
    public UploadWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    public void synToFirebase() {
        Log.d("synToFirebase()", "start");
        ReadTimeDatabase readTimeDatabase = ReadTimeDatabase.getInstance(getApplicationContext());
        ReadTimeDAO readTimeDAO = readTimeDatabase.readTimeDAO();
        Log.d("synToFirebase()", "retrieve data from room");
        List<ReadTime> readTimeList = readTimeDAO.getAll();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String userId = firebaseAuth.getCurrentUser().getUid();
        Log.d("synToFirebase()", "synchronize to firebase");
        for (ReadTime readTime : readTimeList) {
            ReadTimeFirebaseDAO firebaseDAO = new ReadTimeFirebaseDAO(userId, readTime.getReadDate());
            firebaseDAO.upload(readTime.getReadDate(), readTime.getReadTime());
        }

        Log.d("synToFirebase()", "method end");
    }

    @NonNull
    @Override
    public Result doWork() {

        Log.d("UploadWorker", "doWork() start");
        synToFirebase();
        Log.d("UploadWorker", "doWork() end");
        return Result.success();
    }
}