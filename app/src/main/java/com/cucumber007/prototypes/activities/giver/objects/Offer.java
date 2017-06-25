package com.cucumber007.prototypes.activities.giver.objects;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.cucumber007.prototypes.activities.giver.BaseLocationModel;
import com.cucumber007.prototypes.activities.giver.LocationModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Offer implements Parcelable, Comparable<Offer> {

    private static BaseLocationModel locationModel = LocationModel.getInstance();
    public static final int CAMERA_FRONT = 0;
    public static final int CAMERA_BACK = 1;

    private int id;
    private String title;
    @SerializedName("full_info") private String fullInfo;
    @SerializedName("preview_url") private String imageUrl;
    @SerializedName("mask_url") private String maskUrl;
    @SerializedName("gift_type_image_android_url") private String typeImageUrl;
    @SerializedName("gift_icon_color") private String giftIconColor;
    @SerializedName("gift_type") private int giftType;
    @SerializedName("active_recurrence") private Recurrence recurrence;
    @SerializedName("offer_radius") private double radius;
    @SerializedName("partner_name") private String partnerName;
    private double latitude;
    private double longitude;
    @SerializedName("comment_template") private String commentTemplate;
    @SerializedName("facebook_object_id") private String facebookObjectIdRaw;
    private String facebookObjectId;
    private List<String> hashtags;
    @SerializedName("need_checkin_validation") private boolean needCheckinValidation;
    @SerializedName("has_location") private boolean hasLocation;
    private String address;
    @SerializedName("phone_number") private String phoneNumber;
    @SerializedName("site_url") private String siteUrl;
    @SerializedName("default_camera") private int defaultCamera;


    @Override
    public int compareTo(@NonNull Offer another) {
        int anotherDistance = another.measureUserDistance();
        if (locationModel == null || anotherDistance == -1) return 0;
        return -(anotherDistance - measureUserDistance());
    }

    public int measureUserDistance() {
        if (locationModel == null) return -1;
        if (locationModel.getLocationIfPossible() == null) return -1;
        return (int)getLocation().distanceTo(locationModel.getLocationIfPossible());
    }

    public int getId() {
        return id;
    }


    public String getImageUrl() {
        return imageUrl;
    }


    public String getFullInfo() {
        return fullInfo;
    }

    public int getDefaultCamera() {
        return defaultCamera;
    }

    public String getMaskUrl() {
        return maskUrl;
    }

    public int getGiftType() {
        return giftType;
    }

    public Recurrence getRecurrence() {
        return recurrence;
    }

    public Location getLocation() {
        return BaseLocationModel.locationFromLatLng(latitude, longitude);
    }

    public String getTitle() {
        return title;
    }

    public double getRadius() {
        return radius;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getTypeImageUrl() {
        return typeImageUrl;
    }

    public String getCommentTemplate() {
        return commentTemplate;
    }

    public Set<String> getHashtags() {
        return new HashSet<>(hashtags);
    }

    public String getGiftIconColor() {
        return giftIconColor;
    }

    public String getHashtagsString() {
        String res = "";
        for (String hashtag : hashtags) {
            res += "#" + hashtag + " ";
        }
        return res;
    }

    public LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }

    public static String wrapFacebookId( String facebookObjectIdRaw) {
        if (facebookObjectIdRaw == null || facebookObjectIdRaw.length() == 0) return null;
        if(facebookObjectIdRaw.contains("http")) return facebookObjectIdRaw;
        else return "https://www.facebook.com/"+facebookObjectIdRaw;
    }

    public String getFacebookObjectId() {
        if(facebookObjectId == null) facebookObjectId = wrapFacebookId(facebookObjectIdRaw);
        return facebookObjectId;
    }

    public boolean isNeedCheckinValidation() {
        return needCheckinValidation;
    }

    public boolean isHasLocation() {
        return hasLocation;
    }

    public Offer() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.fullInfo);
        dest.writeString(this.imageUrl);
        dest.writeString(this.maskUrl);
        dest.writeString(this.typeImageUrl);
        dest.writeString(this.giftIconColor);
        dest.writeInt(this.giftType);
        dest.writeParcelable(this.recurrence, flags);
        dest.writeDouble(this.radius);
        dest.writeString(this.partnerName);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeString(this.commentTemplate);
        dest.writeString(this.facebookObjectIdRaw);
        dest.writeString(this.facebookObjectId);
        dest.writeStringList(this.hashtags);
        dest.writeByte(this.needCheckinValidation ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hasLocation ? (byte) 1 : (byte) 0);
        dest.writeString(this.address);
        dest.writeString(this.phoneNumber);
        dest.writeString(this.siteUrl);
        dest.writeInt(this.defaultCamera);
    }

    protected Offer(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.fullInfo = in.readString();
        this.imageUrl = in.readString();
        this.maskUrl = in.readString();
        this.typeImageUrl = in.readString();
        this.giftIconColor = in.readString();
        this.giftType = in.readInt();
        this.recurrence = in.readParcelable(Recurrence.class.getClassLoader());
        this.radius = in.readDouble();
        this.partnerName = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.commentTemplate = in.readString();
        this.facebookObjectIdRaw = in.readString();
        this.facebookObjectId = in.readString();
        this.hashtags = in.createStringArrayList();
        this.needCheckinValidation = in.readByte() != 0;
        this.hasLocation = in.readByte() != 0;
        this.address = in.readString();
        this.phoneNumber = in.readString();
        this.siteUrl = in.readString();
        this.defaultCamera = in.readInt();
    }

    public static final Creator<Offer> CREATOR = new Creator<Offer>() {
        @Override
        public Offer createFromParcel(Parcel source) {
            return new Offer(source);
        }

        @Override
        public Offer[] newArray(int size) {
            return new Offer[size];
        }
    };
}
