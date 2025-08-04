package com.example.yogaadmin.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.yogaadmin.models.ClassInstance;
import java.util.ArrayList;
import java.util.List;

/**
 * InstanceDAO (Data Access Object) provides methods to interact with the class_instances table.
 * This class handles all database operations related to individual class sessions including
 * CRUD operations and queries with joins to related tables.
 * 
 * The InstanceDAO supports:
 * - Inserting new class instances
 * - Retrieving instances for specific courses
 * - Updating instance details
 * - Deleting instances
 * 
 * @author YogaAdmin Team
 * @version 1.0
 */
public class InstanceDAO {
    /** Database helper instance for database operations */
    private DatabaseHelper dbHelper;

    /**
     * Constructs a new InstanceDAO with the given context.
     * 
     * @param context The application context
     */
    public InstanceDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    /**
     * Inserts a new class instance into the database.
     * 
     * @param instance The ClassInstance object to insert
     * @return The row ID of the newly inserted instance, or -1 if insertion failed
     */
    public long insertInstance(ClassInstance instance) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        
        // Map instance properties to database columns
        values.put(DatabaseContract.InstanceEntry.COLUMN_NAME_COURSE_ID, instance.getCourseId());
        values.put(DatabaseContract.InstanceEntry.COLUMN_NAME_TEACHER_ID, instance.getTeacherId());
        values.put(DatabaseContract.InstanceEntry.COLUMN_NAME_DATE, instance.getDate());
        
        long result = db.insert(DatabaseContract.InstanceEntry.TABLE_NAME, null, values);
        db.close();
        return result;
    }

    /**
     * Retrieves all class instances for a specific course.
     * This method performs a JOIN with the teachers table to include teacher information
     * and orders results by date in descending order (most recent first).
     * 
     * @param courseId The unique identifier of the course
     * @return List of ClassInstance objects for the specified course
     */
    public List<ClassInstance> getInstancesForCourse(int courseId) {
        List<ClassInstance> instanceList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Build query with JOIN to include teacher information
        String query = "SELECT i.*, t." + DatabaseContract.TeacherEntry.COLUMN_NAME_NAME + " as teacher_name" +
                " FROM " + DatabaseContract.InstanceEntry.TABLE_NAME + " i" +
                " JOIN " + DatabaseContract.TeacherEntry.TABLE_NAME + " t ON i." + DatabaseContract.InstanceEntry.COLUMN_NAME_TEACHER_ID + " = t." + DatabaseContract.TeacherEntry._ID +
                " WHERE i." + DatabaseContract.InstanceEntry.COLUMN_NAME_COURSE_ID + " = ?" +
                " ORDER BY i." + DatabaseContract.InstanceEntry.COLUMN_NAME_DATE + " DESC";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(courseId)});

        // Convert cursor data to ClassInstance objects
        if (cursor.moveToFirst()) {
            do {
                ClassInstance instance = new ClassInstance();
                instance.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.InstanceEntry._ID)));
                instance.setCourseId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.InstanceEntry.COLUMN_NAME_COURSE_ID)));
                instance.setTeacherId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.InstanceEntry.COLUMN_NAME_TEACHER_ID)));
                instance.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.InstanceEntry.COLUMN_NAME_DATE)));
                instance.setTeacherName(cursor.getString(cursor.getColumnIndexOrThrow("teacher_name")));
                instanceList.add(instance);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return instanceList;
    }

    /**
     * Deletes a specific class instance from the database.
     * 
     * @param id The unique identifier of the instance to delete
     */
    public void deleteInstance(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseContract.InstanceEntry.TABLE_NAME, DatabaseContract.InstanceEntry._ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    /**
     * Updates an existing class instance in the database.
     * This method allows updating the date and teacher assignment for an instance.
     * 
     * @param instance The ClassInstance object with updated values
     * @return The number of rows affected by the update operation
     */
    public int updateInstance(ClassInstance instance) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        
        // Update date and teacher assignment
        values.put(DatabaseContract.InstanceEntry.COLUMN_NAME_DATE, instance.getDate());
        values.put(DatabaseContract.InstanceEntry.COLUMN_NAME_TEACHER_ID, instance.getTeacherId());

        int result = db.update(DatabaseContract.InstanceEntry.TABLE_NAME, values,
                DatabaseContract.InstanceEntry._ID + " = ?",
                new String[]{String.valueOf(instance.getId())});
        db.close();
        return result;
    }
}
