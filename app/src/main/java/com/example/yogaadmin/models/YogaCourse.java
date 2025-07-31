package com.example.yogaadmin.models;

public class YogaCourse {
    private int id;
    private String name;
    private String description;
    private String imageUrl;
    private String difficulty;
    private String dayOfWeek;
    private String time;
    private String type;
    private int teacherId;
    private String teacherName; // Used for display purposes
    private int duration;
    private int maxCapacity;
    private double price;

    public YogaCourse() {}

    // Constructor for creating a new course from the UI
    public YogaCourse(String name, String description, String difficulty, String dayOfWeek, String time, String type, int teacherId, int duration, int maxCapacity, double price) {
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
        this.dayOfWeek = dayOfWeek;
        this.time = time;
        this.type = type;
        this.teacherId = teacherId;
        this.duration = duration;
        this.maxCapacity = maxCapacity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return name + " - " + dayOfWeek + " " + time;
    }
}