package com.thanhtung.mockproject.api;

import com.google.gson.annotations.SerializedName;

public class ApiSearchResult {
    @SerializedName("status")
    private Integer status;

    @SerializedName("response")
    private ResponseSearch response;

    public Integer getStatus() {
        return status;
    }

    public ResponseSearch getResponse() {
        return response;
    }
}
