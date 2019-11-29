package com.thanhtung.mockproject.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.thanhtung.mockproject.model.Event;
import com.thanhtung.mockproject.model.EventInSearch;

@Database(entities = EventInSearch.class, version = 1)
public abstract class AppEventInSearchdatabase extends RoomDatabase {
    private static AppEventInSearchdatabase appEventdatabase;

    public static AppEventInSearchdatabase getInstance(Context context, String databaseName) {
        if (appEventdatabase == null) {
            appEventdatabase = Room.databaseBuilder(context, AppEventInSearchdatabase.class, databaseName)
                    .allowMainThreadQueries().build();
        }
        return appEventdatabase;
    }
    public abstract EventInSearchDao getEventDao();

}
