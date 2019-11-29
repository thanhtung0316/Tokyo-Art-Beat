package com.thanhtung.mockproject.dao;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.thanhtung.mockproject.model.Event;

@Database(entities = Event.class, version = 1)
public abstract class AppEventdatabase extends RoomDatabase {
    private static AppEventdatabase appEventdatabase;

    public static AppEventdatabase getInstance(Context context, String databaseName) {
        if (appEventdatabase == null) {
            appEventdatabase = Room.databaseBuilder(context, AppEventdatabase.class, databaseName)
                    .allowMainThreadQueries().build();
        }
        return appEventdatabase;
    }
    public abstract EventDao getEventDao();

}
