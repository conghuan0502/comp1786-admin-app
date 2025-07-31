package com.example.yogaadmin.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.yogaadmin.models.ClassInstance;
import java.util.ArrayList;
import java.util.List;

public class InstanceDAO {
    private DatabaseHelper dbHelper;

    public InstanceDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long insertInstance(ClassInstance instance) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.InstanceEntry.COLUMN_NAME_COURSE_ID, instance.getCourseId());
        values.put(DatabaseContract.InstanceEntry.COLUMN_NAME_TEACHER_ID, instance.getTeacherId());
        values.put(DatabaseContract.InstanceEntry.COLUMN_NAME_DATE, instance.getDate());
        long result = db.insert(DatabaseContract.InstanceEntry.TABLE_NAME, null, values);
        db.close();
        return result;
    }

    public List<ClassInstance> getInstancesForCourse(int courseId) {
        List<ClassInstance> instanceList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT i.*, t." + DatabaseContract.TeacherEntry.COLUMN_NAME_NAME + " as teacher_name" +
                " FROM " + DatabaseContract.InstanceEntry.TABLE_NAME + " i" +
                " JOIN " + DatabaseContract.TeacherEntry.TABLE_NAME + " t ON i." + DatabaseContract.InstanceEntry.COLUMN_NAME_TEACHER_ID + " = t." + DatabaseContract.TeacherEntry._ID +
                " WHERE i." + DatabaseContract.InstanceEntry.COLUMN_NAME_COURSE_ID + " = ?" +
                " ORDER BY i." + DatabaseContract.InstanceEntry.COLUMN_NAME_DATE + " DESC";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(courseId)});

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

    public void deleteInstance(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseContract.InstanceEntry.TABLE_NAME, DatabaseContract.InstanceEntry._ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public int updateInstance(ClassInstance instance) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.InstanceEntry.COLUMN_NAME_DATE, instance.getDate());
        values.put(DatabaseContract.InstanceEntry.COLUMN_NAME_TEACHER_ID, instance.getTeacherId());

        int result = db.update(DatabaseContract.InstanceEntry.TABLE_NAME, values,
                DatabaseContract.InstanceEntry._ID + " = ?",
                new String[]{String.valueOf(instance.getId())});
        db.close();
        return result;
    }
}
