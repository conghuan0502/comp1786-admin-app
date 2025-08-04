package com.example.yogaadmin.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.yogaadmin.models.Teacher;
import java.util.ArrayList;
import java.util.List;

/**
 * TeacherDAO (Data Access Object) provides methods to interact with the teachers table.
 * This class handles all database operations related to yoga teachers including
 * CRUD operations and data retrieval.
 * 
 * The TeacherDAO supports:
 * - Inserting new teachers
 * - Retrieving all teachers with sorting
 * - Basic teacher management operations
 * 
 * @author YogaAdmin Team
 * @version 1.0
 */
public class TeacherDAO {
    /** Database helper instance for database operations */
    private DatabaseHelper dbHelper;

    /**
     * Constructs a new TeacherDAO with the given context.
     * 
     * @param context The application context
     */
    public TeacherDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    /**
     * Inserts a new teacher into the database.
     * 
     * @param teacher The Teacher object to insert
     * @return The row ID of the newly inserted teacher, or -1 if insertion failed
     */
    public long insertTeacher(Teacher teacher) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        
        // Map teacher properties to database columns
        values.put(DatabaseContract.TeacherEntry.COLUMN_NAME_NAME, teacher.getName());
        values.put(DatabaseContract.TeacherEntry.COLUMN_NAME_EMAIL, teacher.getEmail());
        values.put(DatabaseContract.TeacherEntry.COLUMN_NAME_PHONE, teacher.getPhone());
        
        long result = db.insert(DatabaseContract.TeacherEntry.TABLE_NAME, null, values);
        db.close();
        return result;
    }

    /**
     * Retrieves all teachers from the database.
     * Results are sorted alphabetically by teacher name in ascending order.
     * 
     * @return List of all teachers sorted by name
     */
    public List<Teacher> getAllTeachers() {
        List<Teacher> teacherList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        
        // Query all teachers with sorting by name
        Cursor cursor = db.query(DatabaseContract.TeacherEntry.TABLE_NAME, null, null, null, null, null, DatabaseContract.TeacherEntry.COLUMN_NAME_NAME + " ASC");

        // Convert cursor data to Teacher objects
        if (cursor.moveToFirst()) {
            do {
                Teacher teacher = new Teacher();
                teacher.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.TeacherEntry._ID)));
                teacher.setName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TeacherEntry.COLUMN_NAME_NAME)));
                teacher.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TeacherEntry.COLUMN_NAME_EMAIL)));
                teacher.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TeacherEntry.COLUMN_NAME_PHONE)));
                teacherList.add(teacher);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return teacherList;
    }
}
