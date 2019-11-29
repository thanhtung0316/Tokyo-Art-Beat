package com.thanhtung.mockproject.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "category_event")
public class EventInCateGory extends Event {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_event_in_category")
    private long id_event_in_category;

    public long getId_event_in_category() {
        return id_event_in_category;
    }

    public void setId_event_in_category(long id_event_in_category) {
        this.id_event_in_category = id_event_in_category;
    }
}
