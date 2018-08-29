package cev.blackFish;

import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;

import cev.blackFish.*;

/**
 *
 */
public class DateUtils {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat();

    /**
     * Creates a Date, at 00:00:00 on the given day.
     *
     * @param month 0-11 (0 = January)
     * @param date
     * @param year
     * @return calendar instance with the date and time
     */
    public static Date newDate(int month, int date, int year) {
        Calendar inst = Calendar.getInstance();
        inst.clear();
        inst.set(year, month, date);
        return inst.getTime();
    }

    public static Calendar createCalendarMonth(int month, int day, int year) {
        Calendar inst = Calendar.getInstance();
        inst.clear();
        inst.set(year, month, day);
        return inst;
    }

    /**
     * Formats a Date
     *
     * @param format: A String
     * @return: formatted date as a String
     */
    public static String getDate(String format) {
        Date date = Calendar.getInstance().getTime();
        String formattedDate = (new SimpleDateFormat(format)).format(date);
        return formattedDate;
    }

    public static String getDateAsString(Date date, String format) {
        String formattedDate = (new SimpleDateFormat(format)).format(date);
        return formattedDate;
    }

    /**
     * @param dateString
     * @param formatString
     * @return
     * @throws ServletException
     */
    public static Date formatDate(Date date, String formatString) {

        SimpleDateFormat formatter = new SimpleDateFormat(formatString);
        formatter.format(date);
        return date;
    }

    public static java.util.Date convertFromTimeStamp(java.sql.Timestamp timestamp) {
        long milliseconds = timestamp.getTime() + (timestamp.getNanos() / 1000000);
        return new java.util.Date(milliseconds);
    }
}
