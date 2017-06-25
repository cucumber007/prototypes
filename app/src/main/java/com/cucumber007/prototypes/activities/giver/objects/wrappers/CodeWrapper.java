package com.cucumber007.prototypes.activities.giver.objects.wrappers;

import com.google.gson.annotations.SerializedName;

public class CodeWrapper {

    @SerializedName("secret_code")
    private int secretCode;

    public int getSecretCode() {
        return secretCode;
    }
}
