package com.thanhtung.mockproject.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.thanhtung.mockproject.model.Event;
import com.thanhtung.mockproject.model.EventInSearch;

import java.util.List;

@Dao
public interface EventInSearchDao {
    @Query("SELECT * FROM search_event ORDER BY going_count DESC")
    List<Event> getAll();

    @Insert
    void insert(List<EventInSearch> events);

    @Insert
    void insert(EventInSearch event);

    @Update
    void update(List<EventInSearch> events);

    @Query("DELETE FROM search_event")
    void deleteAll();
}