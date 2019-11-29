package com.thanhtung.mockproject.api;

import com.google.gson.annotations.SerializedName;

public class ApiResultEventDetail {
    @SerializedName("status")
    private Integer status;

    @SerializedName("response")
    private ResponeEventDetail response;

    @SerializedName("error_message")
    private String errorMessage;

    @SerializedName("error_code")
    private Integer errorCode;


    public String getErrorMessage() {
        return errorMessage;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public Integer getStatus() {
        return status;
    }

    public ResponeEventDetail getResponse() {
        return response;
    }
}
