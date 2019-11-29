package com.thanhtung.mockproject.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.thanhtung.mockproject.model.Event;
import com.thanhtung.mockproject.model.MarkerEvent;

@Database(entities = MarkerEvent.class, version = 1)
public abstract class AppMarkerdatabase extends RoomDatabase {
    private static AppMarkerdatabase appEventdatabase;

    public static AppMarkerdatabase getInstance(Context context, String databaseName) {
        if (appEventdatabase == null) {
            appEventdatabase = Room.databaseBuilder(context, AppMarkerdatabase.class, databaseName)
                    .allowMainThreadQueries().build();
        }
        return appEventdatabase;
    }
    public abstract MarkerDao getMarkerDao();

}
