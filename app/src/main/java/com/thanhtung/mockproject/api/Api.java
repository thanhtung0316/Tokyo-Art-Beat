package com.thanhtung.mockproject.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @GET("listNews")
    Call<ApiResult> getListNews(
            @Query("pageIndex") int pageIndex,
            @Query("pageSize") int pageSize
    );

    @GET("listPopularEvents")
    Call<ApiResult> getPopullar(@Query("pageIndex") int pageIndex,
                                @Query("pageSize") int pageSize);

    @POST("register")
    Call<ApiResult> register(@Query("name") String name,
                             @Query("email") String email,
                             @Query("password") String password);

    @POST("login")
    @FormUrlEncoded
    Call<ApiResult> login(@Field("email") String email,
                          @Field("password") String password);

    @POST("resetPassword")
    Call<ApiResult> reset(@Query("email") String email);


    @GET("listMyEvents")
    Call<ApiResult> getGoingEvent(@Header("Authorization") String token,
                                  @Query("status") long status);

    @GET("getDetailEvent")
    Call<ApiResultEventDetail> getDetailEvent(@Header("Authorization") String token,
                                              @Query("event_id") long id);


    @POST("doFollowVenue")
    Call<ApiResultEventDetail> doFollow(@Header("Authorization") String token,
                                        @Query("venue_id") long id);


    @GET("listNearlyEvents")
    Call<ApiResultNearlyEvent> listNearlyEvents(
            @Header("Authorization") String token,
            @Query("radius") int radius,
            @Query("longitue") double longitue,
            @Query("latitude") double latitude);


    @GET("listCategories ")
    Call<ApiResult> getCategory();

    @GET("search")
    Call<ApiSearchResult> search(
            @Header("Authorization") String token,
            @Query("keyword") String keyword,
            @Query("pageIndex") int pageIndex,
            @Query("pageSize") int pageSize
    );

    @GET("listEventsByCategory")
    Call<ApiResultInCategory> listEventsByCategory(
            @Header("Authorization") String token,
            @Query("category_id") Integer categoryId,
            @Query("pageIndex") int pageIndex,
            @Query("pageSize") int pageSize
    );

    @POST("doUpdateEvent")
    Call<ApiResult> doUpdateEvent(@Header("Authorization") String token,
                                  @Query("status") long status,
                                  @Query("event_id") long event_id
    );


}
