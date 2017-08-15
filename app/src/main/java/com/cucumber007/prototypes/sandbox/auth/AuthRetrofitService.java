package com.cucumber007.prototypes.sandbox.auth;


import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface AuthRetrofitService {

    @POST("login/google-oauth2/")
    Observable<Response<Object>> login(@Body Object loginParams);



}
