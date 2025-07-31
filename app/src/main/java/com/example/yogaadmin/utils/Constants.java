package com.example.yogaadmin.utils;

public class Constants {

    // Database Constants
    public static final String DATABASE_NAME = "yoga_admin.db";
    public static final int DATABASE_VERSION = 1;

    // Table Names
    public static final String TABLE_COURSES = "courses";
    public static final String TABLE_INSTANCES = "instances";
    public static final String TABLE_TEACHERS = "teachers";
    public static final String TABLE_STUDENTS = "students";
    public static final String TABLE_BOOKINGS = "bookings";

    // Course Table Columns
    public static final String COLUMN_COURSE_ID = "id";
    public static final String COLUMN_COURSE_NAME = "name";
    public static final String COLUMN_COURSE_DESCRIPTION = "description";
    public static final String COLUMN_COURSE_DURATION = "duration";
    public static final String COLUMN_COURSE_CAPACITY = "capacity";
    public static final String COLUMN_COURSE_PRICE = "price";
    public static final String COLUMN_COURSE_TYPE = "type";
    public static final String COLUMN_COURSE_LEVEL = "level";
    public static final String COLUMN_COURSE_INSTRUCTOR_ID = "instructor_id";
    public static final String COLUMN_COURSE_CREATED_AT = "created_at";
    public static final String COLUMN_COURSE_UPDATED_AT = "updated_at";

    // Instance Table Columns
    public static final String COLUMN_INSTANCE_ID = "id";
    public static final String COLUMN_INSTANCE_COURSE_ID = "course_id";
    public static final String COLUMN_INSTANCE_DATE = "date";
    public static final String COLUMN_INSTANCE_TIME = "time";
    public static final String COLUMN_INSTANCE_END_TIME = "end_time";
    public static final String COLUMN_INSTANCE_INSTRUCTOR_ID = "instructor_id";
    public static final String COLUMN_INSTANCE_CAPACITY = "capacity";
    public static final String COLUMN_INSTANCE_ENROLLED = "enrolled";
    public static final String COLUMN_INSTANCE_STATUS = "status";
    public static final String COLUMN_INSTANCE_NOTES = "notes";
    public static final String COLUMN_INSTANCE_CREATED_AT = "created_at";
    public static final String COLUMN_INSTANCE_UPDATED_AT = "updated_at";

    // Teacher Table Columns
    public static final String COLUMN_TEACHER_ID = "id";
    public static final String COLUMN_TEACHER_NAME = "name";
    public static final String COLUMN_TEACHER_EMAIL = "email";
    public static final String COLUMN_TEACHER_PHONE = "phone";
    public static final String COLUMN_TEACHER_SPECIALIZATION = "specialization";
    public static final String COLUMN_TEACHER_EXPERIENCE = "experience";
    public static final String COLUMN_TEACHER_CREATED_AT = "created_at";

    // Course Types
    public static final String COURSE_TYPE_FLOW = "Flow Yoga";
    public static final String COURSE_TYPE_AERIAL = "Aerial Yoga";
    public static final String COURSE_TYPE_FAMILY = "Family Yoga";



    // Difficulty Levels
    public static final String LEVEL_BEGINNER = "Beginner";
    public static final String LEVEL_INTERMEDIATE = "Intermediate";
    public static final String LEVEL_ADVANCED = "Advanced";
    public static final String LEVEL_ALL = "All Levels";

    // Instance Status
    public static final String STATUS_SCHEDULED = "Scheduled";
    public static final String STATUS_ONGOING = "Ongoing";
    public static final String STATUS_COMPLETED = "Completed";
    public static final String STATUS_CANCELLED = "Cancelled";

    // Date Time Formats
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DISPLAY_DATE_FORMAT = "MMM dd, yyyy";
    public static final String DISPLAY_TIME_FORMAT = "h:mm a";
    public static final String DISPLAY_DATETIME_FORMAT = "MMM dd, yyyy h:mm a";

    // SharedPreferences Keys
    public static final String PREF_NAME = "yoga_admin_prefs";
    public static final String PREF_FIRST_LAUNCH = "first_launch";
    public static final String PREF_NOTIFICATIONS_ENABLED = "notifications_enabled";
    public static final String PREF_AUTO_BACKUP = "auto_backup";
    public static final String PREF_DEFAULT_DURATION = "default_duration";
    public static final String PREF_DEFAULT_CAPACITY = "default_capacity";
    public static final String PREF_THEME_MODE = "theme_mode";

    // Intent Extra Keys
    public static final String EXTRA_COURSE_ID = "course_id";
    public static final String EXTRA_INSTANCE_ID = "instance_id";
    public static final String EXTRA_TEACHER_ID = "teacher_id";
    public static final String EXTRA_MODE = "mode";
    public static final String EXTRA_TITLE = "title";

