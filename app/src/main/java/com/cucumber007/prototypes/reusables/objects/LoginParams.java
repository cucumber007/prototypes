package com.cucumber007.prototypes.reusables.objects;

import com.google.gson.annotations.SerializedName;

public class LoginParams {

    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("social_type")
    private int socialType;
    private String email;
    private String password;

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

    public void setSocialType(SocialType socialType) {
        this.socialType = socialType.ordinal();
    }
}
