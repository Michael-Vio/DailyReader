package com.example.dailyreader.viewmodel;

import android.app.Application;
import android.os.Build;


import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.dailyreader.entity.ReadTime;
import com.example.dailyreader.repository.ReadTimeRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;


public class ReadTimeViewModel extends AndroidViewModel {
    private final ReadTimeRepository rRepository;
    private final LiveData<List<ReadTime>> allReadTimes;
    public ReadTimeViewModel (Application application) {
        super(application);
        rRepository = new ReadTimeRepository(application);
        allReadTimes = rRepository.getReadTimes();
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<ReadTime> findByDateFuture(final String readTimeDate){
        return rRepository.findByDateFuture(readTimeDate);
    }
    public LiveData<List<ReadTime>> getReadTimes() {

        return allReadTimes;
    }
    public void insert(ReadTime readTime) {

        rRepository.insert(readTime);
    }
    public void delete(ReadTime readTime) {

        rRepository.delete(readTime);
    }
    public void deleteAll() {

        rRepository.deleteAll();
    }
    public void update(ReadTime readTime) {

        rRepository.updateBook(readTime);
    }


}





