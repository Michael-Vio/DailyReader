package com.example.dailyreader.repository;

import android.app.Application;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.dailyreader.DAO.ReadTimeDAO;
import com.example.dailyreader.database.ReadTimeDatabase;
import com.example.dailyreader.entity.ReadTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class ReadTimeRepository {
    private final ReadTimeDAO readTimeDao;

    public ReadTimeRepository(Application application){
        ReadTimeDatabase ReadTimeDb = ReadTimeDatabase.getInstance(application);
        readTimeDao = ReadTimeDb.readTimeDAO();
    }

    public CompletableFuture<List<ReadTime>> getAllReadTimes() {
        return CompletableFuture.supplyAsync(new Supplier<List<ReadTime>>() {
            @Override
            public List<ReadTime> get() {
                return readTimeDao.getAll();
            }
        }, ReadTimeDatabase.databaseWriteExecutor);
    }

    public void insert(final ReadTime readTime){
        ReadTimeDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {

                readTimeDao.insert(readTime);
            }
        });
    }
    public void deleteAll(){
        ReadTimeDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {

                readTimeDao.deleteAll();
            }
        });
    }
    public void delete(final ReadTime readTime){
        ReadTimeDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {

                readTimeDao.delete(readTime);
            }
        });
    }
    public void update(final ReadTime readTime){
        ReadTimeDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {

                readTimeDao.updateReadTime(readTime);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<ReadTime> findByDateFuture(final String readDate) {
        return CompletableFuture.supplyAsync(new Supplier<ReadTime>() {
            @Override
            public ReadTime get() {
                return readTimeDao.findByDate(readDate);
            }
        }, ReadTimeDatabase.databaseWriteExecutor);
    }
}
