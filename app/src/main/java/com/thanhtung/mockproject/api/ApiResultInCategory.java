package com.thanhtung.mockproject.api;

import com.google.gson.annotations.SerializedName;

public class ApiResultInCategory {
    @SerializedName("status")
    private Integer status;

    @SerializedName("response")
    private ResponeIncategory response;

    public Integer getStatus() {
        return status;
    }

    public ResponeIncategory getResponse() {
        return response;
    }
}
