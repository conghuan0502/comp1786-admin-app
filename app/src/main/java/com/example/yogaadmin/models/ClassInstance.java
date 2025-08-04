package com.example.yogaadmin.models;

/**
 * ClassInstance model class represents an individual yoga class session.
 * This class contains information about a specific class instance that
 * is scheduled on a particular date with a specific teacher.
 * 
 * The ClassInstance model is used throughout the application for:
 * - Managing individual class sessions
 * - Displaying class schedules and calendars
 * - Tracking class attendance and bookings
 * - Linking courses to specific dates and teachers
 * - Handling class instance modifications and cancellations
 * 
 * @author YogaAdmin Team
 * @version 1.0
 */
public class ClassInstance {
    /** Unique identifier for the class instance */
    private int id;
    
    /** Foreign key reference to the course this instance belongs to */
    private int courseId;
    
    /** Foreign key reference to the teacher assigned to this instance */
    private int teacherId;
    
    /** Date of the class instance (format: YYYY-MM-DD) */
    private String date;
    
    /** Teacher's name for display purposes (populated from database JOIN) */
    private String teacherName;

    /**
     * Default constructor for creating an empty ClassInstance object.
     * Used by database operations and object instantiation.
     */
    public ClassInstance() {}

    /**
     * Constructor for creating a new class instance from the UI.
     * This constructor is used when scheduling a new class instance.
     * 
     * @param courseId The ID of the course this instance belongs to
     * @param teacherId The ID of the teacher assigned to this instance
     * @param date The date of the class instance
     */
    public ClassInstance(int courseId, int teacherId, String date) {
        this.courseId = courseId;
        this.teacherId = teacherId;
        this.date = date;
    }

    /**
     * Constructor for creating a ClassInstance object with all properties.
     * This constructor is typically used when retrieving data from the database.
     * 
     * @param id The unique identifier for the class instance
     * @param courseId The ID of the course this instance belongs to
     * @param teacherId The ID of the teacher assigned to this instance
     * @param date The date of the class instance
     */
    public ClassInstance(int id, int courseId, int teacherId, String date) {
        this.id = id;
        this.courseId = courseId;
        this.teacherId = teacherId;
        this.date = date;
    }

    /**
     * Gets the unique identifier for the class instance.
     * 
     * @return The class instance ID
     */
    public int getId() { 
        return id; 
    }
    
    /**
     * Sets the unique identifier for the class instance.
     * 
     * @param id The class instance ID to set
     */
    public void setId(int id) { 
        this.id = id; 
    }

    /**
     * Gets the ID of the course this instance belongs to.
     * 
     * @return The course ID
     */
    public int getCourseId() { 
        return courseId; 
    }
    
    /**
     * Sets the ID of the course this instance belongs to.
     * 
     * @param courseId The course ID to set
     */
    public void setCourseId(int courseId) { 
        this.courseId = courseId; 
    }

    /**
     * Gets the ID of the teacher assigned to this instance.
     * 
     * @return The teacher ID
     */
    public int getTeacherId() { 
        return teacherId; 
    }
    
    /**
     * Sets the ID of the teacher assigned to this instance.
     * 
     * @param teacherId The teacher ID to set
     */
    public void setTeacherId(int teacherId) { 
        this.teacherId = teacherId; 
    }

    /**
     * Gets the date of the class instance.
     * 
     * @return The class instance date
     */
    public String getDate() { 
        return date; 
    }
    
    /**
     * Sets the date of the class instance.
     * 
     * @param date The class instance date to set
     */
    public void setDate(String date) { 
        this.date = date; 
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
}