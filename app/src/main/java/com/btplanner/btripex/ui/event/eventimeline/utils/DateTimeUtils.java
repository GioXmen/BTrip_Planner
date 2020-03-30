package com.btplanner.btripex.ui.event.eventimeline.utils;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {

    public static String parseDateTime(String dateString, String originalFormat, String outputFromat) {

        SimpleDateFormat formatter = new SimpleDateFormat(originalFormat, Locale.UK);
        Date date;
        try {
            date = formatter.parse(dateString);
            SimpleDateFormat dateFormat = new SimpleDateFormat(outputFromat, new Locale("UK"));
            return dateFormat.format(date);
        }
        catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getRelativeTimeSpan(String dateString, String originalFormat) {

        SimpleDateFormat formatter = new SimpleDateFormat(originalFormat, Locale.UK);
        Date date;
        try {
            date = formatter.parse(dateString);
            return DateUtils.getRelativeTimeSpanString(date.getTime()).toString();
        }
        catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

}
