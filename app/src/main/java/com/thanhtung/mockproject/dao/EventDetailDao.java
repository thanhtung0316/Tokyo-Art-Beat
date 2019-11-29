package com.thanhtung.mockproject.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.thanhtung.mockproject.model.EventDetail;
import java.util.List;

@Dao
public interface EventDetailDao {
    @Query("SELECT * FROM eventDetail ORDER BY going_count DESC")
    List<EventDetail> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<EventDetail> eventDetails);

    @Query("DELETE FROM eventDetail")
    void deleteAll();

//    @Query("SELECT * FROM eventDetail WHERE id_venue = 723291")
//    EventDetail getEventDetail();
}