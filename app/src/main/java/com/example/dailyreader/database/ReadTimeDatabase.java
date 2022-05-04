package com.example.dailyreader.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.dailyreader.DAO.ReadTimeDAO;
import com.example.dailyreader.entity.ReadTime;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ReadTime.class}, version = 1, exportSchema = false)
public abstract class ReadTimeDatabase extends RoomDatabase {
    public abstract ReadTimeDAO readTimeDAO();
    private static ReadTimeDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized ReadTimeDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ReadTimeDatabase.class, "ReadTimeDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
