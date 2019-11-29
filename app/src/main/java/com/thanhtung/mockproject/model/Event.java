package com.thanhtung.mockproject.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "event")
public class Event  {
    @PrimaryKey()
    @ColumnInfo(name = "id_event")
    @SerializedName("id")
    private long id;

    @ColumnInfo(name = "status_event")
    @SerializedName("status")
    private Integer status;

    @ColumnInfo(name = "photo")
    @SerializedName("photo")
    private String photo;

    @ColumnInfo(name = "name_event")
    @SerializedName("name")
    private String name;

    @ColumnInfo(name = "description_raw")
    @SerializedName("description_raw")
    private String descriptionRaw;

    @ColumnInfo(name = "description_html")
    @SerializedName("description_html")
    private String descriptionHtml;

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
    private VenuePopular venue;


    public VenuePopular getVenue() {
        return venue;
    }

    public void setVenue(VenuePopular venue) {
        this.venue = venue;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptionRaw() {
        return descriptionRaw;
    }

    public void setDescriptionRaw(String descriptionRaw) {
        this.descriptionRaw = descriptionRaw;
    }

    public String getDescriptionHtml() {
        return descriptionHtml;
    }

    public void setDescriptionHtml(String descriptionHtml) {
        this.descriptionHtml = descriptionHtml;
    }

    public String getSchedulePermanent() {
        return schedulePermanent;
    }

    public void setSchedulePermanent(String schedulePermanent) {
        this.schedulePermanent = schedulePermanent;
    }

    public String getScheduleDateWarning() {
        return scheduleDateWarning;
    }

    public void setScheduleDateWarning(String scheduleDateWarning) {
        this.scheduleDateWarning = scheduleDateWarning;
    }

    public String getScheduleTimeAlert() {
        return scheduleTimeAlert;
    }

    public void setScheduleTimeAlert(String scheduleTimeAlert) {
        this.scheduleTimeAlert = scheduleTimeAlert;
    }

    public String getScheduleStartDate() {
        return scheduleStartDate;
    }

    public void setScheduleStartDate(String scheduleStartDate) {
        this.scheduleStartDate = scheduleStartDate;
    }

    public String getScheduleStartTime() {
        return scheduleStartTime;
    }

    public void setScheduleStartTime(String scheduleStartTime) {
        this.scheduleStartTime = scheduleStartTime;
    }

    public String getScheduleEndDate() {
        return scheduleEndDate;
    }

    public void setScheduleEndDate(String scheduleEndDate) {
        this.scheduleEndDate = scheduleEndDate;
    }

    public String getScheduleEndTime() {
        return scheduleEndTime;
    }

    public void setScheduleEndTime(String scheduleEndTime) {
        this.scheduleEndTime = scheduleEndTime;
    }

    public String getScheduleOneDayEvent() {
        return scheduleOneDayEvent;
    }

    public void setScheduleOneDayEvent(String scheduleOneDayEvent) {
        this.scheduleOneDayEvent = scheduleOneDayEvent;
    }

    public String getScheduleExtra() {
        return scheduleExtra;
    }

    public void setScheduleExtra(String scheduleExtra) {
        this.scheduleExtra = scheduleExtra;
    }

    public Integer getGoingCount() {
        return goingCount;
    }

    public void setGoingCount(Integer goingCount) {
        this.goingCount = goingCount;
    }

    public Integer getWentCount() {
        return wentCount;
    }

    public void setWentCount(Integer wentCount) {
        this.wentCount = wentCount;
    }

}