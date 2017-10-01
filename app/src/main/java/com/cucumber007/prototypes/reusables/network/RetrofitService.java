package com.cucumber007.prototypes.reusables.network;


import com.cucumber007.prototypes.reusables.models.objects.User;

import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface RetrofitService {

    @POST("login")
    Observable<Response<User>> login(@Body Object loginParams);

    @GET("offers/{id}/")
    Observable<Object> getOffers(@Query("latitude") double latitude, @Path("id") int offerId);

    @Multipart
    @POST("offers/{id}/finish_checkin")
    Observable<Object> finishCheckin(
            @Part("checkin_id") RequestBody checkinId,
            @Part("file\"; filename=\"file.jpg\" ") RequestBody file
    );

}
