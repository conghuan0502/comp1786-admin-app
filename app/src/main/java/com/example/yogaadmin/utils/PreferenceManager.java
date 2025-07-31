package com.example.yogaadmin.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class PreferenceManager {

    private static final String PREF_NAME = "yoga_admin_prefs";
    private static PreferenceManager instance;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    // Preference Keys
    private static final String KEY_FIRST_LAUNCH = "first_launch";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_NOTIFICATIONS_ENABLED = "notifications_enabled";
    private static final String KEY_REMINDER_TIME = "reminder_time_minutes";
    private static final String KEY_DARK_MODE = "dark_mode";
    private static final String KEY_LANGUAGE = "language";
    private static final String KEY_AUTO_BACKUP = "auto_backup";
    private static final String KEY_LAST_BACKUP_DATE = "last_backup_date";
    private static final String KEY_DEFAULT_CLASS_DURATION = "default_class_duration";
    private static final String KEY_BOOKING_LIMIT = "booking_limit";
    private static final String KEY_SHOW_TUTORIAL = "show_tutorial";
    private static final String KEY_VIBRATION_ENABLED = "vibration_enabled";
    private static final String KEY_SOUND_ENABLED = "sound_enabled";
    private static final String KEY_PREFERRED_TIME_FORMAT = "time_format_24h";
    private static final String KEY_PREFERRED_DATE_FORMAT = "date_format";
    private static final String KEY_DEFAULT_COURSE_TYPE = "default_course_type";
    private static final String KEY_INSTRUCTOR_MODE = "instructor_mode";
    private static final String KEY_LAST_SYNC_TIME = "last_sync_time";
    private static final String KEY_FAVORITE_COURSES = "favorite_courses";

    // Default values
    private static final boolean DEFAULT_NOTIFICATIONS_ENABLED = true;
    private static final int DEFAULT_REMINDER_TIME = 30; // 30 minutes before class
    private static final boolean DEFAULT_DARK_MODE = false;
    private static final String DEFAULT_LANGUAGE = "en";
    private static final boolean DEFAULT_AUTO_BACKUP = true;
    private static final int DEFAULT_CLASS_DURATION = 60; // 60 minutes
    private static final int DEFAULT_BOOKING_LIMIT = 20;
    private static final boolean DEFAULT_SHOW_TUTORIAL = true;
    private static final boolean DEFAULT_VIBRATION_ENABLED = true;
    private static final boolean DEFAULT_SOUND_ENABLED = true;
    private static final boolean DEFAULT_TIME_FORMAT_24H = true;
    private static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";
    private static final boolean DEFAULT_INSTRUCTOR_MODE = false;

    private PreferenceManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    /**
     * Gets singleton instance of PreferenceManager
     */
    public static synchronized PreferenceManager getInstance(Context context) {
        if (instance == null) {
            instance = new PreferenceManager(context.getApplicationContext());
        }
        return instance;
    }

    // First Launch and Tutorial
    public boolean isFirstLaunch() {
        return preferences.getBoolean(KEY_FIRST_LAUNCH, true);
    }

    public void setFirstLaunch(boolean isFirstLaunch) {
        editor.putBoolean(KEY_FIRST_LAUNCH, isFirstLaunch).apply();
    }

    public boolean shouldShowTutorial() {
        return preferences.getBoolean(KEY_SHOW_TUTORIAL, DEFAULT_SHOW_TUTORIAL);
    }

    public void setShowTutorial(boolean showTutorial) {
        editor.putBoolean(KEY_SHOW_TUTORIAL, showTutorial).apply();
    }

    // User Information
    public String getUserName() {
        return preferences.getString(KEY_USER_NAME, "");
    }

    public void setUserName(String userName) {
        editor.putString(KEY_USER_NAME, userName).apply();
    }

    public String getUserEmail() {
        return preferences.getString(KEY_USER_EMAIL, "");
    }

    public void setUserEmail(String userEmail) {
        editor.putString(KEY_USER_EMAIL, userEmail).apply();
    }

    // Notification Settings
    public boolean areNotificationsEnabled() {
        return preferences.getBoolean(KEY_NOTIFICATIONS_ENABLED, DEFAULT_NOTIFICATIONS_ENABLED);
    }

    public void setNotificationsEnabled(boolean enabled) {
        editor.putBoolean(KEY_NOTIFICATIONS_ENABLED, enabled).apply();
    }

    public int getReminderTime() {
        return preferences.getInt(KEY_REMINDER_TIME, DEFAULT_REMINDER_TIME);
    }

    public void setReminderTime(int minutes) {
        editor.putInt(KEY_REMINDER_TIME, minutes).apply();
    }

    public boolean isVibrationEnabled() {
        return preferences.getBoolean(KEY_VIBRATION_ENABLED, DEFAULT_VIBRATION_ENABLED);
    }

    public void setVibrationEnabled(boolean enabled) {
        editor.putBoolean(KEY_VIBRATION_ENABLED, enabled).apply();
    }

    public boolean isSoundEnabled() {
        return preferences.getBoolean(KEY_SOUND_ENABLED, DEFAULT_SOUND_ENABLED);
    }

    public void setSoundEnabled(boolean enabled) {
        editor.putBoolean(KEY_SOUND_ENABLED, enabled).apply();
    }

    // App Appearance
    public boolean isDarkModeEnabled() {
        return preferences.getBoolean(KEY_DARK_MODE, DEFAULT_DARK_MODE);
    }

    public void setDarkModeEnabled(boolean enabled) {
        editor.putBoolean(KEY_DARK_MODE, enabled).apply();
    }

    public String getLanguage() {
        return preferences.getString(KEY_LANGUAGE, DEFAULT_LANGUAGE);
    }

    public void setLanguage(String language) {
        editor.putString(KEY_LANGUAGE, language).apply();
    }

    // Time and Date Formats
    public boolean is24HourFormat() {
        return preferences.getBoolean(KEY_PREFERRED_TIME_FORMAT, DEFAULT_TIME_FORMAT_24H);
    }

    public void set24HourFormat(boolean is24Hour) {
        editor.putBoolean(KEY_PREFERRED_TIME_FORMAT, is24Hour).apply();
    }

    public String getDateFormat() {
        return preferences.getString(KEY_PREFERRED_DATE_FORMAT, DEFAULT_DATE_FORMAT);
    }

    public void setDateFormat(String format) {
        editor.putString(KEY_PREFERRED_DATE_FORMAT, format).apply();
    }

    // Backup Settings
    public boolean isAutoBackupEnabled() {
        return preferences.getBoolean(KEY_AUTO_BACKUP, DEFAULT_AUTO_BACKUP);
    }

    public void setAutoBackupEnabled(boolean enabled) {
        editor.putBoolean(KEY_AUTO_BACKUP, enabled).apply();
    }

    public long getLastBackupDate() {
        return preferences.getLong(KEY_LAST_BACKUP_DATE, 0);
    }

    public void setLastBackupDate(long timestamp) {
        editor.putLong(KEY_LAST_BACKUP_DATE, timestamp).apply();
    }

    public long getLastSyncTime() {
        return preferences.getLong(KEY_LAST_SYNC_TIME, 0);
    }

    public void setLastSyncTime(long timestamp) {
        editor.putLong(KEY_LAST_SYNC_TIME, timestamp).apply();
    }

    // Class and Booking Settings
    public int getDefaultClassDuration() {
        return preferences.getInt(KEY_DEFAULT_CLASS_DURATION, DEFAULT_CLASS_DURATION);
    }

    public void setDefaultClassDuration(int minutes) {
        editor.putInt(KEY_DEFAULT_CLASS_DURATION, minutes).apply();
    }

    public int getBookingLimit() {
        return preferences.getInt(KEY_BOOKING_LIMIT, DEFAULT_BOOKING_LIMIT);
    }

    public void setBookingLimit(int limit) {
        editor.putInt(KEY_BOOKING_LIMIT, limit).apply();
    }

    public String getDefaultCourseType() {
        return preferences.getString(KEY_DEFAULT_COURSE_TYPE, "");
    }

    public void setDefaultCourseType(String courseType) {
        editor.putString(KEY_DEFAULT_COURSE_TYPE, courseType).apply();
    }

    // Instructor Mode
    public boolean isInstructorMode() {
        return preferences.getBoolean(KEY_INSTRUCTOR_MODE, DEFAULT_INSTRUCTOR_MODE);
    }

    public void setInstructorMode(boolean enabled) {
        editor.putBoolean(KEY_INSTRUCTOR_MODE, enabled).apply();
    }

    // Favorite Courses
    public Set<String> getFavoriteCourses() {
        return preferences.getStringSet(KEY_FAVORITE_COURSES, new HashSet<String>());
    }

    public void setFavoriteCourses(Set<String> favoriteCourses) {
        editor.putStringSet(KEY_FAVORITE_COURSES, favoriteCourses).apply();
    }

    public void addFavoriteCourse(String courseId) {
        Set<String> favorites = new HashSet<>(getFavoriteCourses());
        favorites.add(courseId);
        setFavoriteCourses(favorites);
    }

    public void removeFavoriteCourse(String courseId) {
        Set<String> favorites = new HashSet<>(getFavoriteCourses());
        favorites.remove(courseId);
        setFavoriteCourses(favorites);
    }

    public boolean isFavoriteCourse(String courseId) {
        return getFavoriteCourses().contains(courseId);
    }

    // General utility methods
    public void clearAllPreferences() {
        editor.clear().apply();
    }

    public void removePreference(String key) {
        editor.remove(key).apply();
    }

    /**
     * Saves a custom string preference
     */
    public void saveString(String key, String value) {
        editor.putString(key, value).apply();
    }

    /**
     * Gets a custom string preference
     */
    public String getString(String key, String defaultValue) {
        return preferences.getString(key, defaultValue);
    }

    /**
     * Saves a custom boolean preference
     */
    public void saveBoolean(String key, boolean value) {
        editor.putBoolean(key, value).apply();
    }

    /**
     * Gets a custom boolean preference
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

    /**
     * Saves a custom int preference
     */
    public void saveInt(String key, int value) {
        editor.putInt(key, value).apply();
    }

    /**
     * Gets a custom int preference
     */
    public int getInt(String key, int defaultValue) {
        return preferences.getInt(key, defaultValue);
    }

    /**
     * Saves a custom long preference
     */
    public void saveLong(String key, long value) {
        editor.putLong(key, value).apply();
    }

    /**
     * Gets a custom long preference
     */
    public long getLong(String key, long defaultValue) {
        return preferences.getLong(key, defaultValue);
    }
}