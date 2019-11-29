package com.thanhtung.mockproject.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.thanhtung.mockproject.model.Category;
import com.thanhtung.mockproject.model.Event;
import com.thanhtung.mockproject.model.EventInCateGory;
import com.thanhtung.mockproject.model.EventInSearch;

import java.util.List;

@Dao
public interface EventInCategoryDao {

    @Query("SELECT * FROM category_event ORDER BY going_count DESC")
    List<EventInCateGory> getAll();


    @Query("SELECT * FROM category_event ORDER BY schedule_end_date DESC")
    List<EventInCateGory> getAllByDate();
    @Insert
    void insert(List<EventInCateGory> events);

    @Insert
    void insert(EventInCateGory event);

    @Query("DELETE FROM category_event")
    void deleteAll();
}