package com.example.yogaadmin.utils;

/**
 * Constants class contains all the static constants used throughout the YogaAdmin application.
 * This class centralizes all application-wide constants including course types, difficulty levels,
 * date formats, intent keys, and predefined arrays for UI components.
 * 
 * The constants are organized into logical groups:
 * - Course Types: Different categories of yoga courses
 * - Difficulty Levels: Student skill levels for course classification
 * - Date/Time Formats: Standardized date formatting
 * - Intent Keys: Keys for passing data between activities
 * - UI Arrays: Predefined arrays for spinners and dropdowns
 * 
 * @author YogaAdmin Team
 * @version 1.0
 */
public class Constants {

    // ==================== COURSE TYPES ====================
    /** Flow Yoga course type */
    public static final String COURSE_TYPE_FLOW = "Flow Yoga";
    
    /** Aerial Yoga course type */
    public static final String COURSE_TYPE_AERIAL = "Aerial Yoga";
    
    /** Family Yoga course type */
    public static final String COURSE_TYPE_FAMILY = "Family Yoga";

    // ==================== DIFFICULTY LEVELS ====================
    /** Beginner level for yoga courses */
    public static final String LEVEL_BEGINNER = "Beginner";
    
    /** Intermediate level for yoga courses */
    public static final String LEVEL_INTERMEDIATE = "Intermediate";
    
    /** Advanced level for yoga courses */
    public static final String LEVEL_ADVANCED = "Advanced";
    
    /** All levels course (suitable for everyone) */
    public static final String LEVEL_ALL = "All Levels";

    // ==================== DATE/TIME FORMATS ====================
    /** Standard date format used throughout the application (ISO format) */
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    // ==================== INTENT EXTRA KEYS ====================
    /** Intent extra key for passing course ID between activities */
    public static final String EXTRA_COURSE_ID = "course_id";

    // ==================== UI ARRAYS FOR SPINNERS ====================
    /**
     * Array of available course types for spinner/dropdown components.
     * Used in course creation and filtering interfaces.
     */
    public static final String[] COURSE_TYPES = {
            COURSE_TYPE_FLOW, COURSE_TYPE_AERIAL, COURSE_TYPE_FAMILY,
    };

    /**
     * Array of difficulty levels for spinner/dropdown components.
     * Used in course creation and filtering interfaces.
     */
    public static final String[] DIFFICULTY_LEVELS = {
            LEVEL_BEGINNER, LEVEL_INTERMEDIATE, LEVEL_ADVANCED, LEVEL_ALL
    };

    /**
     * Array of days of the week for spinner/dropdown components.
     * Used in course scheduling and filtering interfaces.
     */
    public static final String[] DAYS_OF_WEEK = {
            "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
    };

    /**
     * Array of time slots in 24-hour format for spinner/dropdown components.
     * Covers a full day from 6:00 AM to 9:30 PM in 30-minute intervals.
     * Used in course scheduling interfaces.
     */
    public static final String[] TIME_SLOTS = {
            "06:00", "06:30", "07:00", "07:30", "08:00", "08:30", "09:00", "09:30",
            "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30",
            "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30",
            "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30"
    };
}