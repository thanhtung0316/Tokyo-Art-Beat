package com.thanhtung.mockproject.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.thanhtung.mockproject.model.Event;
import com.thanhtung.mockproject.model.MarkerEvent;

import java.util.List;

@Dao
public interface MarkerDao {
    @Query("SELECT * FROM marker ORDER BY goingCount DESC")
    List<MarkerEvent> getAll();

    @Insert
    void insert(List<MarkerEvent> events);

    @Update
    void update(List<MarkerEvent> events);

    @Query("DELETE FROM marker")
    void deleteAll();
}