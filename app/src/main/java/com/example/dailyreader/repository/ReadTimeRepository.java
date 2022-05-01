package com.example.dailyreader.repository;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import com.example.dailyreader.DAO.ReadTimeDAO;
import com.example.dailyreader.database.ReadTimeDatabase;
import com.example.dailyreader.entity.ReadTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;


public class ReadTimeRepository {
    private final ReadTimeDAO readTimeDAO;
    private final LiveData<List<ReadTime>> allReadTimes;

    public ReadTimeRepository(Application application){
        ReadTimeDatabase readTimeDb = ReadTimeDatabase.getInstance(application);
        readTimeDAO = readTimeDb.readTimeDAO();
        allReadTimes = readTimeDAO.getAll();
    }
    // Room executes this query on a separate thread
    public LiveData<List<ReadTime>> getReadTimes() {

        return allReadTimes;
    }

    public void insert(final ReadTime book){
        ReadTimeDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {

                readTimeDAO.insert(book);
            }
        });
    }
    public void deleteAll(){
        ReadTimeDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {

                readTimeDAO.deleteAll();
            }
        });
    }
    public void delete(final ReadTime readTime){
        ReadTimeDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {

                readTimeDAO.delete(readTime);
            }
        });
    }
    public void updateBook(final ReadTime readTime){
        ReadTimeDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {

                readTimeDAO.updateBook(readTime);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<ReadTime> findByDateFuture(final String readTimeDate) {
        return CompletableFuture.supplyAsync(new Supplier<ReadTime>() {
            @Override
            public ReadTime get() {
                return readTimeDAO.findByDate(readTimeDate);
            }
        }, ReadTimeDatabase.databaseWriteExecutor);
    }

}
