package com.cucumber007.prototypes.reusables.objects;

import android.content.Context;
import android.text.format.DateFormat;

import com.cucumber007.prototypes.reusables.ContextApplication;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.FormatStyle;

import java.text.Format;
import java.text.SimpleDateFormat;

public class BaseObject  {

    private ZonedDateTime zonedDateTime;
    private String dateString;
    private Context context;


    public ZonedDateTime convertDateString(String dateString) {
        if (zonedDateTime == null) {
            zonedDateTime = ZonedDateTime.parse(dateString);
        }
        return zonedDateTime;
    }


    public String formatDate(ZonedDateTime zonedDateTime) {
        if (dateString == null) {
            LocalDateTime dateTime = zonedDateTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
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



}
