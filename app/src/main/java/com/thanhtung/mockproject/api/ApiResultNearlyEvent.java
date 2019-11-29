package com.thanhtung.mockproject.api;

import com.google.gson.annotations.SerializedName;

public class ApiResultNearlyEvent {
    @SerializedName("status")
    private Integer status;

    @SerializedName("response")
    private ResponeNearlyEvent response;

    public Integer getStatus() {
        return status;
    }

    public ResponeNearlyEvent getResponse() {
        return response;
    }
}