package com.example.yogaadmin.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.yogaadmin.models.ClassInstance;

import android.database.Cursor;


import java.util.ArrayList;
import java.util.List;

/**
 * DatabaseHelper manages the SQLite database for the YogaAdmin application.
 * This class handles database creation, upgrades, and provides utility methods
 * for database operations. It extends SQLiteOpenHelper to manage database
 * lifecycle events.
 * 
 * The database contains three tables:
 * - teachers: Stores teacher information
 * - courses: Stores course details with foreign key to teachers
 * - class_instances: Stores individual class sessions with foreign keys to courses and teachers
 * 
 * @author YogaAdmin Team
 * @version 1.0
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    /** Database name */
    private static final String DATABASE_NAME = "YogaAdmin.db";
    
    /** Database version - increment to trigger onUpgrade */
    private static final int DATABASE_VERSION = 3;

    /**
     * Constructs a new DatabaseHelper with the given context.
     * 
     * @param context The application context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creates the database tables when the database is first created.
     * Tables are created in order to respect foreign key constraints:
     * 1. teachers (referenced by courses)
     * 2. courses (referenced by class_instances)
     * 3. class_instances (references both courses and teachers)
     * 
     * @param db The database to create tables in
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create courses table with all required columns
        String createCoursesTable = "CREATE TABLE " + DatabaseContract.CourseEntry.TABLE_NAME + "("
                + DatabaseContract.CourseEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DatabaseContract.CourseEntry.COLUMN_NAME_NAME + " TEXT NOT NULL,"
                + DatabaseContract.CourseEntry.COLUMN_NAME_DESCRIPTION + " TEXT,"
                + DatabaseContract.CourseEntry.COLUMN_NAME_TEACHER_ID + " INTEGER NOT NULL,"
                + DatabaseContract.CourseEntry.COLUMN_NAME_DAY_OF_WEEK + " TEXT NOT NULL,"
                + DatabaseContract.CourseEntry.COLUMN_NAME_TIME + " TEXT NOT NULL,"
                + DatabaseContract.CourseEntry.COLUMN_NAME_DURATION + " INTEGER NOT NULL,"
                + DatabaseContract.CourseEntry.COLUMN_NAME_MAX_CAPACITY + " INTEGER NOT NULL,"
                + DatabaseContract.CourseEntry.COLUMN_NAME_PRICE + " REAL NOT NULL,"
                + DatabaseContract.CourseEntry.COLUMN_NAME_DIFFICULTY + " TEXT,"
                + DatabaseContract.CourseEntry.COLUMN_NAME_TYPE + " TEXT,"
                + "FOREIGN KEY(" + DatabaseContract.CourseEntry.COLUMN_NAME_TEACHER_ID + ") REFERENCES "
                + DatabaseContract.TeacherEntry.TABLE_NAME + "(" + DatabaseContract.TeacherEntry._ID + "))";

        // Create class_instances table with foreign key constraints
        String createInstancesTable = "CREATE TABLE " + DatabaseContract.InstanceEntry.TABLE_NAME + "("
                + DatabaseContract.InstanceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DatabaseContract.InstanceEntry.COLUMN_NAME_COURSE_ID + " INTEGER NOT NULL,"
                + DatabaseContract.InstanceEntry.COLUMN_NAME_TEACHER_ID + " INTEGER NOT NULL,"
                + DatabaseContract.InstanceEntry.COLUMN_NAME_DATE + " TEXT NOT NULL,"
                + "FOREIGN KEY(" + DatabaseContract.InstanceEntry.COLUMN_NAME_COURSE_ID + ") REFERENCES "
                + DatabaseContract.CourseEntry.TABLE_NAME + "(" + DatabaseContract.CourseEntry._ID + "),"
                + "FOREIGN KEY(" + DatabaseContract.InstanceEntry.COLUMN_NAME_TEACHER_ID + ") REFERENCES "
                + DatabaseContract.TeacherEntry.TABLE_NAME + "(" + DatabaseContract.TeacherEntry._ID + "))";

        // Create teachers table
        String createTeachersTable = "CREATE TABLE " + DatabaseContract.TeacherEntry.TABLE_NAME + "("
                + DatabaseContract.TeacherEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DatabaseContract.TeacherEntry.COLUMN_NAME_NAME + " TEXT NOT NULL,"
                + DatabaseContract.TeacherEntry.COLUMN_NAME_EMAIL + " TEXT,"
                + DatabaseContract.TeacherEntry.COLUMN_NAME_PHONE + " TEXT)";

        // Execute table creation in order to respect foreign key constraints
        db.execSQL(createTeachersTable);
        db.execSQL(createCoursesTable);
        db.execSQL(createInstancesTable);
    }

    /**
     * Handles database upgrades when the database version is incremented.
     * This method adds new columns to existing tables without losing data.
     * 
     * Version history:
     * - Version 1: Initial database with basic columns
     * - Version 2: Added price column to courses table
     * - Version 3: Added difficulty and type columns to courses table
     * 
     * @param db The database to upgrade
     * @param oldVersion The previous database version
     * @param newVersion The new database version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Add price column to courses table for version 2
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + DatabaseContract.CourseEntry.TABLE_NAME + " ADD COLUMN " + DatabaseContract.CourseEntry.COLUMN_NAME_PRICE + " REAL NOT NULL DEFAULT 0");
        }
        
        // Add difficulty and type columns to courses table for version 3
        if (oldVersion < 3) {
            db.execSQL("ALTER TABLE " + DatabaseContract.CourseEntry.TABLE_NAME + " ADD COLUMN " + DatabaseContract.CourseEntry.COLUMN_NAME_DIFFICULTY + " TEXT");
            db.execSQL("ALTER TABLE " + DatabaseContract.CourseEntry.TABLE_NAME + " ADD COLUMN " + DatabaseContract.CourseEntry.COLUMN_NAME_TYPE + " TEXT");
        }
    }

    /**
     * Resets the entire database by dropping all tables and recreating them.
     * This method is useful for testing or when a complete database reset is needed.
     * Tables are dropped in reverse order to avoid foreign key constraint violations.
     */
    public void resetDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        
        // Drop tables in reverse order of creation to avoid foreign key constraints
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.InstanceEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.CourseEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TeacherEntry.TABLE_NAME);
        
        // Recreate the tables
        onCreate(db);
        db.close();
    }

    /**
     * Retrieves all class instances for a specific date.
     * This method is useful for calendar views and scheduling displays.
     * 
     * @param date The date to search for instances (format: YYYY-MM-DD)
     * @return List of ClassInstance objects for the specified date
     */
    public List<ClassInstance> getInstancesForDate(String date) {
        List<ClassInstance> instanceList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        
        // Query for instances on the specified date
        String selection = DatabaseContract.InstanceEntry.COLUMN_NAME_DATE + " = ?";
        String[] selectionArgs = { date };

        Cursor cursor = db.query(
                DatabaseContract.InstanceEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        // Convert cursor data to ClassInstance objects
        if (cursor.moveToFirst()) {
            do {
                ClassInstance instance = new ClassInstance();
                instance.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.InstanceEntry._ID)));
                instance.setCourseId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.InstanceEntry.COLUMN_NAME_COURSE_ID)));
                instance.setTeacherId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.InstanceEntry.COLUMN_NAME_TEACHER_ID)));
                instance.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.InstanceEntry.COLUMN_NAME_DATE)));
                instanceList.add(instance);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        db.close();
        return instanceList;
    }
}