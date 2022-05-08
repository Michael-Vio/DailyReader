package com.example.dailyreader.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.dailyreader.DAO.ReadTimeDAO;
import com.example.dailyreader.entity.ReadTime;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ReadTime.class}, version = 2, exportSchema = false)
public abstract class ReadTimeDatabase extends RoomDatabase {
    public abstract ReadTimeDAO readTimeDAO();
    private static ReadTimeDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized ReadTimeDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ReadTimeDatabase.class, "ReadTimeDatabase")
                    .fallbackToDestructiveMigration()

                    //add call back to our database.
                    //.addCallback(readRoomDatabaseCallback)
                    .build();
        }
        return INSTANCE;
    }


    /*//Examples for reading time to test the report screen
    private static RoomDatabase.Callback readRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase database){
            super.onCreate(database);

            databaseWriteExecutor.execute(() -> {
                ReadTimeDAO readTimeDAO = INSTANCE.readTimeDAO();
                readTimeDAO.deleteAll();

                // try to insert some values into database
                ReadTime readTime = new ReadTime("2022-05-01", 10);
                readTimeDAO.insert(readTime);

                readTime = new ReadTime("2022-05-02", 30);
                readTimeDAO.insert(readTime);

                readTime = new ReadTime("2022-05-03", 44);
                readTimeDAO.insert(readTime);

                readTime = new ReadTime("2022-05-04", 55);
                readTimeDAO.insert(readTime);

            });
        }

        };*/

}
