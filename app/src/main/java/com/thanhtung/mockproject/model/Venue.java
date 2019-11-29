package com.thanhtung.mockproject.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
@Entity(tableName = "venue")
public class Venue {

    @SerializedName("id")
    @ColumnInfo(name = "id_venue")
    private long id_venue;

    @SerializedName("name")
    @ColumnInfo(name = "venue_name")
    private String name;

    @ColumnInfo(name = "type")
    @SerializedName("type")
    private Integer type;

    @ColumnInfo(name = "description")
    @SerializedName("description")
    private String description;

    @ColumnInfo(name = "permanent")
    @SerializedName("permanent")
    private String permanent;

    @ColumnInfo(name = "contact_fee")
    @SerializedName("contact_fee")
    private String contactFee;

    @ColumnInfo(name = "contact_phone")
    @SerializedName("contact_phone")
    private String contactPhone;

    @ColumnInfo(name = "contact_fax")
    @SerializedName("contact_fax")
    private String contactFax;

    @ColumnInfo(name = "contact_web")
    @SerializedName("contact_web")
    private String contactWeb;

    @ColumnInfo(name = "contact_web_lang")
    @SerializedName("contact_web_lang")
    private String contactWebLang;

    @ColumnInfo(name = "contact_address")
    @SerializedName("contact_address")
    private String contactAddress;

    @ColumnInfo(name = "contact_access")
    @SerializedName("contact_access")
    private String contactAccess;

    @ColumnInfo(name = "contact_discount")
    @SerializedName("contact_discount")
    private String contactDiscount;

    @ColumnInfo(name = "contact_discount_details")
    @SerializedName("contact_discount_details")
    private String contactDiscountDetails;

    @ColumnInfo(name = "geo_area")
    @SerializedName("geo_area")
    private String geoArea;

    @ColumnInfo(name = "geo_long")
    @SerializedName("geo_long")
    private Double geoLong;

    @ColumnInfo(name = "geo_lat")
    @SerializedName("geo_lat")
    private Double geoLat;

    @ColumnInfo(name = "schedule_openinghour")
    @SerializedName("schedule_openinghour")
    private String scheduleOpeninghour;

    @ColumnInfo(name = "schedule_closinghour")
    @SerializedName("schedule_closinghour")
    private String scheduleClosinghour;

    @ColumnInfo(name = "schedule_breakstart")
    @SerializedName("schedule_breakstart")
    private String scheduleBreakstart;

    @ColumnInfo(name = "schedule_breakend")
    @SerializedName("schedule_breakend")
    private String scheduleBreakend;

    @ColumnInfo(name = "schedule_openingdetails")
    @SerializedName("schedule_openingdetails")
    private String scheduleOpeningdetails;

    @ColumnInfo(name = "schedule_closed")
    @SerializedName("schedule_closed")
    private String scheduleClosed;

    public long getId_venue() {
        return id_venue;
    }

    public String getName() {
        return name;
    }

    public Integer getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getPermanent() {
        return permanent;
    }

    public String getContactFee() {
        return contactFee;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public String getContactFax() {
        return contactFax;
    }

    public String getContactWeb() {
        return contactWeb;
    }

    public String getContactWebLang() {
        return contactWebLang;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public String getContactAccess() {
        return contactAccess;
    }

    public String getContactDiscount() {
        return contactDiscount;
    }

    public String getContactDiscountDetails() {
        return contactDiscountDetails;
    }

    public String getGeoArea() {
        return geoArea;
    }

    public Double getGeoLong() {
        return geoLong;
    }

    public Double getGeoLat() {
        return geoLat;
    }

    public String getScheduleOpeninghour() {
        return scheduleOpeninghour;
    }

    public String getScheduleClosinghour() {
        return scheduleClosinghour;
    }

    public String getScheduleBreakstart() {
        return scheduleBreakstart;
    }

    public String getScheduleBreakend() {
        return scheduleBreakend;
    }

    public String getScheduleOpeningdetails() {
        return scheduleOpeningdetails;
    }

    public String getScheduleClosed() {
        return scheduleClosed;
    }

    public void setId_venue(long id_venue) {
        this.id_venue = id_venue;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPermanent(String permanent) {
        this.permanent = permanent;
    }

    public void setContactFee(String contactFee) {
        this.contactFee = contactFee;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public void setContactFax(String contactFax) {
        this.contactFax = contactFax;
    }

    public void setContactWeb(String contactWeb) {
        this.contactWeb = contactWeb;
    }

    public void setContactWebLang(String contactWebLang) {
        this.contactWebLang = contactWebLang;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public void setContactAccess(String contactAccess) {
        this.contactAccess = contactAccess;
    }

    public void setContactDiscount(String contactDiscount) {
        this.contactDiscount = contactDiscount;
    }

    public void setContactDiscountDetails(String contactDiscountDetails) {
        this.contactDiscountDetails = contactDiscountDetails;
    }

    public void setGeoArea(String geoArea) {
        this.geoArea = geoArea;
    }

    public void setGeoLong(Double geoLong) {
        this.geoLong = geoLong;
    }

    public void setGeoLat(Double geoLat) {
        this.geoLat = geoLat;
    }

    public void setScheduleOpeninghour(String scheduleOpeninghour) {
        this.scheduleOpeninghour = scheduleOpeninghour;
    }

    public void setScheduleClosinghour(String scheduleClosinghour) {
        this.scheduleClosinghour = scheduleClosinghour;
    }

    public void setScheduleBreakstart(String scheduleBreakstart) {
        this.scheduleBreakstart = scheduleBreakstart;
    }

    public void setScheduleBreakend(String scheduleBreakend) {
        this.scheduleBreakend = scheduleBreakend;
    }

    public void setScheduleOpeningdetails(String scheduleOpeningdetails) {
        this.scheduleOpeningdetails = scheduleOpeningdetails;
    }

    public void setScheduleClosed(String scheduleClosed) {
        this.scheduleClosed = scheduleClosed;
    }
}