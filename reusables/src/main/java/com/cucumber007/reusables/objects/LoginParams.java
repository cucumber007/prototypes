package com.cucumber007.reusables.objects;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LoginParams {

    @SerializedName("access_token") private String accessToken;
    @SerializedName("social_type") private int socialType;
    private String email;
    private String password;
    private String name;
    @SerializedName("picture_url") private String pictureUrl;

    public enum SocialType {
        FACEBOOK, GOOGLE;
    }

    public LoginParams(String accessToken) {
        this.accessToken = accessToken;
        setSocialType(SocialType.FACEBOOK);
    }

    public LoginParams(String accessToken, SocialType socialType) {
        this.accessToken = accessToken;
        setSocialType(socialType);
    }

    public LoginParams(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginParams(String accessToken, String email, String name, String pictureUrl) {
        setSocialType(SocialType.FACEBOOK);
        this.accessToken = accessToken;
        this.email = email;
        this.name = name;
        this.pictureUrl = pictureUrl;
    }

    public LoginParams(String accessToken, SocialType socialType, String email, String name, String pictureUrl) {
        setSocialType(socialType);
        this.accessToken = accessToken;
        this.email = email;
        this.name = name;
        this.pictureUrl = pictureUrl;
    }

    public void setSocialType(SocialType socialType) {
        this.socialType = socialType.ordinal();
    }

    public Map<String, RequestBody> toMap() {
        Map<String, RequestBody> map = new HashMap<>();
        if (accessToken != null) {
            map.put("access_token", RequestBody.create(MediaType.parse("text/plain"), accessToken));
            if (name != null) map.put("name", RequestBody.create(MediaType.parse("text/plain"), name));
            if (email != null) map.put("email", RequestBody.create(MediaType.parse("text/plain"), email));
            if (pictureUrl != null) map.put("pictureUrl", RequestBody.create(MediaType.parse("text/plain"), pictureUrl));
        }
        else {
            map.put("email", RequestBody.create(MediaType.parse("text/plain"), email));
            map.put("password", RequestBody.create(MediaType.parse("text/plain"), password));
        }
        return map;
    }
}
