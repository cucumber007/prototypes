package com.cucumber007.prototypes.activities.giver.objects;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;

import com.cucumber007.prototypes.reusables.ContextApplication;
import com.google.gson.annotations.SerializedName;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.FormatStyle;

import java.text.Format;
import java.text.SimpleDateFormat;

public class Checkin implements Parcelable, Comparable<Checkin>  {

    private int id;
    private int status;
    @SerializedName("secret_code")
    private String secretCode;
    @SerializedName("start_date")
    private String startDate;
    @SerializedName("finish_date")
    private String finishDate;
    @SerializedName("review_date")
    private String reviewDate;
    @SerializedName("post_image")
    private String postImageUrl;
    private Offer offer;

    public static final int WAIT_APPROVING = 0; //
    public static final int APPROVED = 1; //
    public static final int DISCARDED = 2; //
    public static final int IN_PROGRESS = 3;
    public static final int EXPIRED = 4;

    private ZonedDateTime zonedDateTime;
    private String dateString;
    private Context context;

    public String getCode() {
        return secretCode;
    }

    public String getPostImageUrl() {
        return postImageUrl;
    }

    public int getStatus() {
        return status;
    }

    public ZonedDateTime getDate() {
        if (zonedDateTime == null) {
            if(getStatus() == APPROVED || getStatus() == DISCARDED) zonedDateTime = ZonedDateTime.parse(reviewDate);
            else zonedDateTime = ZonedDateTime.parse(startDate);
        }
        return zonedDateTime;
    }


    public String getDateString() {
        if (dateString == null) {
            LocalDateTime dateTime = getDate().withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
            String time;
            String date;

            if (DateFormat.is24HourFormat(ContextApplication.getContext()))
                time = dateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
            else
                time = dateTime.toLocalTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
            Format dateFormat = DateFormat.getDateFormat(ContextApplication.getContext());
            String pattern = ((SimpleDateFormat) dateFormat).toLocalizedPattern();
            //pattern = pattern.replace(".", "/");

            date = dateTime.toLocalDate().format(DateTimeFormatter.ofPattern(pattern));

            dateString = date + " " + time;
        }
        return dateString;
    }

    public int getId() {
        return id;
    }

    public Offer getOffer() {
        return offer;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.status);
        dest.writeString(this.secretCode);
        dest.writeString(this.startDate);
        dest.writeString(this.finishDate);
        dest.writeString(this.reviewDate);
        dest.writeString(this.postImageUrl);
        dest.writeParcelable(this.offer, flags);
    }

    protected Checkin(Parcel in) {
        this.id = in.readInt();
        this.status = in.readInt();
        this.secretCode = in.readString();
        this.startDate = in.readString();
        this.finishDate = in.readString();
        this.reviewDate = in.readString();
        this.postImageUrl = in.readString();
        this.offer = in.readParcelable(Offer.class.getClassLoader());
    }

    public static final Creator<Checkin> CREATOR = new Creator<Checkin>() {
        @Override
        public Checkin createFromParcel(Parcel source) {
            return new Checkin(source);
        }

        @Override
        public Checkin[] newArray(int size) {
            return new Checkin[size];
        }
    };

    @Override
    public int compareTo(@NonNull Checkin another) {
        return -(int)(getDate().toEpochSecond() - another.getDate().toEpochSecond());
    }

    /*@DrawableRes
    public int getStatusIconResource() {
        switch (status) {
            case Checkin.APPROVED:
                return R.drawable.checkin_approved_icon;
            case Checkin.WAIT_APPROVING:
                return R.drawable.checkin_wait_approving_icon;
            case Checkin.DISCARDED:
            default:
                return R.drawable.checkin_discarded_icon;
        }
    }


    @DrawableRes
    public int getStatusBackgroundResource() {
        switch (status) {
            case Checkin.APPROVED:
                return R.drawable.checkin_approved_background;
            case Checkin.WAIT_APPROVING:
                return R.drawable.checkin_wait_approving_background;
            case Checkin.DISCARDED:
            default:
                return R.drawable.checkin_discarded_background;
        }
    }

    @DrawableRes
    public int getStatusBackgroundImageResource() {
        switch (status) {
            case Checkin.APPROVED:
                return R.drawable.checkin_approved_large;
            case Checkin.WAIT_APPROVING:
                return R.drawable.checkin_approved_large;
            case Checkin.DISCARDED:
                return R.drawable.checkin_discarded_large;
            default:
                return R.drawable.checkin_discarded_large;
        }
    }

    public String getCommentResource() {
        switch (status) {
            case Checkin.APPROVED:
                return ContextApplication.getContext().getResources().getString(R.string.approved);
            case Checkin.WAIT_APPROVING:
                return ContextApplication.getContext().getResources().getString(R.string.wait_approving);
            case Checkin.DISCARDED:
            default:
                return ContextApplication.getContext().getResources().getString(R.string.discarded);
        }
    }*/


}
