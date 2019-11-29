package com.thanhtung.mockproject.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.thanhtung.mockproject.model.Category;
import com.thanhtung.mockproject.model.EventDetail;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM category ")
    List<Category> getAll();

    @Insert
    void insert(List<Category> categories);

    @Query("DELETE FROM category")
    void deleteAll();

//    @Query("SELECT * FROM eventDetail WHERE id_venue = 723291")
//    EventDetail getEventDetail();
}