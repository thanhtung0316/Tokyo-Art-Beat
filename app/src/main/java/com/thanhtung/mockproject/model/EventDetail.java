package com.thanhtung.mockproject.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "eventDetail")
public class EventDetail {
    @PrimaryKey
    @SerializedName("id_venue_detail")
    @ColumnInfo(name ="id")
    private long id;

    @SerializedName("status")
    @ColumnInfo(name = "status")
    private Integer status;

    @SerializedName("link")
    private String link;

    @SerializedName("photo")
    @ColumnInfo(name = "photo")
    private String photo;

    @SerializedName("name")
    @ColumnInfo(name = "event_detail_name")
    private String name;

    @ColumnInfo(name = "description_raw")
    @SerializedName("description_raw")
    private String descriptionRaw;

    @ColumnInfo(name = "description_html")
    @SerializedName("description_html")
    private String descriptionHtml;

    @ColumnInfo(name = "artist")
    @SerializedName("artist")
    private String artist;

    @ColumnInfo(name = "schedule_permanent")
    @SerializedName("schedule_permanent")
    private String schedulePermanent;

    @ColumnInfo(name = "schedule_date_warning")
    @SerializedName("schedule_date_warning")
    private String scheduleDateWarning;

    @ColumnInfo(name = "schedule_time_alert")
    @SerializedName("schedule_time_alert")
    private String scheduleTimeAlert;

    @ColumnInfo(name = "schedule_start_date")
    @SerializedName("schedule_start_date")
    private String scheduleStartDate;

    @ColumnInfo(name = "schedule_start_time")
    @SerializedName("schedule_start_time")
    private String scheduleStartTime;

    @ColumnInfo(name = "schedule_end_date")
    @SerializedName("schedule_end_date")
    private String scheduleEndDate;

    @ColumnInfo(name = "schedule_end_time")
    @SerializedName("schedule_end_time")
    private String scheduleEndTime;

    @ColumnInfo(name = "schedule_one_day_event")
    @SerializedName("schedule_one_day_event")
    private String scheduleOneDayEvent;

    @ColumnInfo(name = "schedule_extra")
    @SerializedName("schedule_extra")
    private String scheduleExtra;

    @ColumnInfo(name = "going_count")
    @SerializedName("going_count")
    private Integer goingCount;

    @ColumnInfo(name = "went_count")
    @SerializedName("went_count")
    private Integer wentCount;


    @SerializedName("venue")
    @Embedded
    private Venue venue;


    @Embedded
    @SerializedName("category")
    private Category category;

    public long getId() {
        return id;
    }

    public Integer getStatus() {
        return status;
    }

    public String getLink() {
        return link;
    }

    public String getPhoto() {
        return photo;
    }

    public String getName() {
        return name;
    }

    public String getDescriptionRaw() {
        return descriptionRaw;
    }

    public String getDescriptionHtml() {
        return descriptionHtml;
    }

    public String getArtist() {
        return artist;
    }

    public String getSchedulePermanent() {
        return schedulePermanent;
    }

    public String getScheduleDateWarning() {
        return scheduleDateWarning;
    }

    public String getScheduleTimeAlert() {
        return scheduleTimeAlert;
    }

    public String getScheduleStartDate() {
        return scheduleStartDate;
    }

    public String getScheduleStartTime() {
        return scheduleStartTime;
    }

    public String getScheduleEndDate() {
        return scheduleEndDate;
    }

    public String getScheduleEndTime() {
        return scheduleEndTime;
    }

    public String getScheduleOneDayEvent() {
        return scheduleOneDayEvent;
    }

    public String getScheduleExtra() {
        return scheduleExtra;
    }

    public Integer getGoingCount() {
        return goingCount;
    }

    public Integer getWentCount() {
        return wentCount;
    }

    public Venue getVenue() {
        return venue;
    }

    public Category getCategory() {
        return category;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescriptionRaw(String descriptionRaw) {
        this.descriptionRaw = descriptionRaw;
    }

    public void setDescriptionHtml(String descriptionHtml) {
        this.descriptionHtml = descriptionHtml;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setSchedulePermanent(String schedulePermanent) {
        this.schedulePermanent = schedulePermanent;
    }

    public void setScheduleDateWarning(String scheduleDateWarning) {
        this.scheduleDateWarning = scheduleDateWarning;
    }

    public void setScheduleTimeAlert(String scheduleTimeAlert) {
        this.scheduleTimeAlert = scheduleTimeAlert;
    }

    public void setScheduleStartDate(String scheduleStartDate) {
        this.scheduleStartDate = scheduleStartDate;
    }

    public void setScheduleStartTime(String scheduleStartTime) {
        this.scheduleStartTime = scheduleStartTime;
    }

    public void setScheduleEndDate(String scheduleEndDate) {
        this.scheduleEndDate = scheduleEndDate;
    }

    public void setScheduleEndTime(String scheduleEndTime) {
        this.scheduleEndTime = scheduleEndTime;
    }

    public void setScheduleOneDayEvent(String scheduleOneDayEvent) {
        this.scheduleOneDayEvent = scheduleOneDayEvent;
    }

    public void setScheduleExtra(String scheduleExtra) {
        this.scheduleExtra = scheduleExtra;
    }

    public void setGoingCount(Integer goingCount) {
        this.goingCount = goingCount;
    }

    public void setWentCount(Integer wentCount) {
        this.wentCount = wentCount;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}