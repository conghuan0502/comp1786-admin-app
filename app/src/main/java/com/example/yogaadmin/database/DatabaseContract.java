package com.example.yogaadmin.database;

import android.provider.BaseColumns;

/**
 * DatabaseContract defines the database schema for the YogaAdmin application.
 * This class contains the table names and column definitions for all database tables.
 * It implements BaseColumns to provide standard Android database columns (_id).
 * 
 * The database consists of three main tables:
 * - courses: Stores yoga course information
 * - class_instances: Stores individual class sessions
 * - teachers: Stores teacher information
 * 
 * @author YogaAdmin Team
 * @version 1.0
 */
public final class DatabaseContract {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private DatabaseContract() {}

    /**
     * Defines the schema for the courses table.
     * This table stores information about yoga courses including their schedule,
     * teacher assignments, pricing, and class details.
     */
    public static class CourseEntry implements BaseColumns {
        /** Table name for courses */
        public static final String TABLE_NAME = "courses";
        
        /** Course name (required) */
        public static final String COLUMN_NAME_NAME = "name";
        
        /** Course description (optional) */
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        
        /** Foreign key reference to teachers table (required) */
        public static final String COLUMN_NAME_TEACHER_ID = "teacher_id";
        
        /** Day of the week when the course is held (required) */
        public static final String COLUMN_NAME_DAY_OF_WEEK = "day_of_week";
        
        /** Time when the course starts (required) */
        public static final String COLUMN_NAME_TIME = "time";
        
        /** Duration of the course in minutes (required) */
        public static final String COLUMN_NAME_DURATION = "duration";
        
        /** Maximum number of students allowed (required) */
        public static final String COLUMN_NAME_MAX_CAPACITY = "max_capacity";
        
        /** Course price (required) */
        public static final String COLUMN_NAME_PRICE = "price";
        
        /** Difficulty level of the course (optional) */
        public static final String COLUMN_NAME_DIFFICULTY = "difficulty";
        
        /** Type/category of the yoga course (optional) */
        public static final String COLUMN_NAME_TYPE = "type";
    }

    /**
     * Defines the schema for the class_instances table.
     * This table stores individual class sessions that are instances of courses.
     * Each instance represents a specific class on a specific date.
     */
    public static class InstanceEntry implements BaseColumns {
        /** Table name for class instances */
        public static final String TABLE_NAME = "class_instances";
        
        /** Foreign key reference to courses table (required) */
        public static final String COLUMN_NAME_COURSE_ID = "course_id";
        
        /** Foreign key reference to teachers table (required) */
        public static final String COLUMN_NAME_TEACHER_ID = "teacher_id";
        
        /** Date of the class instance (required) */
        public static final String COLUMN_NAME_DATE = "date";
    }

    /**
     * Defines the schema for the teachers table.
     * This table stores information about yoga teachers including their
     * contact details and personal information.
     */
    public static class TeacherEntry implements BaseColumns {
        /** Table name for teachers */
        public static final String TABLE_NAME = "teachers";
        
        /** Teacher's full name (required) */
        public static final String COLUMN_NAME_NAME = "name";
        
        /** Teacher's email address (optional) */
        public static final String COLUMN_NAME_EMAIL = "email";
        
        /** Teacher's phone number (optional) */
        public static final String COLUMN_NAME_PHONE = "phone";
    }
}
