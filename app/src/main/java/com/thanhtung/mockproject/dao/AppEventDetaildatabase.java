package com.thanhtung.mockproject.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.thanhtung.mockproject.model.EventDetail;

@Database(entities = EventDetail.class, version = 1)
public abstract class AppEventDetaildatabase extends RoomDatabase {
    private static AppEventDetaildatabase appEventDetaildatabase;

    public static AppEventDetaildatabase getInstance(Context context, String databaseName) {
        if (appEventDetaildatabase == null) {
            appEventDetaildatabase = Room.databaseBuilder(context, AppEventDetaildatabase.class, databaseName)
                    .allowMainThreadQueries().build();
        }
        return appEventDetaildatabase;
    }
    public abstract EventDetailDao getEventDetailDao();

}
