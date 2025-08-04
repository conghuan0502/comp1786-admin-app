package com.example.yogaadmin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.example.yogaadmin.R;
import com.example.yogaadmin.database.DatabaseHelper;
import com.example.yogaadmin.database.FirebaseSyncManager;

/**
 * MainActivity - The main dashboard activity for the Yoga Admin application
 * 
 * This activity serves as the central hub for navigating to different features:
 * - Course management (add/view courses)
 * - Instance management (schedule/manage class instances)
 * - Teacher management
 * - Search functionality
 * - Database operations (reset/sync)
 * 
 * The activity uses a card-based layout for easy navigation and provides
 * administrative functions for database management.
 */
public class MainActivity extends AppCompatActivity {

    // UI Components - Navigation Cards
    private CardView addCourseCard, viewCoursesCard, manageInstancesCard, searchCard, manageTeachersCard;
    
    // UI Components - Database Management Buttons
    private Button btnResetDatabase, btnSyncFirebase;
    
    // Database and Sync Managers
    private DatabaseHelper dbHelper;
    private FirebaseSyncManager firebaseSyncManager;

    /**
     * Called when the activity is first created
     * Initializes the database helpers and sets up the UI
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize database managers
        dbHelper = new DatabaseHelper(this);
        firebaseSyncManager = new FirebaseSyncManager(this);
        
        // Set up UI components and event listeners
        initializeViews();
        setClickListeners();
    }

    /**
     * Initializes all UI components by finding them in the layout
     * This method binds the Java variables to the XML layout elements
     */
    private void initializeViews() {
        // Navigation cards for different features
        addCourseCard = findViewById(R.id.addCourseCard);
        viewCoursesCard = findViewById(R.id.viewCoursesCard);
        manageInstancesCard = findViewById(R.id.manageInstancesCard);
        searchCard = findViewById(R.id.searchCard);
        manageTeachersCard = findViewById(R.id.manageTeachersCard);
        
        // Database management buttons
        btnResetDatabase = findViewById(R.id.btnResetDatabase);
        btnSyncFirebase = findViewById(R.id.btnSyncFirebase);
    }

    /**
     * Sets up click listeners for all interactive UI elements
     * Each listener navigates to the appropriate activity or performs database operations
     */
    private void setClickListeners() {
        // Navigation to course management activities
        addCourseCard.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AddCourseActivity.class)));

        viewCoursesCard.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ViewCoursesActivity.class)));

        // Navigation to instance management activities
        manageInstancesCard.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ManageInstancesActivity.class)));

        // Navigation to search functionality
        searchCard.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SearchActivity.class)));

        // Navigation to teacher management
        manageTeachersCard.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ManageTeachersActivity.class)));

        // Database reset functionality with confirmation dialog
        btnResetDatabase.setOnClickListener(v -> {
            new android.app.AlertDialog.Builder(this)
                .setTitle("Reset Database")
                .setMessage("Are you sure you want to reset both the local and Firebase databases? This action cannot be undone.")
                .setPositiveButton("Reset", (dialog, which) -> {
                    // Reset both local SQLite and Firebase databases
                    dbHelper.resetDatabase();
                    firebaseSyncManager.resetFirebaseDatabase();
                    showToast("Databases reset successfully!");
                })
                .setNegativeButton("Cancel", null)
                .show();
        });

        // Firebase synchronization functionality
        btnSyncFirebase.setOnClickListener(v -> {
            firebaseSyncManager.syncAllData();
            showToast("Syncing data to Firebase!");
        });
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