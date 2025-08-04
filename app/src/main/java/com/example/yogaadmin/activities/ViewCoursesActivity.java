package com.example.yogaadmin.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.yogaadmin.R;
import com.example.yogaadmin.adapters.CourseViewAdapter;
import com.example.yogaadmin.database.CourseDAO;
import com.example.yogaadmin.models.YogaCourse;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

/**
 * ViewCoursesActivity - Activity for displaying and managing yoga courses
 * 
 * This activity provides a comprehensive interface for viewing all yoga courses
 * in the system. It displays courses in a RecyclerView with options to:
 * - View course details
 * - Manage class instances for each course
 * - Edit course information
 * - Delete courses (with confirmation)
 * - Add new courses via FloatingActionButton
 * 
 * The activity implements CourseViewAdapter.OnCourseActionsClickListener to handle
 * user interactions with individual course items.
 * 
 * Features:
 * - RecyclerView with custom adapter for course display
 * - CRUD operations for courses
 * - Navigation to related activities
 * - Confirmation dialogs for destructive actions
 * - Automatic refresh on resume
 */
public class ViewCoursesActivity extends AppCompatActivity implements CourseViewAdapter.OnCourseActionsClickListener {
    
    // UI Components
    private RecyclerView rvCourses;
    
    // Data Access Objects
    private CourseDAO courseDAO;
    
    // Adapters and Data
    private CourseViewAdapter courseAdapter;
    private List<YogaCourse> courseList;

    /**
     * Called when the activity is first created
     * Initializes the UI, database connections, and sets up the RecyclerView
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_courses);

        // Set up the toolbar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize database access and RecyclerView
        courseDAO = new CourseDAO(this);
        rvCourses = findViewById(R.id.rvCourses);
        rvCourses.setLayoutManager(new LinearLayoutManager(this));

        // Set up FloatingActionButton for adding new courses
        FloatingActionButton fabAddCourse = findViewById(R.id.fabAddCourse);
        fabAddCourse.setOnClickListener(v -> {
            startActivity(new Intent(ViewCoursesActivity.this, AddCourseActivity.class));
        });

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
     * Loads all courses from the database and updates the RecyclerView
     * Creates a new adapter with the current course list and sets it to the RecyclerView
     */
    private void loadCourses() {
        courseList = courseDAO.getAllCourses();
        courseAdapter = new CourseViewAdapter(this, courseList, this);
        rvCourses.setAdapter(courseAdapter);
    }

    /**
     * Handles the "View Details" action for a course
     * Navigates to CourseDetailActivity with the selected course ID
     * 
     * @param course The course to view details for
     */
    @Override
    public void onViewDetailsClick(YogaCourse course) {
        Intent intent = new Intent(this, CourseDetailActivity.class);
        intent.putExtra("course_id", course.getId());
        startActivity(intent);
    }

    /**
     * Handles the "Manage Instances" action for a course
     * Navigates to ViewInstancesActivity with course information
     * 
     * @param course The course to manage instances for
     */
    @Override
    public void onManageInstancesClick(YogaCourse course) {
        Intent intent = new Intent(this, ViewInstancesActivity.class);
        intent.putExtra("course_id", course.getId());
        intent.putExtra("course_name", course.getName() + " - " + course.getDayOfWeek() + " " + course.getTime());
        startActivity(intent);
    }

    /**
     * Handles the "Edit Course" action
     * Navigates to AddCourseActivity in edit mode with course data
     * 
     * @param course The course to edit
     */
    @Override
    public void onEditCourseClick(YogaCourse course) {
        editCourse(course);
    }

    /**
     * Handles the "Delete Course" action
     * Shows a confirmation dialog before deleting the course
     * 
     * @param course The course to delete
     */
    @Override
    public void onDeleteCourseClick(YogaCourse course) {
        deleteCourse(course);
    }

    /**
     * Initiates the edit course process
     * Creates an intent to AddCourseActivity with the course ID for editing
     * 
     * @param course The course to edit
     */
    private void editCourse(YogaCourse course) {
        Intent intent = new Intent(this, AddCourseActivity.class);
        intent.putExtra("course_id", course.getId());
        startActivity(intent);
    }

    /**
     * Shows a confirmation dialog and deletes the course if confirmed
     * Deletes the course and all its associated class instances from the database
     * 
     * @param course The course to delete
     */
    private void deleteCourse(YogaCourse course) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Course")
                .setMessage("Are you sure you want to delete this course? This will also delete all associated class instances.")
                .setPositiveButton("Delete", (dialog, which) -> {
                    // Delete the course from database (cascade delete will handle instances)
                    courseDAO.deleteCourse(course.getId());
                    // Refresh the course list to reflect changes
                    loadCourses();
                    showToast("Course deleted successfully");
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    /**
     * Utility method to display toast messages to the user
     * Provides user feedback for various operations
     * 
     * @param message The message to display in the toast
     */
    private void showToast(String message) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show();
    }
}