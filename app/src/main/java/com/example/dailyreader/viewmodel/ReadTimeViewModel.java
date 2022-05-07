package com.example.dailyreader.viewmodel;

import android.app.Application;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import com.example.dailyreader.entity.ReadTime;
import com.example.dailyreader.repository.ReadTimeRepository;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public class ReadTimeViewModel extends AndroidViewModel {
    private final ReadTimeRepository rRepository;
    private final List<ReadTime> allReadTimes;
    public ReadTimeViewModel (Application application) {
        super(application);
        rRepository = new ReadTimeRepository(application);
        allReadTimes = rRepository.getAllReadTimes();
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<ReadTime> findByDateFuture(final String readDate){
        return rRepository.findByDateFuture(readDate);
    }
    public List<ReadTime> getAllReadTimes() {

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

        rRepository.updateReadTime(readTime);
    }
}
