package com.example.yogaadmin.utils;

import android.content.Context;
import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateTimeUtils {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault());
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat(Constants.TIME_FORMAT, Locale.getDefault());
    private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat(Constants.DATETIME_FORMAT, Locale.getDefault());
    private static final SimpleDateFormat displayDateFormat = new SimpleDateFormat(Constants.DISPLAY_DATE_FORMAT, Locale.getDefault());
    private static final SimpleDateFormat displayTimeFormat = new SimpleDateFormat(Constants.DISPLAY_TIME_FORMAT, Locale.getDefault());
    private static final SimpleDateFormat displayDateTimeFormat = new SimpleDateFormat(Constants.DISPLAY_DATETIME_FORMAT, Locale.getDefault());

    /**
     * Get current date as string in database format (yyyy-MM-dd)
     */
    public static String getCurrentDate() {
        return dateFormat.format(new Date());
    }

    /**
     * Get current time as string in database format (HH:mm)
     */
    public static String getCurrentTime() {
        return timeFormat.format(new Date());
    }

    /**
     * Get current datetime as string in database format (yyyy-MM-dd HH:mm:ss)
     */
    public static String getCurrentDateTime() {
        return dateTimeFormat.format(new Date());
    }

    /**
     * Format date string for display (MMM dd, yyyy)
     */
    public static String formatDateForDisplay(String dateString) {
        try {
            Date date = dateFormat.parse(dateString);
            return displayDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateString;
        }
    }

    /**
     * Format time string for display (h:mm a)
     */
    public static String formatTimeForDisplay(String timeString) {
        try {
            Date time = timeFormat.parse(timeString);
            return displayTimeFormat.format(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return timeString;
        }
    }

    /**
     * Format datetime string for display (MMM dd, yyyy h:mm a)
     */
    public static String formatDateTimeForDisplay(String dateTimeString) {
        try {
            Date dateTime = dateTimeFormat.parse(dateTimeString);
            return displayDateTimeFormat.format(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateTimeString;
        }
    }

    /**
     * Convert Date object to database date format string
     */
    public static String formatDateForDatabase(Date date) {
        return dateFormat.format(date);
    }

    /**
     * Convert Date object to database time format string
     */
    public static String formatTimeForDatabase(Date date) {
        return timeFormat.format(date);
    }

    /**
     * Convert Date object to database datetime format string
     */
    public static String formatDateTimeForDatabase(Date date) {
        return dateTimeFormat.format(date);
    }

    /**
     * Parse date string from database format to Date object
     */
    public static Date parseDateFromDatabase(String dateString) {
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Parse time string from database format to Date object
     */
    public static Date parseTimeFromDatabase(String timeString) {
        try {
            return timeFormat.parse(timeString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Parse datetime string from database format to Date object
     */
    public static Date parseDateTimeFromDatabase(String dateTimeString) {
        try {
            return dateTimeFormat.parse(dateTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Check if a date string is today
     */
    public static boolean isToday(String dateString) {
        String today = getCurrentDate();
        return today.equals(dateString);
    }

    /**
     * Check if a date string is tomorrow
     */
    public static boolean isTomorrow(String dateString) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        String tomorrow = dateFormat.format(calendar.getTime());
        return tomorrow.equals(dateString);
    }

    /**
     * Check if a date string is in the past
     */
    public static boolean isPastDate(String dateString) {
        try {
            Date inputDate = dateFormat.parse(dateString);
            Date today = dateFormat.parse(getCurrentDate());
            return inputDate.before(today);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Check if a datetime is in the past
     */
    public static boolean isPastDateTime(String dateString, String timeString) {
        try {
            String dateTimeString = dateString + " " + timeString + ":00";
            Date inputDateTime = dateTimeFormat.parse(dateTimeString);
            Date now = new Date();
            return inputDateTime.before(now);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Check if a date string is within this week
     */
    public static boolean isThisWeek(String dateString) {
        try {
            Date inputDate = dateFormat.parse(dateString);
            Calendar calendar = Calendar.getInstance();

            // Get start of this week (Sunday)
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date weekStart = calendar.getTime();

            // Get end of this week (Saturday)
            calendar.add(Calendar.DAY_OF_WEEK, 6);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            Date weekEnd = calendar.getTime();

            return inputDate.compareTo(weekStart) >= 0 && inputDate.compareTo(weekEnd) <= 0;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get relative time description (e.g., "Today", "Tomorrow", "2 days ago")
     */
    public static String getRelativeTimeDescription(String dateString) {
        try {
            Date inputDate = dateFormat.parse(dateString);
            Date today = dateFormat.parse(getCurrentDate());

            long diffInMillis = inputDate.getTime() - today.getTime();
            long diffInDays = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);

            if (diffInDays == 0) {
                return "Today";
            } else if (diffInDays == 1) {
                return "Tomorrow";
            } else if (diffInDays == -1) {
                return "Yesterday";
            } else if (diffInDays > 1 && diffInDays <= 7) {
                return "In " + diffInDays + " days";
            } else if (diffInDays < -1 && diffInDays >= -7) {
                return Math.abs(diffInDays) + " days ago";
            } else {
                return formatDateForDisplay(dateString);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return formatDateForDisplay(dateString);
        }
    }

    /**
     * Get day of week from date string
     */
    public static String getDayOfWeek(String dateString) {
        try {
            Date date = dateFormat.parse(dateString);
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
            return dayFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Add days to a date string
     */
    public static String addDaysToDate(String dateString, int days) {
        try {
            Date date = dateFormat.parse(dateString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, days);
            return dateFormat.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return dateString;
        }
    }

    /**
     * Add minutes to a time string
     */
    public static String addMinutesToTime(String timeString, int minutes) {
        try {
            Date time = timeFormat.parse(timeString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(time);
            calendar.add(Calendar.MINUTE, minutes);
            return timeFormat.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return timeString;
        }
    }

    /**
     * Calculate duration between two times in minutes
     */
    public static int calculateDurationInMinutes(String startTime, String endTime) {
        try {
            Date start = timeFormat.parse(startTime);
            Date end = timeFormat.parse(endTime);
            long diffInMillis = end.getTime() - start.getTime();
            return (int) TimeUnit.MINUTES.convert(diffInMillis, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Get end time based on start time and duration
     */
    public static String getEndTime(String startTime, int durationMinutes) {
        return addMinutesToTime(startTime, durationMinutes);
    }

    /**
     * Check if time1 is before time2
     */
    public static boolean isTimeBefore(String time1, String time2) {
        try {
            Date t1 = timeFormat.parse(time1);
            Date t2 = timeFormat.parse(time2);
            return t1.before(t2);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get current time in 12-hour format with AM/PM
     */
    public static String getCurrentTimeForDisplay() {
        return displayTimeFormat.format(new Date());
    }

    /**
     * Get current date for display
     */
    public static String getCurrentDateForDisplay() {
        return displayDateFormat.format(new Date());
    }

    /**
     * Convert 24-hour format to 12-hour format
     */
    public static String convertTo12HourFormat(String time24) {
        try {
            Date time = timeFormat.parse(time24);
            return displayTimeFormat.format(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return time24;
        }
    }

    /**
     * Convert 12-hour format to 24-hour format
     */
    public static String convertTo24HourFormat(String time12) {
        try {
            Date time = displayTimeFormat.parse(time12);
            return timeFormat.format(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return time12;
        }
    }

    /**
     * Check if the system uses 24-hour format
     */
    public static boolean is24HourFormat(Context context) {
        return DateFormat.is24HourFormat(context);
    }

    /**
     * Get time format based on system preference
     */
    public static String getTimeForDisplay(Context context, String timeString) {
        if (is24HourFormat(context)) {
            return timeString;
        } else {
            return convertTo12HourFormat(timeString);
        }
    }

    /**
     * Get formatted date and time for display
     */
    public static String getFormattedDateTime(String dateString, String timeString) {
        String displayDate = formatDateForDisplay(dateString);
        String displayTime = formatTimeForDisplay(timeString);
        return displayDate + " " + displayTime;
    }

    public static int getDayOfWeekFromString(String dayName, Locale locale) {
        String[] weekdays = new java.text.DateFormatSymbols(locale).getWeekdays();
        for (int i = 0; i < weekdays.length; i++) {
            if (weekdays[i].equalsIgnoreCase(dayName)) {
                return i;
            }
        }
        // Fallback for short day names
        String[] shortWeekdays = new java.text.DateFormatSymbols(locale).getShortWeekdays();
        for (int i = 0; i < shortWeekdays.length; i++) {
            if (shortWeekdays[i].equalsIgnoreCase(dayName)) {
                return i;
            }
        }
        return -1;
    }
}