package com.polyana.cucumber007.copypaste.network;


import com.cucumber007.reusables.models.objects.User;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface RetrofitService {

    @POST("login")
    Observable<Response<User>> login(@Body Object loginParams);

    @POST("login")
    Observable<Response<User>> login(@QueryMap Map loginParams);

    @GET("offers/{id}/")
    Observable<Object> getOffers(@Query("latitude") double latitude, @Path("id") int offerId);

    @Multipart
    @POST("offers/{id}/finish_checkin")
    Observable<Object> finishCheckin(
            @Path("id") int offerId,
            @Part("checkin_id") RequestBody checkinId,
            @Part("file\"; filename=\"file.jpg\" ") RequestBody file
    );

    @Multipart
    @POST("ads_write/")
    Observable<Object> uploadAd(
            @PartMap() Map<String, RequestBody> images,
            @PartMap() Map<String, RequestBody> params
    );

    @FormUrlEncoded
    @POST("user/edit")
    Call<User> updateUser(@Field("first_name") String first, @Field("last_name") String last);

    @Headers("Cache-Control: max-age=640000")
    @GET("widget/list")
    Call<List<Object>> widgetList();

    @Headers({
                     "Accept: application/vnd.github.v3.full+json",
                     "User-Agent: Retrofit-Sample-App"
             })
    @GET("users/{username}")
    Call<User> getUser(@Path("username") String username);

    @GET("user")
    Call<User> getUsers(@Header("Authorization") String authorization);

}
