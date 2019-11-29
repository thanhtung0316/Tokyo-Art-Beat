package com.thanhtung.mockproject.dao;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.thanhtung.mockproject.model.News;

@Database(entities = News.class, version = 1)
public abstract class AppNewsdatabase extends RoomDatabase {
    private static AppNewsdatabase appNewsdatabase;

    public static AppNewsdatabase getInstance(Context context, String databaseName) {
        if (appNewsdatabase == null) {
            appNewsdatabase = Room.databaseBuilder(context, AppNewsdatabase.class, databaseName)
                    .allowMainThreadQueries().build();
        }
        return appNewsdatabase;
    }
    public abstract NewsDao getNewsDao();

}
