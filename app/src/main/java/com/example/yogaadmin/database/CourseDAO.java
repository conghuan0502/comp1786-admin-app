package com.example.yogaadmin.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.example.yogaadmin.models.YogaCourse;
import java.util.ArrayList;
import java.util.List;

/**
 * CourseDAO (Data Access Object) provides methods to interact with the courses table.
 * This class handles all database operations related to yoga courses including
 * CRUD operations and complex queries with joins to related tables.
 * 
 * The CourseDAO supports:
 * - Inserting new courses
 * - Retrieving courses with teacher information
 * - Searching courses by various criteria
 * - Deleting courses and their related instances
 * 
 * @author YogaAdmin Team
 * @version 1.0
 */
public class CourseDAO {
    /** Database helper instance for database operations */
    private DatabaseHelper dbHelper;

    /**
     * Constructs a new CourseDAO with the given context.
     * 
     * @param context The application context
     */
    public CourseDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    /**
     * Inserts a new course into the database.
     * 
     * @param course The YogaCourse object to insert
     * @return The row ID of the newly inserted course, or -1 if insertion failed
     */
    public long insertCourse(YogaCourse course) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        
        // Map course properties to database columns
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

    /**
     * Converts a database cursor to a YogaCourse object.
     * This method handles the mapping of database columns to object properties,
     * including the joined teacher name from the teachers table.
     * 
     * @param cursor The database cursor containing course data
     * @return A YogaCourse object populated with cursor data
     */
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

    /**
     * Retrieves all courses from the database with teacher information.
     * This method performs a JOIN with the teachers table to include teacher names.
     * 
     * @return List of all courses with teacher information
     */
    public List<YogaCourse> getAllCourses() {
        return searchCourses(null, null, null);
    }

    /**
     * Searches for courses based on specified criteria.
     * This method supports filtering by teacher name, day of week, and date.
     * It performs a complex query with JOINs to include teacher information
     * and optional filtering by class instances on specific dates.
     * 
     * @param teacherName Optional teacher name filter (supports partial matching)
     * @param dayOfWeek Optional day of week filter (exact match)
     * @param date Optional date filter for class instances (exact match)
     * @return List of courses matching the search criteria
     */
    public List<YogaCourse> searchCourses(String teacherName, String dayOfWeek, String date) {
        List<YogaCourse> courseList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Build base query with JOINs to include teacher information
        String baseQuery = "SELECT c.*, t." + DatabaseContract.TeacherEntry.COLUMN_NAME_NAME + " as teacher_name" +
                           " FROM " + DatabaseContract.CourseEntry.TABLE_NAME + " c" +
                           " JOIN " + DatabaseContract.TeacherEntry.TABLE_NAME + " t ON c." + DatabaseContract.CourseEntry.COLUMN_NAME_TEACHER_ID + " = t." + DatabaseContract.TeacherEntry._ID +
                           " LEFT JOIN " + DatabaseContract.InstanceEntry.TABLE_NAME + " i ON c." + DatabaseContract.CourseEntry._ID + " = i." + DatabaseContract.InstanceEntry.COLUMN_NAME_COURSE_ID;

        StringBuilder whereClause = new StringBuilder();
        List<String> selectionArgs = new ArrayList<>();

        // Add teacher name filter if provided
        if (!TextUtils.isEmpty(teacherName)) {
            whereClause.append("t.").append(DatabaseContract.TeacherEntry.COLUMN_NAME_NAME).append(" LIKE ?");
            selectionArgs.add("%" + teacherName + "%");
        }

        // Add day of week filter if provided
        if (!TextUtils.isEmpty(dayOfWeek)) {
            if (whereClause.length() > 0) {
                whereClause.append(" AND ");
            }
            whereClause.append("c.").append(DatabaseContract.CourseEntry.COLUMN_NAME_DAY_OF_WEEK).append(" = ?");
            selectionArgs.add(dayOfWeek);
        }

        // Add date filter if provided
        if (!TextUtils.isEmpty(date)) {
            if (whereClause.length() > 0) {
                whereClause.append(" AND ");
            }
            whereClause.append("i.").append(DatabaseContract.InstanceEntry.COLUMN_NAME_DATE).append(" = ?");
            selectionArgs.add(date);
        }

        // Build final query with WHERE clause if filters are applied
        String finalQuery = baseQuery;
        if (whereClause.length() > 0) {
            finalQuery += " WHERE " + whereClause.toString();
        }
        finalQuery += " GROUP BY c." + DatabaseContract.CourseEntry._ID;

        // Execute query and convert results
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

    /**
     * Retrieves a specific course by its ID.
     * This method performs a JOIN with the teachers table to include teacher information.
     * 
     * @param id The unique identifier of the course
     * @return The YogaCourse object if found, null otherwise
     */
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

    /**
     * Deletes a course and all its related class instances.
     * This method performs a cascading delete to maintain referential integrity.
     * 
     * @param id The unique identifier of the course to delete
     */
    public void deleteCourse(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        
        // Delete related class instances first to maintain foreign key integrity
        db.delete(DatabaseContract.InstanceEntry.TABLE_NAME, DatabaseContract.InstanceEntry.COLUMN_NAME_COURSE_ID + " = ?",
                new String[]{String.valueOf(id)});
        
        // Delete the course
        db.delete(DatabaseContract.CourseEntry.TABLE_NAME, DatabaseContract.CourseEntry._ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }
}