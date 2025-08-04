package com.example.yogaadmin.models;

/**
 * YogaCourse model class represents a yoga course in the system.
 * This class contains all the information about a yoga course including
 * its schedule, teacher assignment, pricing, and class details.
 * 
 * The YogaCourse model is used throughout the application for:
 * - Displaying course information in UI components
 * - Managing course schedules and teacher assignments
 * - Storing course data in the database
 * - Handling course bookings and capacity management
 * - Course search and filtering operations
 * 
 * @author YogaAdmin Team
 * @version 1.0
 */
public class YogaCourse {
    /** Unique identifier for the course */
    private int id;
    
    /** Course name/title */
    private String name;
    
    /** Detailed description of the course */
    private String description;
    
    /** URL or path to the course image */
    private String imageUrl;
    
    /** Difficulty level of the course (e.g., Beginner, Intermediate, Advanced) */
    private String difficulty;
    
    /** Day of the week when the course is held */
    private String dayOfWeek;
    
    /** Time when the course starts (format: HH:MM) */
    private String time;
    
    /** Type/category of the yoga course (e.g., Hatha, Vinyasa, Ashtanga) */
    private String type;
    
    /** Foreign key reference to the teacher assigned to this course */
    private int teacherId;
    
    /** Teacher's name for display purposes (populated from database JOIN) */
    private String teacherName;
    
    /** Duration of the course in minutes */
    private int duration;
    
    /** Maximum number of students allowed in the course */
    private int maxCapacity;
    
    /** Course price in the local currency */
    private double price;

    /**
     * Default constructor for creating an empty YogaCourse object.
     * Used by database operations and object instantiation.
     */
    public YogaCourse() {}

    /**
     * Constructor for creating a new course from the UI.
     * This constructor is used when adding a new course through the application interface.
     * 
     * @param name The course name/title
     * @param description The course description
     * @param difficulty The difficulty level
     * @param dayOfWeek The day of the week
     * @param time The start time
     * @param type The course type/category
     * @param teacherId The ID of the assigned teacher
     * @param duration The course duration in minutes
     * @param maxCapacity The maximum number of students
     * @param price The course price
     */
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

    /**
     * Gets the unique identifier for the course.
     * 
     * @return The course ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the course.
     * 
     * @param id The course ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the course name/title.
     * 
     * @return The course name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the course name/title.
     * 
     * @param name The course name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the course description.
     * 
     * @return The course description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the course description.
     * 
     * @param description The course description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the URL or path to the course image.
     * 
     * @return The course image URL
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Sets the URL or path to the course image.
     * 
     * @param imageUrl The course image URL to set
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Gets the difficulty level of the course.
     * 
     * @return The course difficulty level
     */
    public String getDifficulty() {
        return difficulty;
    }

    /**
     * Sets the difficulty level of the course.
     * 
     * @param difficulty The course difficulty level to set
     */
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Gets the day of the week when the course is held.
     * 
     * @return The day of the week
     */
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * Sets the day of the week when the course is held.
     * 
     * @param dayOfWeek The day of the week to set
     */
    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    /**
     * Gets the time when the course starts.
     * 
     * @return The course start time
     */
    public String getTime() {
        return time;
    }

    /**
     * Sets the time when the course starts.
     * 
     * @param time The course start time to set
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Gets the type/category of the yoga course.
     * 
     * @return The course type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type/category of the yoga course.
     * 
     * @param type The course type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the ID of the teacher assigned to this course.
     * 
     * @return The teacher ID
     */
    public int getTeacherId() {
        return teacherId;
    }

    /**
     * Sets the ID of the teacher assigned to this course.
     * 
     * @param teacherId The teacher ID to set
     */
    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    /**
     * Gets the teacher's name for display purposes.
     * This field is populated from database JOIN operations.
     * 
     * @return The teacher's name
     */
    public String getTeacherName() {
        return teacherName;
    }

    /**
     * Sets the teacher's name for display purposes.
     * 
     * @param teacherName The teacher's name to set
     */
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    /**
     * Gets the duration of the course in minutes.
     * 
     * @return The course duration in minutes
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Sets the duration of the course in minutes.
     * 
     * @param duration The course duration in minutes to set
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Gets the maximum number of students allowed in the course.
     * 
     * @return The maximum capacity
     */
    public int getMaxCapacity() {
        return maxCapacity;
    }

    /**
     * Sets the maximum number of students allowed in the course.
     * 
     * @param maxCapacity The maximum capacity to set
     */
    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    /**
     * Gets the course price in the local currency.
     * 
     * @return The course price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the course price in the local currency.
     * 
     * @param price The course price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returns a string representation of the course for display purposes.
     * This method combines the course name, day, and time for easy identification
     * in UI components such as ListViews and Spinners.
     * 
     * @return A formatted string containing course name, day, and time
     */
    @Override
    public String toString() {
        return name + " - " + dayOfWeek + " " + time;
    }
}