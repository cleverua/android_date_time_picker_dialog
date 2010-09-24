package com.cleverua.android;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.widget.DatePicker;
import android.widget.TimePicker;

public class DateHelper {

    private static final SimpleDateFormat FULL_DATE_FORMATTER = new SimpleDateFormat(
        "MM/dd/yyyy 'at' HH:mm"
    );

    private static final SimpleDateFormat SHORT_DATE_FORMATTER = new SimpleDateFormat(
        "MM/dd/yyyy"
    );

    public static String format(long date) {
        return FULL_DATE_FORMATTER.format(date);
    }

    public static String formatShortly(long date) {
        return SHORT_DATE_FORMATTER.format(date);
    }

    public static int getDayOfMonth(long date) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    public static int getMonthOfYear(long date) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date);
        return c.get(Calendar.MONTH);
    }

    public static int getYear(long date) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date);
        return c.get(Calendar.YEAR);
    }

    public static int getHourOfDay(long date) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinute(long date) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date);
        return c.get(Calendar.MINUTE);
    }

    public static long getDate(int year, int monthOfYear, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, monthOfYear, dayOfMonth);
        return c.getTimeInMillis();
    }

    /**
     * @param datePicker
     * @return date with the hours/minutes/seconds/milliseconds stripped off
     */
    public static long getDate(DatePicker datePicker) {
        return getDate(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
    }

    public static void initDatePicker(DatePicker datePicker, long date) {
        datePicker.init(getYear(date), getMonthOfYear(date), getDayOfMonth(date), null);
    }

    public static void initTimePicker(TimePicker timePicker, long date) {
        timePicker.setCurrentMinute(getMinute(date));
        timePicker.setCurrentHour(getHourOfDay(date));
    }

    /**
     * @param timePicker
     * @return time as milliseconds since 00:00:00:000 (midnight)
     */
    public static long getTime(TimePicker timePicker) {
        int h = timePicker.getCurrentHour().intValue();
        int m = timePicker.getCurrentMinute().intValue();
        return (h * 3600 + m * 60) * 1000;
    }

    public static long getStartOfTheDay(long date) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date);

        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return c.getTimeInMillis();
    }
}
