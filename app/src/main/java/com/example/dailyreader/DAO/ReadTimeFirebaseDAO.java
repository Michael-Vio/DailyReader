package com.example.dailyreader.DAO;


import androidx.annotation.NonNull;

import com.example.dailyreader.entity.ReadTime;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class ReadTimeFirebaseDAO {
    private DatabaseReference databaseReference;

    public ReadTimeFirebaseDAO(String userId, String date)
    {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User").child(userId).child("ReadTimeRecords").child(date);
    }

    public Task<Void> add(ReadTime readTime) {

        return databaseReference.setValue(readTime);
    }

    public Task<Void> update(String key, HashMap<String, Object> hashMap) {
        return  databaseReference.child(key).updateChildren(hashMap);
    }



    public void upload(String date, int minutes) {

        databaseReference.orderByChild("readDate").equalTo(date).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HashMap<String, Object> readTime = new HashMap<>();

                    readTime.put("readDate", date);
                    readTime.put("readTime", minutes);
                    update(snapshot.getKey(), readTime);
                } else {
                    ReadTime readTime = new ReadTime(date, minutes);
                    add(readTime);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public Query get() {
        return databaseReference.getParent().orderByChild("readDate");
    }

}

