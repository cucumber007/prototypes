package com.cucumber007.prototypes.activities.giver.objects;


import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateFormat;

import com.cucumber007.prototypes.ContextApplication;

import org.threeten.bp.LocalTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.FormatStyle;

import java.util.Locale;

public class Recurrence implements Parcelable {
    private String start;
    private String end;

    public Recurrence(Parcel parcel) {
        start = parcel.readString();
        end = parcel.readString();
    }

    public ZonedDateTime getStart() {
        return ZonedDateTime.parse(start).withZoneSameInstant(ZoneId.systemDefault());
    }

    public ZonedDateTime getEnd() {
        return ZonedDateTime.parse(end).withZoneSameInstant(ZoneId.systemDefault());
    }

    public String getStartString() {
        if (DateFormat.is24HourFormat(ContextApplication.getContext()))
            return LocalTime.from(getStart()).format(DateTimeFormatter.ofPattern("HH:mm").withLocale(Locale.getDefault()));
        else return LocalTime.from(getStart()).format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.getDefault()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(start);
        parcel.writeString(end);
    }

    public static final Creator<Recurrence> CREATOR
            = new Creator<Recurrence>() {
        public Recurrence createFromParcel(Parcel in) {
            return new Recurrence(in);
        }

        public Recurrence[] newArray(int size) {
            return new Recurrence[size];
        }
    };
}