    // Request Codes
    public static final int REQUEST_CODE_ADD_COURSE = 1001;
    public static final int REQUEST_CODE_EDIT_COURSE = 1002;
    public static final int REQUEST_CODE_ADD_INSTANCE = 1003;
    public static final int REQUEST_CODE_EDIT_INSTANCE = 1004;
    public static final int REQUEST_CODE_SELECT_DATE = 1005;
    public static final int REQUEST_CODE_SELECT_TIME = 1006;
    public static final int REQUEST_CODE_PERMISSION = 1007;

    // Default Values
    public static final int DEFAULT_COURSE_DURATION = 60; // minutes
    public static final int DEFAULT_COURSE_CAPACITY = 20;
    public static final double DEFAULT_COURSE_PRICE = 0.0;
    public static final int MIN_COURSE_DURATION = 30;
    public static final int MAX_COURSE_DURATION = 120;
    public static final int MIN_COURSE_CAPACITY = 1;
    public static final int MAX_COURSE_CAPACITY = 50;

    // Validation Constants
    public static final int MIN_NAME_LENGTH = 2;
    public static final int MAX_NAME_LENGTH = 100;
    public static final int MAX_DESCRIPTION_LENGTH = 500;
    public static final int MAX_NOTES_LENGTH = 300;

    // UI Constants
    public static final int ANIMATION_DURATION = 300;
    public static final int SPLASH_DELAY = 2000;
    public static final int SEARCH_DELAY = 500;
    public static final int ITEMS_PER_PAGE = 20;

    // Notification Constants
    public static final String NOTIFICATION_CHANNEL_ID = "yoga_admin_notifications";
    public static final String NOTIFICATION_CHANNEL_NAME = "Yoga Admin";
    public static final String NOTIFICATION_CHANNEL_DESCRIPTION = "Notifications for Yoga Admin app";
    public static final int NOTIFICATION_ID_REMINDER = 1001;
    public static final int NOTIFICATION_ID_BOOKING = 1002;

    // File Constants
    public static final String BACKUP_FILE_NAME = "yoga_admin_backup.json";
    public static final String EXPORT_FILE_NAME = "yoga_admin_export.csv";
    public static final String BACKUP_DIRECTORY = "YogaAdminBackups";

    // Error Messages
    public static final String ERROR_GENERIC = "Something went wrong. Please try again.";
    public static final String ERROR_NETWORK = "Network connection error. Please check your internet connection.";
    public static final String ERROR_DATABASE = "Database error occurred. Please restart the app.";
    public static final String ERROR_VALIDATION = "Please check your input and try again.";
    public static final String ERROR_NOT_FOUND = "Requested item not found.";
    public static final String ERROR_DUPLICATE = "Item already exists.";

    // Success Messages
    public static final String SUCCESS_SAVE = "Saved successfully";
    public static final String SUCCESS_UPDATE = "Updated successfully";
    public static final String SUCCESS_DELETE = "Deleted successfully";
    public static final String SUCCESS_BACKUP = "Backup created successfully";
    public static final String SUCCESS_RESTORE = "Data restored successfully";

    // Array Constants for Spinners
    public static final String[] COURSE_TYPES = {
            COURSE_TYPE_FLOW, COURSE_TYPE_AERIAL, COURSE_TYPE_FAMILY,
               };

    public static final String[] DIFFICULTY_LEVELS = {
            LEVEL_BEGINNER, LEVEL_INTERMEDIATE, LEVEL_ADVANCED, LEVEL_ALL
    };

    public static final String[] DAYS_OF_WEEK = {
            "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
    };

    public static final String[] INSTANCE_STATUSES = {
            STATUS_SCHEDULED, STATUS_ONGOING, STATUS_COMPLETED, STATUS_CANCELLED
    };

    // Time slots (24-hour format)
    public static final String[] TIME_SLOTS = {
            "06:00", "06:30", "07:00", "07:30", "08:00", "08:30", "09:00", "09:30",
            "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30",
            "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30",
            "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30"
    };

    // Regular Expressions for Validation
    public static final String EMAIL_PATTERN =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@[a-zA-Z0-9-]+\\.[a-zA-Z]{2,7}$";
    public static final String PHONE_PATTERN = "^[+]?[0-9]{10,15}$";
    public static final String PRICE_PATTERN = "^[0-9]+(\\.[0-9]{1,2})?$";

    // Private constructor to prevent instantiation
    private Constants() {
        throw new UnsupportedOperationException("Constants class cannot be instantiated");
    }
}