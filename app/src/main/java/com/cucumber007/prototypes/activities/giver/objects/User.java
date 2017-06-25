package com.cucumber007.prototypes.activities.giver.objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Field;

public class User implements Parcelable {

    private int id;
    @SerializedName("full_name") private String fullName;
    @SerializedName("social_token") private String socialToken;
    @SerializedName("fb_rate") private float socialRate;
    @SerializedName("user_data") private UserData userData;
    @SerializedName("picture") private String pictureUrl;


    public int getId() {
        return id;
    }


    public String getFullName() {
        return fullName;
    }

    public String getSocialToken() {
        return socialToken;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.fullName);
        dest.writeString(this.socialToken);
    }

    /*public String getFirebaseTopic() {
        return GiverFirebaseInstanceIdService.FIREBASE_TOPIC_PREFIX_USER+getSocialToken();
    }*/

    public String getPictureUrl() {
        return pictureUrl;
    }

    protected User(Parcel in) {
        this.id = in.readInt();
        this.fullName = in.readString();
        this.socialToken = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public float getSocialRate() {
        return Math.round(socialRate*100)/(float)100;
    }

    public boolean isProfileFilled() {
        try {
            for(Field field : UserData.class.getDeclaredFields()) {
                Object value = field.get(userData);
                if (value == null && !field.isSynthetic()) return false;
                if (value instanceof String && ((String) value).length() == 0) return false;
                if (userData.getGender() == 2) return false;
            }
        } catch (IllegalAccessException | NullPointerException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
