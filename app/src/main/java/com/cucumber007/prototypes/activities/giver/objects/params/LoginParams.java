package com.cucumber007.prototypes.activities.giver.objects.params;

import com.google.gson.annotations.SerializedName;

public class LoginParams {

    @SerializedName("access_token")
    private String accesToken;
    @SerializedName("social_type")
    private int socialType;
    public static final int TYPE_FACEBOOK = 0;


    public LoginParams(String accesToken) {
        this.accesToken = accesToken;
        this.socialType = TYPE_FACEBOOK;
    }
}
