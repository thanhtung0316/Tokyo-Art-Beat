package com.thanhtung.mockproject.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class ApiBuilder {
    private static Api api;

    public static Api getInstance() {
        if (api==null){
            Retrofit retrofit =new Retrofit.Builder()
                    .baseUrl("http://4aff10d0.ngrok.io/18175d1_mobile_100_fresher/public/api/v0/")
                    .addConverterFactory(GsonConverterFactory.create()).build();
            api = retrofit.create(Api.class);
        }
        return api;
    }
}
