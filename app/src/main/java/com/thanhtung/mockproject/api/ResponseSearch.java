package com.thanhtung.mockproject.api;

import com.google.gson.annotations.SerializedName;
import com.thanhtung.mockproject.model.EventInSearch;

import java.util.List;

public class ResponseSearch {

    @SerializedName("events")
    private List<EventInSearch> events;


    public List<EventInSearch> getEvents() {
        return events;
    }


}
