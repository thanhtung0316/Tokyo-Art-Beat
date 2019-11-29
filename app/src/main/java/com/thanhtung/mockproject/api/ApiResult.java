package com.thanhtung.mockproject.api;

import com.google.gson.annotations.SerializedName;

public class ApiResult {
    @SerializedName("status")
    private Integer status;

    @SerializedName("response")
    private Response response;

    @SerializedName("error_message")
    private String error_message;


    @SerializedName("error_code")
    private Integer error_code;


    public String getError_message() {
        return error_message;
    }

    public Integer getError_code() {
        return error_code;
    }

    public Integer getStatus() {
        return status;
    }

    public Response getResponse() {
        return response;
    }
}
