package com.cucumber007.reusables.models.objects;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User implements Parcelable {

    @SerializedName("first_name") private String firstName;
    @SerializedName("last_name") private String lastName;
    private String email;
    private List<String> phones;
    @SerializedName("image") private String imageUrl;
    private float rating;
    private boolean verified;
    @SerializedName("created_date") private String createdDateString;

    public static User getDefault() {
        return new User(true);
    }

    public User(boolean def) {
        firstName = "Sobaka Sutulaya";
        imageUrl = "http://www.django-rest-framework.org/img/logo.png";
        rating = 2.0f;
    }

    public String getName() {
        return firstName+" "+lastName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public float getRating() {
        return rating;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.email);
        dest.writeStringList(this.phones);
        dest.writeString(this.imageUrl);
        dest.writeFloat(this.rating);
        dest.writeByte(this.verified ? (byte) 1 : (byte) 0);
        dest.writeString(this.createdDateString);
    }

    protected User(Parcel in) {
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.email = in.readString();
        this.phones = in.createStringArrayList();
        this.imageUrl = in.readString();
        this.rating = in.readFloat();
        this.verified = in.readByte() != 0;
        this.createdDateString = in.readString();
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
}
