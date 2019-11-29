package com.thanhtung.mockproject.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "marker")
public class MarkerEvent {
    @PrimaryKey(autoGenerate = true)
    private long markerID;

    @ColumnInfo(name ="latitude")
    private double latitude;

    @ColumnInfo(name ="longitude")
    private double longitude;

    @ColumnInfo(name ="eventName")
    private String eventName;

    @ColumnInfo(name ="eventDesc")
    private String eventDesc;

    @ColumnInfo(name ="dateStartEvent")
    private String dateStartEvent;

    @ColumnInfo(name ="dateEndEvent" )
    private String dateEndEvent;

    @ColumnInfo(name ="goingCount")
    private int goingCount;

    @ColumnInfo(name ="distance")
    private float distance;

    @ColumnInfo(name = "photo")
    private String photo;

    public long getMarkerID() {
        return markerID;
    }

    public void setMarkerID(long markerID) {
        this.markerID = markerID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public String getDateStartEvent() {
        return dateStartEvent;
    }

    public void setDateStartEvent(String dateStartEvent) {
        this.dateStartEvent = dateStartEvent;
    }

    public String getDateEndEvent() {
        return dateEndEvent;
    }

    public void setDateEndEvent(String dateEndEvent) {
        this.dateEndEvent = dateEndEvent;
    }

    public int getGoingCount() {
        return goingCount;
    }

    public void setGoingCount(int goingCount) {
        this.goingCount = goingCount;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}