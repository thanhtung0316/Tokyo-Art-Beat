package com.thanhtung.mockproject.api;

import com.google.gson.annotations.SerializedName;
import com.thanhtung.mockproject.model.EventDetail;

public class ResponeEventDetail {
    @SerializedName("events")
    private EventDetail eventDetail;

    public EventDetail getEventDetail() {
        return eventDetail;
    }
}
