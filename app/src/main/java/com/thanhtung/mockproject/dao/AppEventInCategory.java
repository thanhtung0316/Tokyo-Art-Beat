package com.thanhtung.mockproject.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.thanhtung.mockproject.model.EventInCateGory;
import com.thanhtung.mockproject.model.EventInSearch;

@Database(entities = EventInCateGory.class, version = 1)
public abstract class AppEventInCategory extends RoomDatabase {
    private static AppEventInCategory appEventdatabase;

    public static AppEventInCategory getInstance(Context context, String databaseName) {
        if (appEventdatabase == null) {
            appEventdatabase = Room.databaseBuilder(context, AppEventInCategory.class, databaseName)
                    .allowMainThreadQueries().build();
        }
        return appEventdatabase;
    }
    public abstract EventInCategoryDao getEventDao();

}
