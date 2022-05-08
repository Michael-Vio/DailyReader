package com.example.dailyreader;


import android.app.Application;

import com.example.dailyreader.entity.ReadTime;
import com.example.dailyreader.repository.ReadTimeRepository;

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
    private ReadTimeRepository readTimeRepository;


    public RecordReadTime(Application application) {
        readTimeRepository = new ReadTimeRepository(application);
    }

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
        ReadTime readTime = findRecord(startDate);
        if (readTime != null) {
            int newMinutes = readTime.getReadTime() + minutes;
            readTime.setReadTime(newMinutes);
            readTimeRepository.update(readTime);
        } else {
            readTime = new ReadTime(startDate, minutes);
            readTimeRepository.insert(readTime);
        }
    }

    public ReadTime findRecord(String date) {
        ReadTime readTime = null;
        CompletableFuture<ReadTime> readTimeCompletableFuture =  readTimeRepository.findByDateFuture(date);
        try {
            readTime = readTimeCompletableFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return readTime;
    }
}
