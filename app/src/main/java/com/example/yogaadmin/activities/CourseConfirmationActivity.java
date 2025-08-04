package com.example.yogaadmin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yogaadmin.R;

/**
 * CourseConfirmationActivity - Activity for displaying course creation confirmation
 * 
 * This activity shows a summary of the course details after successful course creation.
 * It provides users with a confirmation of what was created and options to either
 * return to the main screen or go back to edit the course.
 * 
 * Features:
 * - Display comprehensive course details summary
 * - Navigation back to main activity
 * - Option to return to course editing
 * - Clear activity stack when returning to main
 * 
 * The activity receives course information from the calling activity and displays
 * it in a formatted, user-friendly manner. It serves as a confirmation step
 * in the course creation workflow.
 */
public class CourseConfirmationActivity extends AppCompatActivity {

    // UI Components - Display Information
    private TextView tvConfirmationDetails;
    
    // UI Components - Action Buttons
    private Button btnBackToMain, btnEditCourse;

    /**
     * Called when the activity is first created
     * Initializes the UI and sets up navigation options
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_confirmation);

        // Initialize UI components
        tvConfirmationDetails = findViewById(R.id.tvConfirmationDetails);
        btnBackToMain = findViewById(R.id.btnBackToMain);
        btnEditCourse = findViewById(R.id.btnEditCourse);

        // Display the course details summary
        displayCourseDetails();

        // Set up navigation button for returning to main activity
        btnBackToMain.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clear activity stack
            startActivity(intent);
            finish(); // Close this activity
        });

        // Set up navigation button for returning to course editing
        btnEditCourse.setOnClickListener(v -> {
            finish(); // Go back to the previous activity (edit screen)
        });
    }

    /**
     * Displays the course details in a formatted summary
     * 
     * This method extracts all course information from the intent extras
     * and formats it into a readable summary for the user to review.
     * The information includes all the key details of the created course.
     */
    private void displayCourseDetails() {
        Intent intent = getIntent();
        StringBuilder details = new StringBuilder();
        
        // Build a comprehensive course details summary
        details.append("Course Details:\n\n");
        details.append("Name: ").append(intent.getStringExtra("course_name")).append("\n");
        details.append("Description: ").append(intent.getStringExtra("course_description")).append("\n");
        details.append("Teacher ID: ").append(intent.getIntExtra("course_teacher_id", 0)).append("\n");
        details.append("Day: ").append(intent.getStringExtra("course_day_of_week")).append("\n");
        details.append("Time: ").append(intent.getStringExtra("course_time")).append("\n");
        details.append("Duration: ").append(intent.getIntExtra("course_duration", 0)).append(" minutes\n");
        details.append("Max Capacity: ").append(intent.getIntExtra("course_max_capacity", 0)).append(" people\n");

        // Display the formatted details in the text view
        tvConfirmationDetails.setText(details.toString());
    }
}