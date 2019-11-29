package com.thanhtung.mockproject.api;

import com.google.gson.annotations.SerializedName;
import com.thanhtung.mockproject.model.EventInCateGory;

import java.util.List;

public class ResponeIncategory {
    @SerializedName("events")
    private List<EventInCateGory> events;

    public List<EventInCateGory> getEvents() {
        return events;
    }
}
