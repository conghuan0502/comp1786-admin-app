package com.example.yogaadmin.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.yogaadmin.R;
import com.example.yogaadmin.adapters.CourseRecyclerAdapter;
import com.example.yogaadmin.database.CourseDAO;
import com.example.yogaadmin.models.YogaCourse;
import com.google.android.material.appbar.MaterialToolbar;
import java.util.List;

/**
 * ManageInstancesActivity - Activity for managing class instances across all courses
 * 
 * This activity provides a centralized interface for managing class instances.
 * It displays all courses in a list and allows users to navigate to the
 * instance management screen for each specific course.
 * 
 * Features:
 * - Display all courses in a RecyclerView
 * - Navigation to instance management for each course
 * - Automatic refresh when returning to the activity
 * - Clean, organized course listing
 * 
 * The activity serves as a hub for accessing instance management functionality
 * for any course in the system. It uses a custom adapter to display courses
 * and handle navigation to the ViewInstancesActivity for each course.
 */
public class ManageInstancesActivity extends AppCompatActivity {
    
    // UI Components - List Display
    private RecyclerView rvCourses;
    
    // Data Access Objects
    private CourseDAO courseDAO;
    
    // Adapters and Data
    private CourseRecyclerAdapter courseAdapter;
    private List<YogaCourse> courseList;

    /**
     * Called when the activity is first created
     * Initializes the UI, database connections, and sets up the RecyclerView
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_instances);

        // Set up the toolbar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize database access and RecyclerView
        courseDAO = new CourseDAO(this);
        rvCourses = findViewById(R.id.rvCourses);
        rvCourses.setLayoutManager(new LinearLayoutManager(this));

        // Load and display courses
        loadCourses();
    }

    /**
     * Called when the activity becomes visible to the user
     * Refreshes the course list to show any changes made in other activities
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadCourses(); // Refresh the list when returning to this activity
    }

    /**
     * Loads all courses from the database and sets up the RecyclerView
     * Creates a new adapter with click handling for navigation to instance management
     */
    private void loadCourses() {
        courseList = courseDAO.getAllCourses();
        courseAdapter = new CourseRecyclerAdapter(this, courseList, course -> {
            // Handle course item click - navigate to instance management for this course
            Intent intent = new Intent(ManageInstancesActivity.this, ViewInstancesActivity.class);
            intent.putExtra("course_id", course.getId());
            intent.putExtra("course_name", course.getName());
            startActivity(intent);
        });
        rvCourses.setAdapter(courseAdapter);
    }
}