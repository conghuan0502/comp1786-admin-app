package com.example.yogaadmin.models;

/**
 * Teacher model class represents a yoga instructor in the system.
 * This class contains all the information about a teacher including
 * their personal details and contact information.
 * 
 * The Teacher model is used throughout the application for:
 * - Displaying teacher information in UI components
 * - Managing teacher assignments to courses
 * - Storing teacher data in the database
 * - Populating spinners and dropdowns
 * 
 * @author YogaAdmin Team
 * @version 1.0
 */
public class Teacher {
    /** Unique identifier for the teacher */
    private int id;
    
    /** Teacher's full name */
    private String name;
    
    /** Teacher's email address */
    private String email;
    
    /** Teacher's phone number */
    private String phone;

    /**
     * Default constructor for creating an empty Teacher object.
     * Used by database operations and object instantiation.
     */
    public Teacher() {}

    /**
     * Constructor for creating a Teacher object with all properties.
     * 
     * @param id The unique identifier for the teacher
     * @param name The teacher's full name
     * @param email The teacher's email address
     * @param phone The teacher's phone number
     */
    public Teacher(int id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    /**
     * Gets the unique identifier for the teacher.
     * 
     * @return The teacher's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the teacher.
     * 
     * @param id The teacher's ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the teacher's full name.
     * 
     * @return The teacher's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the teacher's full name.
     * 
     * @param name The teacher's name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the teacher's email address.
     * 
     * @return The teacher's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the teacher's email address.
     * 
     * @param email The teacher's email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the teacher's phone number.
     * 
     * @return The teacher's phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the teacher's phone number.
     * 
     * @param phone The teacher's phone number to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns the teacher's name as a string representation.
     * This method is important for displaying the teacher object in UI components
     * such as Spinners, ListViews, and other adapter-based views.
     * 
     * @return The teacher's name as a string
     */
    @Override
    public String toString() {
        return name;
    }
}