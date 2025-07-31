package com.example.yogaadmin.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.example.yogaadmin.models.YogaCourse;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    private DatabaseHelper dbHelper;

    public CourseDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long insertCourse(YogaCourse course) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.CourseEntry.COLUMN_NAME_NAME, course.getName());
        values.put(DatabaseContract.CourseEntry.COLUMN_NAME_DESCRIPTION, course.getDescription());
        values.put(DatabaseContract.CourseEntry.COLUMN_NAME_TEACHER_ID, course.getTeacherId());
        values.put(DatabaseContract.CourseEntry.COLUMN_NAME_DAY_OF_WEEK, course.getDayOfWeek());
        values.put(DatabaseContract.CourseEntry.COLUMN_NAME_TIME, course.getTime());
        values.put(DatabaseContract.CourseEntry.COLUMN_NAME_DURATION, course.getDuration());
        values.put(DatabaseContract.CourseEntry.COLUMN_NAME_MAX_CAPACITY, course.getMaxCapacity());
        values.put(DatabaseContract.CourseEntry.COLUMN_NAME_PRICE, course.getPrice());
        values.put(DatabaseContract.CourseEntry.COLUMN_NAME_DIFFICULTY, course.getDifficulty());
        values.put(DatabaseContract.CourseEntry.COLUMN_NAME_TYPE, course.getType());
        long result = db.insert(DatabaseContract.CourseEntry.TABLE_NAME, null, values);
        db.close();
        return result;
    }

    private YogaCourse cursorToCourse(Cursor cursor) {
        YogaCourse course = new YogaCourse();
        course.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.CourseEntry._ID)));
        course.setName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.CourseEntry.COLUMN_NAME_NAME)));
        course.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.CourseEntry.COLUMN_NAME_DESCRIPTION)));
        course.setTeacherId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.CourseEntry.COLUMN_NAME_TEACHER_ID)));
        course.setTeacherName(cursor.getString(cursor.getColumnIndexOrThrow("teacher_name")));
        course.setDayOfWeek(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.CourseEntry.COLUMN_NAME_DAY_OF_WEEK)));
        course.setTime(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.CourseEntry.COLUMN_NAME_TIME)));
        course.setDuration(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.CourseEntry.COLUMN_NAME_DURATION)));
        course.setMaxCapacity(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.CourseEntry.COLUMN_NAME_MAX_CAPACITY)));
        course.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseContract.CourseEntry.COLUMN_NAME_PRICE)));
        course.setDifficulty(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.CourseEntry.COLUMN_NAME_DIFFICULTY)));
        course.setType(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.CourseEntry.COLUMN_NAME_TYPE)));
        return course;
    }

    public List<YogaCourse> getAllCourses() {
        return searchCourses(null, null, null);
    }

    public List<YogaCourse> searchCourses(String teacherName, String dayOfWeek, String date) {
        List<YogaCourse> courseList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String baseQuery = "SELECT c.*, t." + DatabaseContract.TeacherEntry.COLUMN_NAME_NAME + " as teacher_name" +
                           " FROM " + DatabaseContract.CourseEntry.TABLE_NAME + " c" +
                           " JOIN " + DatabaseContract.TeacherEntry.TABLE_NAME + " t ON c." + DatabaseContract.CourseEntry.COLUMN_NAME_TEACHER_ID + " = t." + DatabaseContract.TeacherEntry._ID +
                           " LEFT JOIN " + DatabaseContract.InstanceEntry.TABLE_NAME + " i ON c." + DatabaseContract.CourseEntry._ID + " = i." + DatabaseContract.InstanceEntry.COLUMN_NAME_COURSE_ID;

        StringBuilder whereClause = new StringBuilder();
        List<String> selectionArgs = new ArrayList<>();

        if (!TextUtils.isEmpty(teacherName)) {
            whereClause.append("t.").append(DatabaseContract.TeacherEntry.COLUMN_NAME_NAME).append(" LIKE ?");
            selectionArgs.add("%" + teacherName + "%");
        }

        if (!TextUtils.isEmpty(dayOfWeek)) {
            if (whereClause.length() > 0) {
                whereClause.append(" AND ");
            }
            whereClause.append("c.").append(DatabaseContract.CourseEntry.COLUMN_NAME_DAY_OF_WEEK).append(" = ?");
            selectionArgs.add(dayOfWeek);
        }

        if (!TextUtils.isEmpty(date)) {
            if (whereClause.length() > 0) {
                whereClause.append(" AND ");
            }
            whereClause.append("i.").append(DatabaseContract.InstanceEntry.COLUMN_NAME_DATE).append(" = ?");
            selectionArgs.add(date);
        }

        String finalQuery = baseQuery;
        if (whereClause.length() > 0) {
            finalQuery += " WHERE " + whereClause.toString();
        }
        finalQuery += " GROUP BY c." + DatabaseContract.CourseEntry._ID;


        Cursor cursor = db.rawQuery(finalQuery, selectionArgs.toArray(new String[0]));

        if (cursor.moveToFirst()) {
            do {
                courseList.add(cursorToCourse(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return courseList;
    }


    public YogaCourse getCourseById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT c.*, t." + DatabaseContract.TeacherEntry.COLUMN_NAME_NAME + " as teacher_name" + " FROM " + DatabaseContract.CourseEntry.TABLE_NAME + " c JOIN " + DatabaseContract.TeacherEntry.TABLE_NAME + " t ON c." + DatabaseContract.CourseEntry.COLUMN_NAME_TEACHER_ID + " = t." + DatabaseContract.TeacherEntry._ID + " WHERE c." + DatabaseContract.CourseEntry._ID + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});

        YogaCourse course = null;
        if (cursor.moveToFirst()) {
            course = cursorToCourse(cursor);
        }
        cursor.close();
        db.close();
        return course;
    }

    public int updateCourse(YogaCourse course) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.CourseEntry.COLUMN_NAME_NAME, course.getName());
        values.put(DatabaseContract.CourseEntry.COLUMN_NAME_DESCRIPTION, course.getDescription());
        values.put(DatabaseContract.CourseEntry.COLUMN_NAME_TEACHER_ID, course.getTeacherId());
        values.put(DatabaseContract.CourseEntry.COLUMN_NAME_DAY_OF_WEEK, course.getDayOfWeek());
        values.put(DatabaseContract.CourseEntry.COLUMN_NAME_TIME, course.getTime());
        values.put(DatabaseContract.CourseEntry.COLUMN_NAME_DURATION, course.getDuration());
        values.put(DatabaseContract.CourseEntry.COLUMN_NAME_MAX_CAPACITY, course.getMaxCapacity());
        values.put(DatabaseContract.CourseEntry.COLUMN_NAME_PRICE, course.getPrice());
        values.put(DatabaseContract.CourseEntry.COLUMN_NAME_DIFFICULTY, course.getDifficulty());
        values.put(DatabaseContract.CourseEntry.COLUMN_NAME_TYPE, course.getType());

        int result = db.update(DatabaseContract.CourseEntry.TABLE_NAME, values, DatabaseContract.CourseEntry._ID + " = ?",
                new String[]{String.valueOf(course.getId())});
        db.close();
        return result;
    }

    public void deleteCourse(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseContract.InstanceEntry.TABLE_NAME, DatabaseContract.InstanceEntry.COLUMN_NAME_COURSE_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.delete(DatabaseContract.CourseEntry.TABLE_NAME, DatabaseContract.CourseEntry._ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }
}