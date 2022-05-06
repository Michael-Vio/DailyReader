package com.example.dailyreader;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.dailyreader.viewmodel.ReadTimeViewModel;


public class UploadWorker extends Worker {
    public UploadWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    public void synToFirebase() {
//        ReadTimeViewModel readTimeViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ReadTimeViewModel.class);
//                final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference ref = database.getReference("users").child("records");
//        ReadTimeFirebaseDAO readTimeFirebaseDAO = new ReadTimeFirebaseDAO(startDate);
//        readTimeDAO.upload(startDate, minutes);
//
//        ref.child(startDate).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Integer totalReadMinute = dataSnapshot.child("readTime").getValue(int.class);
//                if (totalReadMinute!=null) {
//                    int min = totalReadMinute + minutes;
//                    readTimeFirebaseDAO.upload(startDate, min);
//                    Log.d("xxx", totalReadMinute + "");
//                } else {
////                    ReadTime readTime = new ReadTime(startDate, minutes);
//                    readTimeFirebaseDAO.add(readTime);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                System.out.println("The read failed: " + databaseError.getCode());
//            }
//        });
        Log.d("msg", "start");
    }

    @Override
    public Result doWork() {

        Log.d("UploadWorker:", "doWork() start");
        synToFirebase();
        return Result.success();
    }
}
