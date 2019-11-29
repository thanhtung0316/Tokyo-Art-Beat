package com.thanhtung.mockproject.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.thanhtung.mockproject.model.Event;

import java.util.List;

@Dao
public interface EventDao {
    @Query("SELECT * FROM event ORDER BY going_count DESC")
    List<Event> getAll();

    @Insert
    void insert(List<Event> events);

    @Update
    void update(List<Event> events);

    @Query("DELETE FROM event")
    void deleteAll();
}