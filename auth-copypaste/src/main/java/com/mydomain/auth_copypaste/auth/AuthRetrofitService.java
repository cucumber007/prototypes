package com.mydomain.auth_copypaste.auth;


import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface AuthRetrofitService {

    @POST("login/google-oauth2/")
    Observable<Object> login(@Body Object loginParams);



}
