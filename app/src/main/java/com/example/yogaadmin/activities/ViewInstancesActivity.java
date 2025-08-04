package com.example.yogaadmin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.yogaadmin.R;
import com.example.yogaadmin.adapters.InstanceAdapter;
import com.example.yogaadmin.database.CourseDAO;
import com.example.yogaadmin.database.InstanceDAO;
import com.example.yogaadmin.models.ClassInstance;
import com.example.yogaadmin.models.YogaCourse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

/**
 * ViewInstancesActivity - Activity for viewing and managing class instances
 * 
 * This activity displays all scheduled instances for a specific course and provides
 * functionality to add, edit, and delete class instances. It shows the course name
 * and allows users to manage the schedule for that course.
 * 
 * Features:
 * - Display all instances for a specific course
 * - Add new class instances via FloatingActionButton
 * - Edit existing class instances
 * - Delete class instances with confirmation
 * - Real-time list updates after operations
 * - Navigation to related activities
 * 
 * The activity receives a course ID from the calling activity and loads all
 * associated class instances for display and management.
 */
public class ViewInstancesActivity extends AppCompatActivity {
    
    // UI Components - Display Information
    private TextView tvCourseName;
    
    // UI Components - List Display
    private RecyclerView rvInstances;
    
    // UI Components - Action Button
    private FloatingActionButton btnAddInstance;
    
    // Data Access Objects
    private InstanceDAO instanceDAO;
    private CourseDAO courseDAO;
    
    // Adapters and Data
    private InstanceAdapter instanceAdapter;
    private List<ClassInstance> instanceList;
    
    // Course Information
    private YogaCourse course;
    private int courseId;

    /**
     * Called when the activity is first created
     * Initializes the UI, validates course data, and sets up functionality
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_instances);

        // Initialize database access
        instanceDAO = new InstanceDAO(this);
        courseDAO = new CourseDAO(this);
        
        // Extract course ID from intent
        courseId = getIntent().getIntExtra("course_id", -1);

        // Validate course ID
        if (courseId == -1) {
            showToast("Error: Invalid Course ID");
            finish(); // Close activity if invalid course ID
            return;
        }

        // Load course information
        course = courseDAO.getCourseById(courseId);

        // Validate course exists
        if (course == null) {
            showToast("Error: Course not found");
            finish(); // Close activity if course not found
            return;
        }

        // Set up UI and functionality
        initializeViews();
        setupRecyclerView();
        loadInstances();

        // Set up FloatingActionButton for adding new instances
        btnAddInstance.setOnClickListener(v -> {
            Intent intent = new Intent(ViewInstancesActivity.this, AddInstanceActivity.class);
            intent.putExtra("course_id", course.getId());
            intent.putExtra("teacher_id", course.getTeacherId());
            intent.putExtra("course_name", course.getName());
            intent.putExtra("course_day", course.getDayOfWeek());
            startActivity(intent);
        });
    }

    /**
     * Called when the activity becomes visible to the user
     * Refreshes the instance list to show any changes made in other activities
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadInstances(); // Refresh the list when returning to this activity
    }

    /**
     * Initializes all UI components by finding them in the layout
     * Sets the course name in the toolbar title
     */
    private void initializeViews() {
        tvCourseName = findViewById(R.id.toolbar_title);
        rvInstances = findViewById(R.id.instancesRecyclerView);
        btnAddInstance = findViewById(R.id.addInstanceFab);
        tvCourseName.setText(course.getName()); // Set course name in toolbar
    }

    /**
     * Sets up the RecyclerView with layout manager
     * Configures the RecyclerView to display instances in a vertical list
     */
    private void setupRecyclerView() {
        rvInstances.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Loads all instances for the current course and updates the RecyclerView
     * Creates a new adapter with the current instance list and click handling
     */
    private void loadInstances() {
        instanceList = instanceDAO.getInstancesForCourse(courseId);
        instanceAdapter = new InstanceAdapter(this, instanceList, this::showInstanceOptions);
        rvInstances.setAdapter(instanceAdapter);
    }

    /**
     * Shows a dialog with options for managing a class instance
     * Provides options to edit or delete the selected instance
     * 
     * @param instance The class instance to manage
     */
    private void showInstanceOptions(ClassInstance instance) {
        new AlertDialog.Builder(this)
                .setTitle("Instance Options")
                .setItems(new String[]{"Edit Instance", "Delete Instance"}, (dialog, which) -> {
                    if (which == 0) {
                        editInstance(instance);
                    } else if (which == 1) {
                        deleteInstance(instance);
                    }
                })
                .show();
    }

    /**
     * Initiates the edit instance process
     * Creates an intent to EditInstanceActivity with instance and course data
     * 
     * @param instance The class instance to edit
     */
    private void editInstance(ClassInstance instance) {
        Intent intent = new Intent(this, EditInstanceActivity.class);
        intent.putExtra("instance_id", instance.getId());
        intent.putExtra("course_id", course.getId());
        intent.putExtra("teacher_id", course.getTeacherId());
        intent.putExtra("course_name", course.getName());
        intent.putExtra("course_day", course.getDayOfWeek());
        startActivity(intent);
    }

    /**
     * Shows a confirmation dialog and deletes the instance if confirmed
     * Deletes the class instance from the database and refreshes the list
     * 
     * @param instance The class instance to delete
     */
    private void deleteInstance(ClassInstance instance) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Instance")
                .setMessage("Are you sure you want to delete this class instance?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    // Delete the instance from database
                    instanceDAO.deleteInstance(instance.getId());
                    // Refresh the list to reflect changes
                    loadInstances();
                    showToast("Instance deleted successfully");
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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
