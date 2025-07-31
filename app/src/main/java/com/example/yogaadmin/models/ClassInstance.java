package com.example.yogaadmin.models;

public class ClassInstance {
    private int id;
    private int courseId;
    private int teacherId;
    private String date;
    private String teacherName; // For display purposes

    public ClassInstance() {}

    public ClassInstance(int courseId, int teacherId, String date) {
        this.courseId = courseId;
        this.teacherId = teacherId;
        this.date = date;
    }

    public ClassInstance(int id, int courseId, int teacherId, String date) {
        this.id = id;
        this.courseId = courseId;
        this.teacherId = teacherId;
        this.date = date;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }

    public int getTeacherId() { return teacherId; }
    public void setTeacherId(int teacherId) { this.teacherId = teacherId; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }
}