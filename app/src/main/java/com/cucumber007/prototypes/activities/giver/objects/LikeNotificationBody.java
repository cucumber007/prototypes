package com.cucumber007.prototypes.activities.giver.objects;

import com.google.gson.annotations.SerializedName;

public class LikeNotificationBody {

    @SerializedName("facebook_object_id")
    private String facebook_object_id;
    private Offer offer;
    private String message;

    public String getFacebookObjectId() {
        return facebook_object_id;
    }

    public Offer getOffer() {
        return offer;
    }

    public String getMessage() {
        return message;
    }
}
