package com.thanhtung.mockproject.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.thanhtung.mockproject.model.Category;
import com.thanhtung.mockproject.model.Event;

@Database(entities = Category.class, version = 1)
public abstract class AppCategorydatabase extends RoomDatabase {
    private static AppCategorydatabase appCategorydatabase;

    public static AppCategorydatabase getInstance(Context context, String databaseName) {
        if (appCategorydatabase == null) {
            appCategorydatabase = Room.databaseBuilder(context, AppCategorydatabase.class, databaseName)
                    .allowMainThreadQueries().build();
        }
        return appCategorydatabase;
    }
    public abstract CategoryDao getCategoryDao();

}
