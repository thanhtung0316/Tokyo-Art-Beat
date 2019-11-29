package com.thanhtung.mockproject.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "venue_popular")
public class VenuePopular {

    @ColumnInfo(name = "venue_id")
    @SerializedName("venue_id")
    private long venue_id;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    private String name;

    @ColumnInfo(name = "type")
    @SerializedName("type")
    private String type;

    @ColumnInfo(name = "description")
    @SerializedName("description")
    private String description;

    @ColumnInfo(name = "schedule_openinghour")
    @SerializedName("schedule_openinghour")
    private String scheduleOpeninghour;

    @ColumnInfo(name = "schedule_closinghour")
    @SerializedName("schedule_closinghour")
    private String scheduleClosinghour;

    @ColumnInfo(name = "schedule_closed")
    @SerializedName("schedule_closed")
    private String scheduleClosed;

    @ColumnInfo(name = "geo_long")
    @SerializedName("geo_long")
    private double geoLong;


    @ColumnInfo(name = "geo_lat")
    @SerializedName("geo_lat")
    private double geoLat;


    public double getGeoLong() {
        return geoLong;
    }

    public void setGeoLong(double geoLong) {
        this.geoLong = geoLong;
    }

    public double getGeoLat() {
        return geoLat;
    }

    public void setGeoLat(double geoLat) {
        this.geoLat = geoLat;
    }

    public long getVenue_id() {
        return venue_id;
    }

    public void setVenue_id(long venue_id) {
        this.venue_id = venue_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getScheduleOpeninghour() {
        return scheduleOpeninghour;
    }

    public void setScheduleOpeninghour(String scheduleOpeninghour) {
        this.scheduleOpeninghour = scheduleOpeninghour;
    }

    public String getScheduleClosinghour() {
        return scheduleClosinghour;
    }

    public void setScheduleClosinghour(String scheduleClosinghour) {
        this.scheduleClosinghour = scheduleClosinghour;
    }

    public String getScheduleClosed() {
        return scheduleClosed;
    }

    public void setScheduleClosed(String scheduleClosed) {
        this.scheduleClosed = scheduleClosed;
    }
}
