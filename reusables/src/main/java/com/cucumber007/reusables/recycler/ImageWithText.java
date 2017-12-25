package com.cucumber007.reusables.recycler;

import android.os.Parcel;
import android.os.Parcelable;

public class ImageWithText implements Parcelable {
    private int imageResource;
    private String text;

    public ImageWithText(int imageResource, String text) {
        this.imageResource = imageResource;
        this.text = text;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getText() {
        return text;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.imageResource);
        dest.writeString(this.text);
    }

    protected ImageWithText(Parcel in) {
        this.imageResource = in.readInt();
        this.text = in.readString();
    }

}
