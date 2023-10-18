package com.theroyalsoft.mydoc.apputil;

import com.skh.hkhr.util.NullRemoveUtil;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import timber.log.Timber;

/**
 * Created by Pritom Dutta on 15/10/23.
 */
public interface TimeUtil {


    //2022-10-12T12:11:17.000Z
    String DATE_TIME_FORmMATE_0 = "yyyy-MM-dd HH:mm:ss";
    String DATE_TIME_FORmMATE_1 = "yyyy-MM-dd'T'HH:mm:ssZ";

    String DATE_TIME_FORmMATE_6 = "yyyy-MM-dd'T'hh:mm:ssZ.ffff";
    String DATE_TIME_FORmMATE_2 = "dd MMM yy";
    String DATE_TIME_FORmMATE_3 = "yyyy/MM/dd HH:mm:ss";
    // String DATE_TIME_FORmMATE_4 = "dd MMM YYYY";
    String DATE_TIME_FORmMATE_4 = "yyyy/MM/dd";
    String DATE_TIME_FORmMATE_5 = "dd/MM/yyyy";
    String TIME_FORMAT = "hh:mm a";
    String DATE_TIME_FORMAT_ddMMMyyyy = "dd MMM yyyy";
    String REMINDER_DATE_TIME_FORMAT = "dd MMM yy hh:mm a";
    String REMINDER_DATE_TIME_FORMAT1 = "dd MMM yy hh:mma";

    static String getTime1(String time, String formatterType, String formatterType1) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatterType);
        SimpleDateFormat formatter = new SimpleDateFormat(formatterType1);

        Date date = new Date();
        try {
            date = dateFormat.parse(time);
            String time1 = formatter.format(date);
            // Timber.d("Date is: " + time1);
            return NullRemoveUtil.getNotNull(time1);

        } catch (ParseException e) {
            Timber.e("Error:" + e.toString());
        }
        return time;
    }





    static String getTimeForDob(String time, String fromType, String toType) {

        SimpleDateFormat dateFormat = new SimpleDateFormat(fromType);
        Format formatter = new SimpleDateFormat(toType);

        Date date = new Date();
        try {
            date = dateFormat.parse(time);
            String time1 = formatter.format(date);
            Timber.d("Date is: " + time1);
            return NullRemoveUtil.getNotNull(time1);

        } catch (ParseException e) {
            Timber.e("Error:" + e.toString());
        }
        return "";
    }


/*    static String getTime1(long timeLong) {
        String time = "";
        Format formatter = new SimpleDateFormat(DATE_TIME_FORmMATE_0);

        Date date = new Date();
        date.setTime(timeLong);
        String time1 = formatter.format(date);
        Timber.d("Date is: " + time1);
        return NullRemoveUtil.getNotNull(time1);

    }*/

    static String getToday() {

        Format formatter = new SimpleDateFormat(DATE_TIME_FORmMATE_4);

        Date date = new Date();
        String time1 = formatter.format(date);
        Timber.d("Date is: " + time1);
        return NullRemoveUtil.getNotNull(time1);
    }




    static String getTime1() {
        String time = "";
        Format formatter = new SimpleDateFormat(DATE_TIME_FORmMATE_0);

        Date date = new Date();
        String time1 = formatter.format(date);
        Timber.d("Date is: " + time1);
        return NullRemoveUtil.getNotNull(time1);

    }

    static String getTimeCurrent() {
        String time = "";
        Format formatter = new SimpleDateFormat(TIME_FORMAT);

        Date date = new Date();
        String time1 = formatter.format(date);
        Timber.d("Date is: " + time1);
        return NullRemoveUtil.getNotNull(time1);

    }


    static Date getTime(String dateTime, String formatterType) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatterType);

        Date date = new Date();
        try {
            date = dateFormat.parse(dateTime);
            return date;

        } catch (ParseException e) {
            Timber.e("Error:" + e.toString());
        }
        return date;

    }

    static Date getTimeForReminder(String dateTime, String formatterType) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatterType);

        Date date = new Date();
        try {
            date = dateFormat.parse(dateTime);
            return date;

        } catch (ParseException e) {
            Timber.e("Error:" + e.toString());
        }
        return date;

    }

    static Date getTime3(String dateTime, String formatterType) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatterType);

        Date date = new Date();
        try {
            date = dateFormat.parse(dateTime);
            return date;

        } catch (ParseException e) {
            Timber.e("Error:" + e.toString());
        }
        return date;

    }

    static String getTime4(String formated) {
        DateFormat dform = new SimpleDateFormat(formated);
        Date obj = new Date();
        return dform.format(obj);

    }


    //=============================================
    static String getSecond(long millis) {
        int seconds = (int) (millis / 1000);
        int time = seconds % 60;
        return time > 9 ? time + "" : "0" + time;
    }

    static String getMinute(long millis) {
        int seconds = (int) (millis / 1000);
        int time = seconds / 60;
        return time > 9 ? time + "" : "0" + time;
    }

    static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }


    static boolean isSameDay(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        boolean sameYear = calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR);
        boolean sameMonth = calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH);
        boolean sameDay = calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
        return (sameDay && sameMonth && sameYear);
    }

}
