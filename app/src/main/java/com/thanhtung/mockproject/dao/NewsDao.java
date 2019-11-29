package com.thanhtung.mockproject.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.thanhtung.mockproject.model.News;

import java.util.List;

@Dao
public interface NewsDao {
    @Query("SELECT * FROM news")
    List<News> getAll();

    @Insert
    void insert(List<News> news);

    @Update
    void update(List<News> news);

    @Delete
    int delete(List<News> news);

    @Query("DELETE FROM news")
    void deleteAll();
}
