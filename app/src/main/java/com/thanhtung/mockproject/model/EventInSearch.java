package com.thanhtung.mockproject.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "search_event")
public class EventInSearch extends Event {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_search_event")
    private Integer id_search_event;


    public Integer getId_search_event() {
        return id_search_event;
    }

    public void setId_search_event(Integer id_search_event) {
        this.id_search_event = id_search_event;
    }
}