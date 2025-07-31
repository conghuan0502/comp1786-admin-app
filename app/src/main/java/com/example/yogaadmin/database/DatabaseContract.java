package com.example.yogaadmin.database;

import android.provider.BaseColumns;

public final class DatabaseContract {

    private DatabaseContract() {}

    public static class CourseEntry implements BaseColumns {
        public static final String TABLE_NAME = "courses";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_TEACHER_ID = "teacher_id";
        public static final String COLUMN_NAME_DAY_OF_WEEK = "day_of_week";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String COLUMN_NAME_DURATION = "duration";
        public static final String COLUMN_NAME_MAX_CAPACITY = "max_capacity";
        public static final String COLUMN_NAME_PRICE = "price";
        public static final String COLUMN_NAME_DIFFICULTY = "difficulty";
        public static final String COLUMN_NAME_TYPE = "type";
    }

    public static class InstanceEntry implements BaseColumns {
        public static final String TABLE_NAME = "class_instances";
        public static final String COLUMN_NAME_COURSE_ID = "course_id";
        public static final String COLUMN_NAME_TEACHER_ID = "teacher_id";
        public static final String COLUMN_NAME_DATE = "date";
    }

    

    public static class TeacherEntry implements BaseColumns {
        public static final String TABLE_NAME = "teachers";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_PHONE = "phone";
    }
}
