package com.thanhtung.mockproject.api;

import com.google.gson.annotations.SerializedName;
import com.thanhtung.mockproject.model.Event;
import com.thanhtung.mockproject.model.EventDetail;

import java.util.List;

public class ResponeNearlyEvent {
    @SerializedName("events")
    private List<Event> event;

    public List<Event> getEvent() {
        return event;
    }
}
