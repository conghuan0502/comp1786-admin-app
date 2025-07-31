package com.example.yogaadmin.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.yogaadmin.models.Teacher;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO {
    private DatabaseHelper dbHelper;

    public TeacherDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long insertTeacher(Teacher teacher) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.TeacherEntry.COLUMN_NAME_NAME, teacher.getName());
        values.put(DatabaseContract.TeacherEntry.COLUMN_NAME_EMAIL, teacher.getEmail());
        values.put(DatabaseContract.TeacherEntry.COLUMN_NAME_PHONE, teacher.getPhone());
        long result = db.insert(DatabaseContract.TeacherEntry.TABLE_NAME, null, values);
        db.close();
        return result;
    }

    public List<Teacher> getAllTeachers() {
        List<Teacher> teacherList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseContract.TeacherEntry.TABLE_NAME, null, null, null, null, null, DatabaseContract.TeacherEntry.COLUMN_NAME_NAME + " ASC");

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
