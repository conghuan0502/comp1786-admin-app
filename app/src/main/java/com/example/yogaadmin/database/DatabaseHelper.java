package com.example.yogaadmin.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.yogaadmin.models.ClassInstance;

import android.database.Cursor;


import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "YogaAdmin.db";
    // DATABASE_VERSION needs to be incremented to trigger onUpgrade
    private static final int DATABASE_VERSION = 3;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Added the price column to the courses table
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

        String createInstancesTable = "CREATE TABLE " + DatabaseContract.InstanceEntry.TABLE_NAME + "("
                + DatabaseContract.InstanceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DatabaseContract.InstanceEntry.COLUMN_NAME_COURSE_ID + " INTEGER NOT NULL,"
                + DatabaseContract.InstanceEntry.COLUMN_NAME_TEACHER_ID + " INTEGER NOT NULL,"
                + DatabaseContract.InstanceEntry.COLUMN_NAME_DATE + " TEXT NOT NULL,"
                + "FOREIGN KEY(" + DatabaseContract.InstanceEntry.COLUMN_NAME_COURSE_ID + ") REFERENCES "
                + DatabaseContract.CourseEntry.TABLE_NAME + "(" + DatabaseContract.CourseEntry._ID + "),"
                + "FOREIGN KEY(" + DatabaseContract.InstanceEntry.COLUMN_NAME_TEACHER_ID + ") REFERENCES "
                + DatabaseContract.TeacherEntry.TABLE_NAME + "(" + DatabaseContract.TeacherEntry._ID + "))";

        String createTeachersTable = "CREATE TABLE " + DatabaseContract.TeacherEntry.TABLE_NAME + "("
                + DatabaseContract.TeacherEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DatabaseContract.TeacherEntry.COLUMN_NAME_NAME + " TEXT NOT NULL,"
                + DatabaseContract.TeacherEntry.COLUMN_NAME_EMAIL + " TEXT,"
                + DatabaseContract.TeacherEntry.COLUMN_NAME_PHONE + " TEXT)";

        db.execSQL(createTeachersTable);
        db.execSQL(createCoursesTable);
        db.execSQL(createInstancesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Check if the old version is 1, if so, add the price column.
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + DatabaseContract.CourseEntry.TABLE_NAME + " ADD COLUMN " + DatabaseContract.CourseEntry.COLUMN_NAME_PRICE + " REAL NOT NULL DEFAULT 0");
        }
        if (oldVersion < 3) {
            db.execSQL("ALTER TABLE " + DatabaseContract.CourseEntry.TABLE_NAME + " ADD COLUMN " + DatabaseContract.CourseEntry.COLUMN_NAME_DIFFICULTY + " TEXT");
            db.execSQL("ALTER TABLE " + DatabaseContract.CourseEntry.TABLE_NAME + " ADD COLUMN " + DatabaseContract.CourseEntry.COLUMN_NAME_TYPE + " TEXT");
        }
    }

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

    

    public List<ClassInstance> getInstancesForDate(String date) {
        List<ClassInstance> instanceList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
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