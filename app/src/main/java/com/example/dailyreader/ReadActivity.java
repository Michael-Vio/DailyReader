package com.example.dailyreader;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dailyreader.databinding.ActivityReadBinding;


public class ReadActivity extends AppCompatActivity {
    private ActivityReadBinding activityReadBinding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityReadBinding = ActivityReadBinding.inflate(getLayoutInflater());
        View view = activityReadBinding.getRoot();
        setContentView(view);
    }
}
